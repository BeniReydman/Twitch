
public class Card {

	private String suit;
	private int value;
	
	public Card (String s, int v)
	{
		suit = s;
		value = v;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString()
	{
		String v = "" + value;
		String s = "";
		if(value == 11)
			v = "Jack";
		else
		if(value == 12)
			v = "Queen";
		else
		if(value == 13)
			v = "King";
		else
		if(value == 14)
			v = "Ace";
		
		if(suit.equals("s"))
			s = "Spades";
		else
		if(suit.equals("d"))
			s = "Diamonds";
		else
		if(suit.equals("h"))
			s = "Hearts";
		else
		if(suit.equals("c"))
			s = "Clubs";
		return v + " of " + s;
	}
	
}
