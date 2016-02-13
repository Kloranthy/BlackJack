public class Player
{
	// instance variables
	private String name;
	private int score;

	// constructor
	public Player( String name )
	{
		this.name = name;
		score = 0;
	}

	public Player()
	{
		this( "Steve" );
	}

	// public methods
	public String getName()
	{
		return name;
	}

	public boolean isBust()
	{
		return score > 21;
	}

	public int getScore()
	{
		return score;
	}

	public void add( int value )
	{
		score += value;
	}

	public void resetScore()
	{
		score = 0;
	}
}