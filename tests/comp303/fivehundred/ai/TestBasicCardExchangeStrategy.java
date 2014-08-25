package comp303.fivehundred.ai;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllBids.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.ai.basic.BasicCardExchangeStrategy;

/**
 * @author Shanshan Ruan 260471837
 */

public class TestBasicCardExchangeStrategy
{
	Bid[] pBidsN = {aBid6S, aBidPass,aBid7N,aBidPass}; //contract is No trump
	Bid[] pBidsS = {aBid6H, aBid7D,aBid8C,aBid10S}; // contract is Spades
	Bid[] pBidsC = {aBid7S, aBidPass,aBid8C,aBidPass}; // contract is Clubs
	Bid[] pBidsD = {aBid6C, aBid7D,aBidPass,aBid9D}; // contract is Diamonds
	Bid[] pBidsH = {aBid6D, aBid7H,aBidPass,aBidPass}; // contract is Hearts
	int pIndex;
	Hand pHand;
	BasicCardExchangeStrategy player;

	@Before
	public void init()
	{
		pIndex = 0;
		pHand = new Hand();
		player = new BasicCardExchangeStrategy();
	}

	@Test
	public void test1x()
	{
		pHand.add(a4C);
		pHand.add(a5C);
		pHand.add(a6S);
		pHand.add(a6H);
		pHand.add(a9D);
		pHand.add(aTC);
		pHand.add(aJS);
		pHand.add(aJC);
		pHand.add(aJD);
		pHand.add(aJH);
		pHand.add(aQS);
		pHand.add(aKS);
		pHand.add(aKC);
		pHand.add(aKH);
		pHand.add(aLJo);
		pHand.add(aHJo);
		
		//no trump
		assertEquals(player.selectCardsToDiscard(pBidsN, pIndex, pHand).toString(),"<4C,5C,6S,6H,9D,TC>");
		//spades
		assertEquals(player.selectCardsToDiscard(pBidsS, pIndex, pHand).toString(),"<4C,5C,6H,9D,TC,JD>");
		//clubs
		assertEquals(player.selectCardsToDiscard(pBidsC, pIndex, pHand).toString(),"<6S,6H,9D,JD,JH,QS>");
		//diamonds
		assertEquals(player.selectCardsToDiscard(pBidsD, pIndex, pHand).toString(),"<4C,5C,6S,6H,TC,JS>");
		//hearts
		assertEquals(player.selectCardsToDiscard(pBidsH, pIndex, pHand).toString(),"<4C,5C,6S,9D,TC,JS>");
	}
	
	@Test
	public void test2()
	{
		// Cards are added in a random order
		pHand.add(aQS);
		pHand.add(aQC);
		pHand.add(aKH);
		pHand.add(aLJo);
		pHand.add(aHJo);
		pHand.add(a9D);
		pHand.add(aTS);
		pHand.add(aTC);
		pHand.add(aJS);
		pHand.add(aJC);
		pHand.add(aJD);
		pHand.add(aJH);
		pHand.add(aQH);
		pHand.add(aKS);
		pHand.add(aKC);
		pHand.add(aKD);
		
		//no trump
		assertEquals(player.selectCardsToDiscard(pBidsN, pIndex, pHand).toString(),"<9D,TS,TC,JS,JC,JD>");
		//spades
		assertEquals(player.selectCardsToDiscard(pBidsS, pIndex, pHand).toString(),"<9D,TC,JD,JH,QC,QH>");
		//clubs
		assertEquals(player.selectCardsToDiscard(pBidsC, pIndex, pHand).toString(),"<9D,TS,JD,JH,QS,QH>");
		//diamonds
		assertEquals(player.selectCardsToDiscard(pBidsD, pIndex, pHand).toString(),"<TS,TC,JS,JC,QS,QC>");
		//hearts
		assertEquals(player.selectCardsToDiscard(pBidsH, pIndex, pHand).toString(),"<9D,TS,TC,JS,JC,QS>");
	}
	
	@Test
	public void test3x()
	{
		pHand.add(a4C);
		pHand.add(a5C);
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a8C);
		pHand.add(a9C);
		pHand.add(aTC);
		pHand.add(aJS);
		pHand.add(aJC);
		pHand.add(aJD);
		pHand.add(aJH);
		pHand.add(aQC);
		pHand.add(aKC);
		pHand.add(aKH);
		pHand.add(aLJo);
		pHand.add(aHJo);
		
