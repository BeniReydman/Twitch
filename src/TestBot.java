import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestBot implements MessageListener {

	public static void main(String[] args) {
		new TestBot("PUT YOUR BOTS NAME HERE", "PUT THE NAME OF THE CHANNEL YOU WANT TO GO TO");
	}


	private String hiNinja = "(^(ello|ey|hey|hi|y[o]{1,}|hello|sup)(\\s|,).*(ninja|m8|mate|dude|man|bro)|^(hey|hi|y[o]{1,}|hello|ey|sup|ello)$)";
	private String howsItGoing = "((hows|how's|whatsup|whats up|whatup|what up).*(ninja|m8|mate|dude|man|bro)|^(hows|how's|whats).*(up|going)|^(whatup)$|(ninja|m8|mate|dude|man|bro)\\s(hows|how's|whatsup|whatup|whats up))";
	private String mirrorResponse = "i\\s([^ ]+)\\syou($|(\\sninja[^ ])(!)?$)";
	private String win = "^(who's|whos|who is|who g).*((win|win)(\\?)?)$";
	private String attack = "^(say:)(.*)";
	private String dice = "^(roll!|!roll)$";
	private String howMuch = "(how much|hm).*(make|earn|get|made).*(hour|1b|hr)";
	private String name;
	private String deal = "^(!deal)$";
	private String hit = "^(!hit)$";
	private String stay = "^(!stay)$";
	private String discord ="^(!discord)$";
	private String back = "(^(i am|i'm|im)\\s(back).*|^(back)$)";
	private String channel;
	private String ping;
	private String byeNinja = "(^(bye|see ya|goodbye|later|adios|night|cya|peace out|seeya)$|(bye|see ya|goodbye|later|adios|night|cya|peace out|seeya)\\sninja|(((im|i'm)\\s(going|leaving))\\sninja))";
	private String meanLife = "(((what is|whats|what's)(.+of)+\\slife)|(what is(.*(to|too))*\\slife))";
	private String ninjaMonkey = "^(ninjaMonkey)$";
	private String mathA = "^(\\d+)(\\s)?[+](\\s)?(\\d+)(\\s)?=(\\s)?(\\?)?";
	private String mathS = "^(\\d+)(\\s)?[-](\\s)?(\\d+)(\\s)?=(\\s)?(\\?)?";
	private String mathM = "^(\\d+)(\\s)?[*](\\s)?(\\d+)(\\s)?=(\\s)?(\\?)?";
	private String mathD = "^(\\d+)(\\s)?[/](\\s)?(\\d+)(\\s)?=(\\s)?(\\?)?";
	private String love = "^(what is love\\??)";
	private String love2 = "^(don't|dont) hurt me";
	private int[] primes = {59,61,67,71,73,79,83,89,97,101,103,107};
	private ArrayList<String> responseList = new ArrayList<String>();
	private LinkedList blackJack = new LinkedList();
	private Socket socket;
	private Daemon daemon;

	public TestBot(String name, String channel) {
		this.name = name;
		this.channel = channel;

		try {
			socket = new Socket("irc.twitch.tv", 6667);
			InputStream streamIn = socket.getInputStream();
			OutputStream streamOut = socket.getOutputStream();
			daemon = new Daemon(streamIn, streamOut);
			daemon.addListener(this);

			/**************************************************************
			 * 
			 *************************************************************/
			daemon.sendString("pass oauth:THE REST OF THE NUMBERS HERE");
			/**************************************************************
			 * 
			 *************************************************************/
			daemon.sendString("nick " + this.name);
			daemon.sendString("join #" + this.channel);

			daemon.start();
			
			
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				public void run() {
					daemon.closeStreams();
				}
			}));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	private void getResponse(Message message) {
		String toReturn = "";
		String banana = message.getMessage();
		String user = message.getNick();

		if (banana == null | user == null)
			return ;

		Pattern checkRegex = Pattern.compile(hiNinja, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher = checkRegex.matcher(banana);

		Pattern checkRegex1 = Pattern.compile(howsItGoing, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher1 = checkRegex1.matcher(banana);

		if (regexMatcher.find()) {
			double a = Math.random() * 3;
			if (a < 1)
				toReturn = "Hey " + user + "!";
			else if (a < 2)
				toReturn = "Welcome " + user + "!";
			else if (a < 3)
				toReturn = "Greetings " + user + "!";

		}
		if (regexMatcher1.find()) {
			double a = Math.random() * 3;
			if (a < 1)
				toReturn += " I'm just chilling.";
			else if (a < 2)
				toReturn += " I'm having me some fun.";
			else if (a < 3)
				toReturn += " I'm owning noobs.";
		}
		if (toReturn.equals("")) {
			return;
		} else {
			responseList.add(toReturn);
			return;
		}
	}

	private void goodBye(Message message) {
		String toReturn = "";
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return ;
		Pattern checkRegex = Pattern.compile(byeNinja, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()) 
		{
			double rNum = Math.random()*3;
			if (rNum < 1)
			toReturn = ("See ya " + user + "!");
			else
			if (rNum < 2)
			toReturn = ("Bye " + user + "!");
			else
			if (rNum < 3)
			toReturn = ("Don't leave " + user + "!");
			responseList.add(toReturn);
		}
	}
	
	private void attack(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(attack, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()  && user.equals("ninjamonkeyrulez")) 
		{
			responseList.add(regexMatcher3.group(2));
		}
	}
	
	private void stay(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(stay, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()) 
		{
			if (blackJack.contains(user))
			{
				int total = blackJack.search(user).getTotal();
				int total2 = 0;
				Stack s = blackJack.search(user).getStack();
				blackJack.insert(s, user + "ninja_ai");
				while(total2 < 22)
				{
					Card c = s.pop();
					int cardVal = cardValue(c, blackJack, user + "ninja_ai", total2);
					total2 += cardVal;
					blackJack.search(user + "ninja_ai").addTotal(cardVal);
					if(total2 < 22 && total2 >= total)
					{
						responseList.add(user + " has lost to the dealer with the dealer receiving " + total2 + ".");
						break;
					}
					else
					if(total2 > 21)
						responseList.add("Dealer has busted. " + user + " has won!");
					
				}

			}
			else
			responseList.add(user + ", you aren't being dealt right now.");
		
		blackJack.delete(user + "ninja_ai");
		blackJack.delete(user);
		}
	}
	
	private void hit(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(hit, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()) 
		{
			if (blackJack.contains(user))
			{
				int total = blackJack.search(user).getTotal();
				Stack n = blackJack.search(user).getStack();
				Card a = n.pop();
				int cardVal = cardValue(a, blackJack, user, total);
				total += cardVal;
				blackJack.search(user).addTotal(cardVal);
				responseList.add(user + " was dealt " + a.toString() + ".");
				if(checkBust(blackJack, user, total))
					blackJack.delete(user);
				else
				if(total == 21)
				{
					responseList.add(user + " hit 21, and has won!");
					blackJack.delete(user);
				}
				else
					responseList.add(user + " has a total of " + total + ". !hit, or !stay?");
			}
			else
			responseList.add(user + ", you aren't being dealt right now.");
		}
	}
	
	private boolean checkBust(LinkedList b, String u, int t)
	{
		if(t >= 22)
		{
			if(blackJack.search(u).getAceCount() != 0)
			{
				blackJack.search(u).setAceCount(-1);
				blackJack.search(u).addTotal(-10);
				t -= 10;
				checkBust(b, u, t);
			}
			else
			{
			responseList.add(u + " has busted with " + t + ".");
			return true;
			}
		}
		return false;
	}
	
	private void deal(Message message) {
		Stack s = new Stack();
		int count = 0;
		int b = (int)(Math.random()*52 + 1);
		int m = primes[(int)(Math.random()*12)];
		Card[] cards = new Card[52];
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(deal, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		
		if (regexMatcher3.find())
		{
			
		if (blackJack.contains(user))
		{
			responseList.add(user + ", you are already being dealt.");
			return;
		}
		count = 0;
		for (int x = 2; x < 15; x++)
			for (int y = 0; y < 4; y++)
			{
				if(y == 0)
				cards[count] = new Card("c", x);
				else
				if(y == 1)
				cards[count] = new Card("s", x);
				else
				if(y == 2)
				cards[count] = new Card("d", x);
				else
				cards[count] = new Card("h", x);
				count++;
			}
		
		for(int a = 0; a < 4; a++)
		{
		cards = doShuffle(cards, m, b);
		b = (int)(Math.random()*52 + 1);
		m = primes[(int)(Math.random()*12)];
		}
		
		s = createStack(cards);
		
		blackJack.insert(s, user);
		dealHand(user);
		}
	}
	
	public void dealHand(String user)
	{
		int total = 0;
		Stack n = blackJack.search(user).getStack();
		Card a = n.pop();
		total += cardValue(a, blackJack, user, total);
		Card b = n.pop();
		total += cardValue(b,blackJack, user, total);
		blackJack.search(user).addTotal(total);
		responseList.add(user + " was dealt " + a.toString() + " and " + b.toString() + ", total is " + total);
		if(total == 21)
		{
			responseList.add(user + " hit 21, and has won!");
			blackJack.delete(user);
		}
	}
	
	public int cardValue(Card c, LinkedList b, String user, int total)
	{
		if(c.getValue() <= 10)
		{
			return c.getValue();
		}
		else
		if(c.getValue() < 14)
		{
			return 10;
		}
		else
		if(total >= 11)
		return 1;
		else
		{
		b.search(user).setAceCount(1);
		return 11;
		}
	}
	
	public Stack createStack(Card[] deck)
	{
		Stack s = new Stack();
		for (int x = 0; x < 52; x++) {
			s.push(deck[x]);
		}
		return s;
	}
	
	public Card[] doShuffle(Card[] oldDeck, int m, int b) {
		  Card[] newDeck = new Card[52];
		  int y; 
		  for (int x = 0; x < 52; x++) {
		    y = (m * x + b) % 52;
		    newDeck[y] = oldDeck[x];
		  }
		  return newDeck;
		}
	
	private void back(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(back, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()) 
		{
			responseList.add("Welcome back " + user + "!");
		}
	}
	
	private void discord(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(discord, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()) 
		{
			responseList.add("discordapp.com/invite/0a8ZynBfiUl3EeQk");
		}
	}
	
	private void howMuch(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(howMuch, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		if (regexMatcher3.find()) 
		{
			responseList.add("He makes roughly 5m/h, or roughly 8m/h with 2 accounts.");
		}
	}
	
	private void dice(Message message) {
		String theMessage = message.getMessage();
		String user = message.getNick();
		if (theMessage == null | user == null)
			return;
		Pattern checkRegex = Pattern.compile(dice, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher3 = checkRegex.matcher(theMessage);
		/*if(user.equals("ninjamonkeyrulez") && regexMatcher3.find())
		{
			responseList.add(user + " rolled over 9000!!@!@!@!.");
		}
		else*/
		if (regexMatcher3.find()) 
		{
			int math = (int)(Math.random()*100);
			responseList.add(user + " rolled a " + (math+1) + ".");
		}
	}
	
	private void answerToLife(Message message){
		String theMessage = message.getMessage();
		if (theMessage == null)
			return ;
		Pattern checkRegex = Pattern.compile(meanLife, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher4 = checkRegex.matcher(theMessage);
		if (regexMatcher4.find())
		{
			responseList.add("42");
		}
	}

	private void ninjaMonkey(Message message)
	{
		String theMessage = message.getMessage();
		if (theMessage == null)
			return;
		Pattern checkRegex = Pattern.compile(ninjaMonkey, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher5 = checkRegex.matcher(theMessage);
		if (regexMatcher5.find())
		{
			responseList.add("Rulez");
		}
	}

	
	private void mathIsFun(Message message)
	{
		String theMessage = message.getMessage();
		if (theMessage == null)
			return;
		Pattern checkRegex = Pattern.compile(mathA, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher7 = checkRegex.matcher(theMessage);
		Pattern checkRegex1 = Pattern.compile(mathS, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher8 = checkRegex1.matcher(theMessage);
		Pattern checkRegex2 = Pattern.compile(mathM, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher9 = checkRegex2.matcher(theMessage);
		Pattern checkRegex3 = Pattern.compile(mathD, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher10 = checkRegex3.matcher(theMessage);
		boolean found = regexMatcher7.find();
		if(found && Double.valueOf(regexMatcher7.group(1)) == 9 && Double.valueOf(regexMatcher7.group(4)) == 10)
		{
			responseList.add("It's 19, not 21 you uncultured swine.");
		}
		else
		if (found)
		{
			String a = regexMatcher7.group(1);
			String b = regexMatcher7.group(4);
			double c = Double.valueOf(a);
			double d = Double.valueOf(b);
			double e = c + d;
			responseList.add(String.valueOf(e));
		}
		else
		if (regexMatcher8.find())
		{
			String a = regexMatcher8.group(1);
			String b = regexMatcher8.group(4);
			double c = Double.valueOf(a);
			double d = Double.valueOf(b);
			double e = c - d;
			responseList.add(String.valueOf(e));
		}
		else
		if (regexMatcher9.find())
		{
			String a = regexMatcher9.group(1);
			String b = regexMatcher9.group(4);
			double c = Double.valueOf(a);
			double d = Double.valueOf(b);
			double e = c * d;
			responseList.add(String.valueOf(e));
		}
		else
		if (regexMatcher10.find())
		{
			String a = regexMatcher10.group(1);
			String b = regexMatcher10.group(4);
			double c = Double.valueOf(a);
			double d = Double.valueOf(b);
			if (d != 0.0)
			{
			double e = c / d;
			responseList.add(String.valueOf(e));
			}
			else
			responseList.add("Can't divide by 0");
			}
	}

	private void mirrorResponse(Message message) {
		String toReturn = "";
		String banana = message.getMessage();
		String user = message.getNick();

		if (banana == null | user == null)
			return ;

		Pattern checkRegex = Pattern.compile(mirrorResponse, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher2 = checkRegex.matcher(banana);

		if (regexMatcher2.find()) {
			toReturn = ("I " + regexMatcher2.group(1) + " you too " + user + "!");
			 responseList.add(toReturn);
			 return;
		} else
			return;
	}
	
	private void whatIsLove(Message message)
	{
		String theMessage = message.getMessage();
		if (theMessage == null)
			return;
		Pattern checkRegex = Pattern.compile(love, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher6 = checkRegex.matcher(theMessage);
		if (regexMatcher6.find())
		{
			responseList.add("Baby don't hurt me!");
		}
	}
	
	private void whatIsLove2(Message message)
	{
		String theMessage = message.getMessage();
		if (theMessage == null)
			return;
		Pattern checkRegex = Pattern.compile(love2, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher6 = checkRegex.matcher(theMessage);
		if (regexMatcher6.find())
		{
			responseList.add("No more!");
		}
	}
	
	private void whoWins(Message message)
	{
		String theMessage = message.getMessage();
		if (theMessage == null)
			return;
		Pattern checkRegex = Pattern.compile(win, Pattern.CASE_INSENSITIVE);
		Matcher regexMatcher6 = checkRegex.matcher(theMessage);
		if (regexMatcher6.find())
		{
			responseList.add("NinjaMonkey's gonna win!");
		}
	}

	@Override
	public void newMessage(Message message) {
		ping = message.getPong();
		if(ping != null)
		{
			Message finalPing = message.createPingPong(ping);
			daemon.sendMessages(finalPing);
			return;
		}
		mirrorResponse(message);
		getResponse(message);
		stay(message);
		mathIsFun(message);
		howMuch(message);
		ninjaMonkey(message);
		hit(message);
		deal(message);
		answerToLife(message);
		goodBye(message);
		back(message);
		discord(message);
		whatIsLove(message);
		whatIsLove2(message);
		whoWins(message);
		attack(message);
		dice(message);
		if(!(responseList.isEmpty()))
		for(int x = 0; x < responseList.size(); x++)
		if (responseList.get(x) != null) {
			Message reply = message.createReplyMessage(this.name, responseList.get(x));
			daemon.sendMessages(reply);
		}
		if(!responseList.isEmpty())
		responseList.clear();
	}

}
