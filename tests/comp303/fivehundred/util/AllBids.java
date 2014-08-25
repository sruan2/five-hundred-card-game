package comp303.fivehundred.util;
import comp303.fivehundred.model.Bid;

public interface AllBids
{
	// pIndex in increasing order
	public static final Bid aBid6S = new Bid(6,Card.Suit.SPADES); //pIndex = 0
	public static final Bid aBid6C = new Bid(6,Card.Suit.CLUBS);
	public static final Bid aBid6C2= new Bid (1);
	public static final Bid aBid6D = new Bid(6,Card.Suit.DIAMONDS);
	public static final Bid aBid6H = new Bid(6,Card.Suit.HEARTS);
	public static final Bid aBid6N = new Bid(4);
	
	public static final Bid aBid7S = new Bid(7,Card.Suit.SPADES); 
	public static final Bid aBid7C = new Bid(7,Card.Suit.CLUBS);
	public static final Bid aBid7D = new Bid(7,Card.Suit.DIAMONDS);
	public static final Bid aBid7H = new Bid(7,Card.Suit.HEARTS);
	public static final Bid aBid7N = new Bid(9);
	
	public static final Bid aBid8S = new Bid(8,Card.Suit.SPADES); 
	public static final Bid aBid8C = new Bid(8,Card.Suit.CLUBS);
	public static final Bid aBid8D = new Bid(8,Card.Suit.DIAMONDS);
	public static final Bid aBid8H = new Bid(8,Card.Suit.HEARTS);
	public static final Bid aBid8N = new Bid(14);
	
	public static final Bid aBid9S = new Bid(9,Card.Suit.SPADES); 
	public static final Bid aBid9C = new Bid(9,Card.Suit.CLUBS);
	public static final Bid aBid9D = new Bid(9,Card.Suit.DIAMONDS);
	public static final Bid aBid9H = new Bid(9,Card.Suit.HEARTS);
	public static final Bid aBid9N = new Bid(19);
	
	public static final Bid aBid10S = new Bid(10,Card.Suit.SPADES); 
	public static final Bid aBid10C = new Bid(10,Card.Suit.CLUBS);
	public static final Bid aBid10D = new Bid(10,Card.Suit.DIAMONDS);
	public static final Bid aBid10H = new Bid(10,Card.Suit.HEARTS);
	public static final Bid aBid10N = new Bid(24); // pIndex = 24
	
	public static final Bid aBidPass = new Bid();
	
}