		//no trump
		assertEquals(player.selectCardsToDiscard(pBidsN, pIndex, pHand).toString(),"<4C,5C,6C,7C,8C,9C>");
		//spades
		assertEquals(player.selectCardsToDiscard(pBidsS, pIndex, pHand).toString(),"<4C,5C,6C,7C,8C,9C>");
		//clubs
		assertEquals(player.selectCardsToDiscard(pBidsC, pIndex, pHand).toString(),"<JD,JH,KH,4C,5C,6C>");
		//diamonds
		assertEquals(player.selectCardsToDiscard(pBidsD, pIndex, pHand).toString(),"<4C,5C,6C,7C,8C,9C>");
		//hearts
		assertEquals(player.selectCardsToDiscard(pBidsH, pIndex, pHand).toString(),"<4C,5C,6C,7C,8C,9C>");
	}
	
private BasicCardExchangeStrategy aStrategy = new BasicCardExchangeStrategy();
	
	/**
	 * A basic test to check the normal conditions of the strategy.
	 * Here the strategy is applied with SPADE as trump and there's
	 * not jack of clubs.
	 * If this fails, there's probably a major bug in the code.
	 */
	@Test 
	public void test1()
	{
		Bid[] lBids = {new Bid(7,Suit.SPADES), new Bid(), new Bid(), new Bid() };
		int lIndex = 0;
		Hand lHand = new Hand();
		lHand.add(new Card(Rank.FIVE,Suit.SPADES));
		lHand.add(new Card(Rank.KING,Suit.SPADES));
		lHand.add(new Card(Rank.JACK,Suit.SPADES));
		lHand.add(new Card(Rank.ACE,Suit.SPADES));
		lHand.add(new Card(Rank.JACK,Suit.HEARTS)); //
		lHand.add(new Card(Rank.NINE,Suit.DIAMONDS)); //
		lHand.add(new Card(Rank.SIX,Suit.SPADES));
		lHand.add(new Card(Rank.EIGHT,Suit.SPADES));
		lHand.add(new Card(Joker.HIGH));
		lHand.add(new Card(Rank.ACE,Suit.CLUBS)); //
		lHand.add(new Card(Rank.NINE,Suit.SPADES));
		lHand.add(new Card(Rank.TEN,Suit.SPADES));
		lHand.add(new Card(Rank.QUEEN,Suit.HEARTS));
		lHand.add(new Card(Rank.QUEEN,Suit.DIAMONDS)); //
		lHand.add(new Card(Rank.KING,Suit.DIAMONDS)); //
		lHand.add(new Card(Joker.LOW)); 
		CardList lCards = aStrategy.selectCardsToDiscard(lBids, lIndex, lHand);
		assertEquals( 6, lCards.size() );
		assertTrue(contains(lCards,AllCards.aJH));
		assertTrue(contains(lCards,AllCards.a9D));
		assertTrue(contains(lCards,AllCards.aAC));
		assertTrue(contains(lCards,AllCards.aQD));
		assertTrue(contains(lCards,AllCards.aKD));
		assertTrue(contains(lCards,AllCards.aQH));
		
	}
	
	/**
	 * Another test for normal conditions. Here some of the
	 * ranks are equal.
	 */
	@Test 
	public void test2x()
	{
		Bid[] lBids = {new Bid(6,Suit.SPADES), new Bid(6,Suit.HEARTS), new Bid(), new Bid() };
		int lIndex = 0;
		Hand lHand = new Hand();
		lHand.add(AllCards.a6D); // D
		lHand.add(AllCards.a7D); // D
		lHand.add(AllCards.a7H); 
		lHand.add(AllCards.a7C); // D
		lHand.add(AllCards.a7S); // D
		lHand.add(AllCards.a8S); // D
		lHand.add(AllCards.a8C); // D
		lHand.add(AllCards.aLJo);
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.a8H);
		lHand.add(AllCards.aAS);
		lHand.add(AllCards.aAH);
		lHand.add(AllCards.aAC);
		lHand.add(AllCards.aAD);
		lHand.add(AllCards.aKD);
		lHand.add(AllCards.aJC);
		CardList lCards = aStrategy.selectCardsToDiscard(lBids, lIndex, lHand);
		assertEquals( 6, lCards.size() );
		assertTrue(contains(lCards,AllCards.a6D));
		assertTrue(contains(lCards,AllCards.a7D));
		assertTrue(contains(lCards,AllCards.a7C));
		assertTrue(contains(lCards,AllCards.a7S));
		assertTrue(contains(lCards,AllCards.a8S));
		assertTrue(contains(lCards,AllCards.a8C));
		
	}
	
	/**
	 * This test ensures that Jack swaps are taken into account.
	 */
	@Test 
	public void test3()
	{
		Bid[] lBids = {new Bid(6,Suit.SPADES), new Bid(6,Suit.HEARTS), new Bid(), new Bid() };
		int lIndex = 0;
		Hand lHand = new Hand();
		lHand.add(AllCards.aJH); 
		lHand.add(AllCards.aJD); 
		lHand.add(AllCards.aQD); // D 
		lHand.add(AllCards.aQS); // D
		lHand.add(AllCards.aKD); // D
		lHand.add(AllCards.aKS); // D
		lHand.add(AllCards.aAD); // D
		lHand.add(AllCards.aAS); // D
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.a8H);
		lHand.add(AllCards.a9H);
		lHand.add(AllCards.aTH);
		lHand.add(AllCards.aQH);
		lHand.add(AllCards.aKH);
		lHand.add(AllCards.aAH);
		lHand.add(AllCards.aLJo);
		CardList lCards = aStrategy.selectCardsToDiscard(lBids, lIndex, lHand);
		assertEquals( 6, lCards.size() );
		assertTrue(contains(lCards,AllCards.aQD));
		assertTrue(contains(lCards,AllCards.aQS));
		assertTrue(contains(lCards,AllCards.aKD));
		assertTrue(contains(lCards,AllCards.aKS));
		assertTrue(contains(lCards,AllCards.aAD));
		assertTrue(contains(lCards,AllCards.aAS));
		
	}
	
	/**
	 * This test ensures that no trumps works
	 */
	@Test 
	public void test4()
	{
		Bid[] lBids = {new Bid(6,Suit.SPADES), new Bid(6,Suit.HEARTS), new Bid(6,null), new Bid(7,null) };
		int lIndex = 0;
		Hand lHand = new Hand();
		lHand.add(AllCards.aJH); // D
		lHand.add(AllCards.aJD); // D
		lHand.add(AllCards.aJS); // D  
		lHand.add(AllCards.aQS); 
		lHand.add(AllCards.aKD); 
		lHand.add(AllCards.aKS); 
		lHand.add(AllCards.aAD); 
		lHand.add(AllCards.aAS); 
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.a8H);  // D
		lHand.add(AllCards.a9H);  // D
		lHand.add(AllCards.aTH);  // D
		lHand.add(AllCards.aQH);
		lHand.add(AllCards.aKH);
		lHand.add(AllCards.aAH);
		lHand.add(AllCards.aLJo);
		CardList lCards = aStrategy.selectCardsToDiscard(lBids, lIndex, lHand);
		assertEquals( 6, lCards.size() );
		assertTrue(contains(lCards,AllCards.aJH));
		assertTrue(contains(lCards,AllCards.aJD));
		assertTrue(contains(lCards,AllCards.aJS));
		assertTrue(contains(lCards,AllCards.a8H));
		assertTrue(contains(lCards,AllCards.a9H));
		assertTrue(contains(lCards,AllCards.aTH));
		
	}
	
	/**
	 * This is an advanced test. It checks the situation where almost 
	 * all the cards are trump, and some trump cards are discarded.
	 */
	@Test 
	public void test5()
	{
		Bid[] lBids = {new Bid(6,Suit.SPADES), new Bid(6,Suit.HEARTS), new Bid(7,Suit.HEARTS), new Bid() };
		int lIndex = 0;
		Hand lHand = new Hand();
		lHand.add(AllCards.a4H); // D
		lHand.add(AllCards.a5H); // D
		lHand.add(AllCards.a6H); // D
		lHand.add(AllCards.a7H); // D
		lHand.add(AllCards.a8H); 
		lHand.add(AllCards.a9H); 
		lHand.add(AllCards.aTH); 
		lHand.add(AllCards.aJH); 
		lHand.add(AllCards.aJD);
		lHand.add(AllCards.aQH);  
		lHand.add(AllCards.aKH);  
		lHand.add(AllCards.aAH);  
		lHand.add(AllCards.aLJo);
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.aAS); // D
		lHand.add(AllCards.aAC); // D
		CardList lCards = aStrategy.selectCardsToDiscard(lBids, lIndex, lHand);
		assertEquals( 6, lCards.size() );
		assertTrue(contains(lCards,AllCards.a4H));
		assertTrue(contains(lCards,AllCards.a5H));
		assertTrue(contains(lCards,AllCards.a6H));
		assertTrue(contains(lCards,AllCards.a7H));
		assertTrue(contains(lCards,AllCards.aAS));
		assertTrue(contains(lCards,AllCards.aAC));
		
	}
	
	private static boolean contains(CardList pCardList, Card pCard)
	{
		for( Card card : pCardList)
		{
			if( card.equals(pCard))
			{
				return true;
			}
		}
		return false;
	}
}
