package comp303.fivehundred.model;

import static org.junit.Assert.*;
import static comp303.fivehundred.util.AllCards.*;
import org.junit.Before;
import org.junit.Test;
import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card.Suit;


/**
 * @author Ioannis Fytilis 260482744
 * This is the test class for Bid
 */

public class TestTrick
{
	
	Bid passBid;
	Bid lowBid;
	Bid highBid;
	Bid mediumBid;
	
	@Before
	public void init(){
		lowBid = new Bid(0);// six spades
		highBid = new Bid(24); // ten no trump
		mediumBid = new Bid(7);// seven diamonds
	}
	
	@Test
	public void basicInfo(){
		Trick lowTrick = new Trick(lowBid);
		assertTrue(lowTrick.getTrumpSuit()==Suit.SPADES);
		assertNull(lowTrick.getSuitLed());
		lowTrick.add(a4C);
		assertTrue(lowTrick.getTrumpSuit()==Suit.SPADES);
		assertTrue(lowTrick.getSuitLed()==Suit.CLUBS);
		assertFalse(lowTrick.jokerLed());
		
		Trick highTrick = new Trick(highBid);
		assertNull(highTrick.getTrumpSuit());
		assertNull(highTrick.getSuitLed());
		highTrick.add(aLJo);
		assertNull(highTrick.getTrumpSuit());
		assertNull(highTrick.getSuitLed());
		assertTrue(highTrick.jokerLed());
		
		Trick medTrick = new Trick(mediumBid);
		assertTrue(medTrick.getTrumpSuit()==Suit.DIAMONDS);
		assertNull(medTrick.getSuitLed());
		medTrick.add(a8C);
		assertTrue(medTrick.getTrumpSuit().equals(Suit.DIAMONDS));
		assertTrue(medTrick.getSuitLed().equals(Suit.CLUBS));
		assertFalse(medTrick.jokerLed());
	}
	
	@Test
	public void testCardLed2(){
		Trick aTrick1 = new Trick(lowBid);
		aTrick1.add(a4C);
		aTrick1.add(aAD);
		assertEquals(aTrick1.cardLed(),a4C);
		
		Trick aTrick2 = new Trick(lowBid);
		aTrick2.add(aLJo);
		aTrick2.add(aHJo);
		assertEquals(aTrick2.cardLed(),aLJo);
	}
	
