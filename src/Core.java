import java.util.ArrayList;
import java.util.Collections;

public class Core
{
	// instance variables
	private IOSystem io;
	private boolean running;
	private ArrayList<Card> alDeck;
	private Player plyr, dlr;

	// constructor
	public Core()
	{
		io = new IOSystem();
		plyr = new Player();
		dlr = new Player( "Dealer" );
	}

	// public methods
	public static void main( String[] args )
	{
		Core c = new Core();
		c.start();
	}

	// private methods
	private void start()
	{
		running = true;
		io.print( "\nThe game begins!\n" );
		while( running )
		{
			setupDeck();
			doDealerTurn();
			doPlayerTurn();
			doRoundResults();
			askContinue();
		}
	}

	private void setupDeck()
	{
		alDeck = new ArrayList<Card>();
		String s = "";
		for( int i = 0; i < 4; i++ )
		{
			switch( i )
			{
				case 0 : // diamonds
					s = "Diamonds";
					break;
				case 1 : // hearts
					s = "Hearts";
					break;
				case 2 : // spades
					s = "Spades";
					break;
				case 3 : // clubs
					s = "Clubs";
					break;
			}
			for( int v = 1; v <= 13; v++ )
			{
				alDeck.add( new Card( s, v ) );
			}
		}
		Collections.shuffle( alDeck );
	}

	private Card drawCard()
	{
		return alDeck.remove( 0 );
	}

	private void hit( Player p )
	{
		Card drawn = drawCard();
		int value = drawn.getValue();
		String sval;
		if( value > 2 && value < 11 )
		{
			sval = value + "";
		}
		else if( value == 11 )
		{
			sval = "Jack";
			value = 10;
		}
		else if( value == 12 )
		{
			sval = "Queen";
			value = 10;
		}
		else if( value == 13 )
		{
			sval = "King";
			value = 10;
		}
		else
		{
			sval = "Ace";
			if( p.getScore() <= 10 )
			{
				value = 11;
			}
		}
		p.add( value );
		String suit = drawn.getSuit();
		io.print( p.getName() + " drew the " + sval + " of " + suit + "." );
		io.print( p.getName() + "'s score is currently " + p.getScore() );
	}

	private void doDealerTurn()
	{
		while( dlr.getScore() < 17 )
		{
			hit( dlr );
			if( dlr.isBust() )
			{
				io.print( "Dealer is bust!" );
			}
		}
	}

	private void doPlayerTurn()
	{
		String input = "";
		while( plyr.getScore() < 21 && !input.equalsIgnoreCase( "stand" ) )
		{
			io.print( "\nSelect your move." );
			while( !io.hasInput() )
			{
				try
				{
					Thread.sleep( 500 );
				}
				catch( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
			input = io.getInput();
			switch( input )
			{
				case "hit" :
					hit( plyr );
					if( plyr.isBust() )
					{
						io.print( "Player is bust!" );
						continue;
					}
					break;
				case "stand" :
					continue;
			}
		}
	}

	private void doRoundResults()
	{
		if( plyr.isBust() && !dlr.isBust() )
		{
			io.print( "The Player is bust, leaving the Dealer to win by default." );
		}
		else if( dlr.isBust() && !plyr.isBust() )
		{
			io.print( "The Dealer is bust, leaving the Player to win by default." );
		}
		else if( dlr.getScore() > plyr.getScore() )
		{
			io.print( "The Dealer wins." );
		}
		else if( plyr.getScore() > dlr.getScore() )
		{
			io.print( "The Player wins." );
		}
		else
		{
			io.print( "Game ends in a draw." );
		}
	}

	private void askContinue()
	{
		io.print( "\nDo you want to continue playing?" );
		String answer = "";
		while( !answer.equalsIgnoreCase( "yes" ) && !answer.equalsIgnoreCase( "no" ) )
		{
			while( !io.hasInput() )
			{
				try
				{
					Thread.sleep( 500 );
				}
				catch( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
			answer = io.getInput();
		}
		if( answer.equalsIgnoreCase( "yes" ) )
		{
			setupDeck();
			plyr.resetScore();
			dlr.resetScore();
		}
		else
		{
			running = false;
		}
	}
}