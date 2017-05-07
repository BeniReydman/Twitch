import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	
	static public final String privMsgRegex = ":([^!]+)![^@]+@[^.]+\\.([^ ]+) PRIVMSG ([^ ]+) :(.*)";
	static public final Pattern privMsgPattern = Pattern.compile(privMsgRegex, Pattern.CASE_INSENSITIVE);
	static public final String pingPongRegex = "^(Ping)(.*)";
	static public final Pattern pingPongPattern = Pattern.compile(pingPongRegex, Pattern.CASE_INSENSITIVE);
	private String nick;
	private String domain;
	private String channel;
	private String tMessage;
	private String pingPong;
	
	public void setData(String raw){
		Matcher regexMatcher = privMsgPattern.matcher(raw);
		if(regexMatcher.matches())
		{
		this.nick = regexMatcher.group(1);
		this.domain = regexMatcher.group(2);
		this.channel = regexMatcher.group(3);
		this.tMessage = regexMatcher.group(4);
		}
	}
	
	public void setPong(String raw)
	{
		Matcher pingPongMatcher = pingPongPattern.matcher(raw);
		if(pingPongMatcher.matches())
		this.pingPong = ("PONG" + pingPongMatcher.group(2));
	}
	
	public String getPong()
	{
		return this.pingPong;
	}
	
	public String getMessage()
	{
		return this.tMessage;
	}
	
	public String getNick()
	{
		return this.nick;
	}
	
	public Message createPingPong(String mess)
	{
		Message clone = new Message();
		clone.pingPong = mess;
		return clone;
	}
	
	public Message createReplyMessage(String nick, String mess)
	{
		Message clone = new Message();
		clone.nick = nick;
		clone.domain = this.domain;
		clone.channel = this.channel;
		clone.tMessage = mess;
		return clone;
	}
	
	public String formatForSending() {
		return String.format(":%s!%s@%s.%s PRIVMSG %s :%s",nick,nick,nick,domain,channel,tMessage);
	}
	
	public String toString() {
		return String.format("[%s] %s : %s", channel, nick , tMessage);
	}
}