	@Test
	public void testHighest2(){
		Trick aTrick1 = new Trick(lowBid);
		aTrick1.add(aAD);
		aTrick1.add(a4C);
		assertEquals(aTrick1.highest(),aAD);
		assertEquals(aTrick1.winnerIndex(), 0);
		
		aTrick1.add(aLJo);
		assertEquals(aTrick1.highest(),aLJo); 
		assertEquals(aTrick1.winnerIndex(), 2);
		
		Trick aTrick2 = new Trick(highBid);
		aTrick2.add(a5D);
		aTrick2.add(aKC);
		aTrick2.add(aJS);
		assertEquals(aTrick2.highest(),a5D);
		assertEquals(aTrick2.winnerIndex(), 0);
		
		aTrick2.add(aHJo);
		assertEquals(aTrick2.highest(),aHJo);
		
		aTrick1 = new Trick(new Bid(0));
		aTrick1.add(aJH);
		aTrick1.add(aJC);
		aTrick1.add(aJD);
		aTrick1.add(aJS);
		assertEquals(aTrick1.highest(), aJS);
		assertEquals(aTrick1.winnerIndex(), 3);
		
		aTrick1 = new Trick(new Bid(3));
		aTrick1.add(aHJo);
		aTrick1.add(a4H);
		aTrick1.add(aLJo);
		aTrick1.add(aJH);
		assertEquals(aTrick1.highest(), aHJo);
		assertEquals(aTrick1.winnerIndex(), 0);
		
		aTrick1 = new Trick(new Bid(24));
		aTrick1.add(aAH);
		aTrick1.add(aAD);
		aTrick1.add(aAC);
		aTrick1.add(aAS);
		assertEquals(aTrick1.highest(), aAH);
		assertEquals(aTrick1.winnerIndex(), 0);
		assertEquals(aTrick1.winnerIndex(), 0);
		
		aTrick1 = new Trick(new Bid(24));
		aTrick1.add(aAC);
		aTrick1.add(aAD);
		aTrick1.add(aAH);
		aTrick1.add(aAS);
		assertEquals(aTrick1.highest(), aAC);
		assertEquals(aTrick1.winnerIndex(), 0);
		
		Trick a = new Trick (lowBid);// 6 Spades
		a.add(a9C);
		a.add(a9D);
		a.add(aTH);
		a.add(aTD);
		assertEquals(a.highest(),a9C);
		assertEquals(aTrick1.winnerIndex(), 0);
		
		Trick b = new Trick(highBid);
		assertFalse(b.jokerLed());
		b.add(aHJo);
		b.add(aAH);
		b.add(aQD);
		b.add(aLJo);
		assertEquals(b.highest(),aHJo);
		assertEquals(b.winnerIndex(), 0);
		
		Trick c = new Trick(new Bid(2)); // diamonds
		c.add(aJH);
		c.add(a4D);
		c.add(a8D);
		c.add(aAD);
		assertEquals(c.highest(),aJH);
		assertEquals(c.winnerIndex(), 0);
		
		b = new Trick(highBid);
		b.add(aHJo);
		b.add(aAH);
		b.add(aQD);
		b.add(aLJo);
		assertEquals(b.highest(),aHJo);
		assertTrue(b.jokerLed());
		assertEquals(b.winnerIndex(), 0);
		
		b = new Trick(new Bid(0));
		b.add(aQS);
		b.add(aJC);
		b.add(aJS);
		b.add(aAH);
		assertEquals(b.highest(),aJS);
		assertFalse(b.jokerLed());
		assertEquals(b.winnerIndex(), 2);
		
		b = new Trick(new Bid(24));
		b.add(a6S);
		b.add(a8D);
		b.add(a7S);
		b.add(aJC);
		assertEquals(b.winnerIndex(), 2);
		assertEquals(b.highest(),a7S);
		
		
		b = new Trick(new Bid(24));
		b.add(a7C);
		b.add(aHJo);
		b.add(aLJo);
		b.add(aAC);
		assertEquals(b.highest(),aHJo);
		assertEquals(b.winnerIndex(), 1);
		
		b = new Trick(new Bid(0));
		b.add(a8S);
		b.add(aJC);
		b.add(aAS);
		b.add(aJS);
		assertEquals(b.highest(),aJS);
		assertEquals(b.winnerIndex(), 3);
		
		b = new Trick(new Bid(0));
		b.add(aAS);
		b.add(aJC);
		b.add(aAS);
		b.add(a6H);
		assertEquals(b.highest(),aJC);
		assertEquals(b.winnerIndex(), 1);
		
		b = new Trick(new Bid(0));
		b.add(aAS);
		b.add(aJS);
		b.add(aAS);
		b.add(a6H);
		assertEquals(b.highest(),aJS);
		assertEquals(b.winnerIndex(), 1);
		
		b = new Trick(new Bid(0));
		b.add(a4S);
		b.add(aJC);
		b.add(aAH);
		b.add(a6H);
		assertEquals(b.highest(),aJC);
		assertEquals(b.winnerIndex(),1);
		
		b = new Trick(new Bid(2));
		b.add(a4D);
		b.add(aJH);
		b.add(aAH);
		b.add(a6H);
		assertEquals(b.highest(),aJH);
		assertEquals(b.winnerIndex(), 1);
		
		b = new Trick(new Bid(0));
		b.add(a7C);
		b.add(a6S);
		b.add(aAC);
		b.add(a8S);
		assertEquals(b.highest(),a8S);
		assertEquals(b.winnerIndex(), 3);
		
		b = new Trick(new Bid(0));
		b.add(a8D);
		b.add(aJC);
		b.add(aAD);
		b.add(a8S);
		assertEquals(b.highest(),aJC);
		assertEquals(b.winnerIndex(), 1);

	}
	
	Trick emptyTrick;
	Trick nonEmptyTrick;
	Trick nonTrumpTrick;
	Trick jokerLHTrick;
	Trick lowJokerFirstTrick;
	Trick highJokerFirstTrick;
	Trick firstjackTrick;
	
