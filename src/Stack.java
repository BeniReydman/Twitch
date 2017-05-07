
public class Stack 
{
	public Node top;
	
	public Stack() 
	  {
	    top = null;
	  }
	
	public void push(Card newCard)
	{
		Node newNode = new Node(newCard, null);
		if(top == null)
			top = newNode;
		else
		{
			newNode.setNext(top);
			top = newNode;
		}		
	}
	
	public Card pop()
	{
		Card returnDigit = null;
		
		if(!isEmpty())
		{
		returnDigit = top.getCard();
		top = top.getNext();
		}
		
		return returnDigit;
	}
	
	public Card top()
	{
		Card returnDigit = null;
		
		if(!isEmpty())
		returnDigit = top.getCard();
		
		return returnDigit;
	}
	
	public boolean isEmpty()
	{
		return (top == null);
	}
	
	/*********************** Node Class ***********************/
	
	 private class Node
	  {
		private Card card;
	    private Node next;


	    public Node(Card c, Node newNext)
	    {
	    	card = c;
	    	next = newNext;
	      
	    }

	    public Card getCard()
	    { 
	      return card;
	    }

	    public Node getNext()
	    {
	      return next;
	    }
	    
	    public void setNext(Node newNext)
	    {
	      next = newNext;
	    }

	  } // End of Node Class
	 
} // End of Stack Class
