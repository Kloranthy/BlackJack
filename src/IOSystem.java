import java.util.Scanner;

public class IOSystem
{
	// instance variables
	private Scanner sc;

	// constructor
	public IOSystem()
	{
		sc = new Scanner( System.in );
	}

	// public methods
	public boolean hasInput()
	{
		return sc.hasNext();
	}

	public String getInput()
	{
		return sc.next();
	}

	public void print( String s )
	{
		System.out.println( s );
	}
}