	@Before
	public void setUp() throws Exception
	{
		Bid contract = new Bid(8, Suit.CLUBS);		
		emptyTrick = new Trick(contract); 
		
		nonEmptyTrick = new Trick(contract);
		nonEmptyTrick.add(AllCards.aQH);
		nonEmptyTrick.add(AllCards.a7C);
		
		nonTrumpTrick = new Trick(new Bid(9, null));		
		nonTrumpTrick.add(AllCards.a7D);
		nonTrumpTrick.add(AllCards.aKD);
		nonTrumpTrick.add(AllCards.a6S);
		nonTrumpTrick.add(AllCards.aJH);
		
		jokerLHTrick = new Trick(new Bid(9, Suit.DIAMONDS));
		jokerLHTrick.add(AllCards.aLJo);
		jokerLHTrick.add(AllCards.aKD);
		jokerLHTrick.add(AllCards.a7H);
		jokerLHTrick.add(AllCards.aHJo);

		lowJokerFirstTrick = new Trick(new Bid(9, Suit.HEARTS));
		lowJokerFirstTrick.add(AllCards.aLJo);
		lowJokerFirstTrick.add(AllCards.aKD);
		lowJokerFirstTrick.add(AllCards.aQH);
		lowJokerFirstTrick.add(AllCards.a8H);
		
		highJokerFirstTrick = new Trick(new Bid(9, Suit.HEARTS));
		highJokerFirstTrick.add(AllCards.aHJo);
		highJokerFirstTrick.add(AllCards.aKD);
		highJokerFirstTrick.add(AllCards.aQH);
		highJokerFirstTrick.add(AllCards.aLJo);
		
		firstjackTrick = new Trick(new Bid(9, Suit.SPADES));
		firstjackTrick.add(AllCards.aJC);
	}

	@Test
	public void testGetTrumpSuit()
	{
		assertTrue(Suit.CLUBS.equals(emptyTrick.getTrumpSuit()));
		assertTrue(Suit.CLUBS.equals(nonEmptyTrick.getTrumpSuit()));
		assertTrue(nonTrumpTrick.getTrumpSuit() == null);		
	}

	@Test
	public void testGetSuitLed()
	{
		
		assertTrue(nonEmptyTrick.getSuitLed().equals(Suit.HEARTS));
		assertTrue(nonTrumpTrick.getSuitLed().equals(Suit.DIAMONDS));		
		assertTrue(lowJokerFirstTrick.getSuitLed().equals(Suit.HEARTS));	
		assertTrue(highJokerFirstTrick.getSuitLed().equals(Suit.HEARTS));
		assertTrue(firstjackTrick.getSuitLed().equals(Suit.SPADES));
	}

	@Test
	public void testJokerLed()
	{
		
		assertTrue(!nonEmptyTrick.jokerLed());
		assertTrue(!nonTrumpTrick.jokerLed());		
		assertTrue(lowJokerFirstTrick.jokerLed());	
		assertTrue(highJokerFirstTrick.jokerLed());
	}

	@Test
	public void testCardLed()
	{
		assertTrue(nonEmptyTrick.cardLed().equals(AllCards.aQH));
		assertTrue(nonTrumpTrick.cardLed().equals(AllCards.a7D));		
		assertTrue(lowJokerFirstTrick.cardLed().equals(AllCards.aLJo));	
		assertTrue(highJokerFirstTrick.cardLed().equals(AllCards.aHJo));
	}

	@Test
	public void testHighest()
	{
		assertTrue(nonEmptyTrick.highest().equals(AllCards.a7C));
		assertTrue(nonTrumpTrick.highest().equals(AllCards.aKD));		
		assertTrue(jokerLHTrick.highest().equals(AllCards.aHJo));		
		assertTrue(lowJokerFirstTrick.highest().equals(AllCards.aLJo));
		assertTrue(highJokerFirstTrick.highest().equals(AllCards.aHJo));
	}

	@Test
	public void testWinnerIndex()
	{
		
		assertTrue(nonTrumpTrick.winnerIndex() == 1);		
		assertTrue(jokerLHTrick.winnerIndex() == 3);	
		assertTrue(lowJokerFirstTrick.winnerIndex() == 0);
		assertTrue(highJokerFirstTrick.winnerIndex() == 0);	
	}
	
}
