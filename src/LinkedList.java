
class LinkedList
{
	private Node top;
	private Node end;
	private int count;
	
	public LinkedList( ) // Constructor creates dummy nodes
	  {
	    count = 0;
	  }

	public Node search(String user) 
	{
		if(count > 0)
		{
		Node curr = top;
		
		while (curr != null)
				{
					if (curr.getUsername().equals(user)){
						return curr;
					}
					curr = curr.forward;
				}
		}
		return null;
	}
	
	public void insert(Stack s, String user) 
	{
		if(count == 0)
		{
			Node newNode = new Node(user, s, 0, 0, null, null);
			top = newNode;
			addCount();
		}
		else
			if(count == 1)
			{
				Node newNode = new Node(user, s, 0, 0, top, null);
				end = newNode;
				top.setForward(end);
				addCount();
			}
			else
			{
				Node newNode = new Node(user, s, 0, 0, null, top);
				top.setBack(newNode);
				top = newNode;
				addCount();
			}
	}
	
	public void addCount()
	{
		count++;
	}
	
	public boolean contains(String user)
	{
		Node curr = top;
		for(int x = 0;x < count; x++)
		{
			if(curr.getUsername().equals(user))
				return true;
			curr = curr.getForward();
		}
		return false;
	}
	
	public void delete(String user) 
	{
		if(count > 1)
		{
		Node curr = top;
		if (curr.getUsername().equals(user)){
			top = top.forward;
			top.setBack(null);
			count--;
		}
		else
		{
		curr = curr.forward;	
		while (curr.forward != null)
				{
					if (curr.getUsername().equals(user)){
						curr.back.setForward(curr.forward);
						curr.forward.setBack(curr.back);
						count--;
					}
					curr = curr.forward;
				}
		if (curr.getUsername().equals(user)){
			end = end.back;
			end.forward.setBack(null);
			end.setForward(null);
			count--;
		}
		}
		}
		else
			if(top != null)
			{
				top = null;
				count--;
			}
	} // end of delete
	
	
 /*********************** Node Class ***********************/
	
 class Node
  {
    public String username;
    public Stack stack;
    public int total;
    public int aceCount;
    public Node back;
    public Node forward;


    public Node(String user, Stack newStack,int t, int a, Node newBack, Node newForward) // constructor
    {
      username = user;
      stack = newStack;
      total = t;
      aceCount = a;
      back = newBack;
      forward = newForward;
      
    }
    
    public int getAceCount()
    {
    	return aceCount;
    }
    
    public void setAceCount(int n)
    {
    	aceCount += n;
    }

    public String getUsername()
    { 
      return username;
    }
    
    public int getTotal()
    {
    	return total;
    }
    
    public void addTotal(int v)
    {
    	total += v;
    }
    
    public Stack getStack()
    { 
      return stack;
    }

    public Node getBack()
    {
      return back;
    }
    
    public Node getForward()
    {
      return forward;
    }
    

    public void setBack(Node newBack)
    {
      back = newBack;
    }
    
    public void setForward(Node newForward)
    {
      forward = newForward;
    }

  } // end class Node
 
} // end of LinkedList
