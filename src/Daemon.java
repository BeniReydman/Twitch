import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Daemon implements Runnable {

	private boolean isRunning;
	private Thread thread;
	private HashSet<MessageListener> listeners;
	private BufferedReader reader;
	private PrintWriter writer;

	public Daemon() {
		setup();
	}

	public Daemon(InputStream is, OutputStream os) throws IOException {
		setStreams(is, os);
		setup();
	}

	private void setup() {
		listeners = new HashSet<MessageListener>();
	}

	public void setStreams(InputStream is, OutputStream os) throws IOException {
		setInputStream(is);
		setOutputStream(os);

	}

	private void setInputStream(InputStream is) {
		if (reader == null) {
			reader = createReader(is);
		} else {
			synchronized (reader) {
				try {
					reader.close();
				} catch (IOException ioe) {

				}
				reader = createReader(is);
			}
		}
	}

	private void setOutputStream(OutputStream os) {
		if (writer == null) {
			writer = createWriter(os);
		} else {
			synchronized (writer) {
				writer.close();
				writer = createWriter(os);
			}
		}
	}

	private BufferedReader createReader(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		return br;
	}

	private PrintWriter createWriter(OutputStream os) {
		OutputStreamWriter osw = new OutputStreamWriter(os, Charset.forName("UTF-8"));
		PrintWriter pw = new PrintWriter(osw);
		return pw;
	}

	public void closeStreams() {
		closeInputStream();
		closeOutputStream();
	}

	private void closeInputStream() {
		if (reader != null) {
			synchronized (reader) {
				try {
					reader.close();
				} catch (IOException ioe) {

				}
			}
		}
	}

	private void closeOutputStream() {
		if (writer != null) {
			synchronized (writer) {
				writer.close();
			}
		}
	}

	public void addListener(MessageListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}

	}

	public void remListener(MessageListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}

	}

	public void alertListeners(Message message) {
		synchronized (listeners) {
			for (MessageListener listener : listeners) {
				listener.newMessage(message);
			}
		}
	}

	public void start() {
		if (isRunning) {
			return;
		}

		if (thread == null) {
			thread = new Thread(this);
		}
		isRunning = true;
		thread.start();
	}

	public void stop() {
		if (thread == null)
			return;

		isRunning = false;
		thread.interrupt();
	}

	public void run() {
		try {
			checkForMessages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkForMessages() throws IOException {
		String line;
		Message message;
		while (isRunning) {
			synchronized (writer) {
				line = reader.readLine();
				if (line == null){
					throw new IOException("");
				}
			}
			message = new Message();
			message.setData(line);
			message.setPong(line);
			alertListeners(message);
		}
	}

	public void sendString(String s) {
		synchronized (writer) {
			writer.println(s);
			writer.flush();
		}
	}

	public void sendMessages(Message message) {
		String s = message.formatForSending();
		sendString(s);
	}
}
