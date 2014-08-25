package comp303.fivehundred.ai;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllBids.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import comp303.fivehundred.ai.random.RandomCardExchangeStrategy;
import comp303.fivehundred.model.*;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/** 
 * @author (Sherry) Shanshan Ruan 260471837
 * This is the test class for RandomBiddingStrategy
 */

public class TestRandomCardExchangeStrategy
{
	Hand pHand;
	
	int pIndex;
	
	RandomCardExchangeStrategy aS;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		pHand = new Hand();
		pHand.add(a4S);
		pHand.add(aJS);
		pHand.add(a5C);
		pHand.add(a9C);
		pHand.add(a5D);
		pHand.add(aAD);
		pHand.add(a8H);
		pHand.add(aKH);
		pHand.add(aLJo);
		pHand.add(aHJo);
		pHand.add(a6S);
		pHand.add(a8S);
		pHand.add(aTC);
		pHand.add(aJC);
		pHand.add(a4D);
		pHand.add(aJD);
		pIndex = 1;
		aS= new RandomCardExchangeStrategy();
	}
	
	@Test
	public void testNotAllPassingBids(){
		exception.expect(AssertionError.class);
		Bid[] pBids = {aBidPass,aBidPass,aBidPass,aBidPass};
		aS.selectCardsToDiscard(pBids, pIndex, pHand);
	}

	@Test
	public void testBidsLength(){
		exception.expect(AssertionError.class);
		Bid[] pBids = {aBid6S,aBidPass};
		aS.selectCardsToDiscard(pBids, pIndex, pHand);
	}
	
	@Test
	public void testpHandSize(){
		pHand.add(a4C);
		Bid[] pBids = {aBid6S,aBidPass,aBid7C,aBid10N};
		exception.expect(AssertionError.class);
		aS.selectCardsToDiscard(pBids, pIndex, pHand);
	}
	
	@Test
	public void testPIndex(){
		Bid[] pBids = {aBid6S,aBidPass,aBid7C,aBid10N};
		pIndex = 6;
		exception.expect(AssertionError.class);
		aS.selectCardsToDiscard(pBids, pIndex, pHand);
	}
	
	@Test
	public void test(){
		Bid[] pBids = {aBid6S,aBidPass,aBid7C,aBid10N};
		assertEquals(aS.selectCardsToDiscard(pBids, pIndex, pHand).size(), 6);
	}
	
	@Test
	public void testRandomBiddingStrategyConstructorOK()
	{
		new RandomCardExchangeStrategy ();
	}	

/*	@Test(expected=NullPointerException.class)
	public void testRandomBiddingStrategySelectCardsToDiscardEmptyInput()
	{
		RandomCardExchangeStrategy randomCardExStrategy = new RandomCardExchangeStrategy ();
		randomCardExStrategy.selectCardsToDiscard(null, 1, null);		
	}
*/
	@Test
	public void testRandomBiddingStrategySelectCardsToDiscard()
	{
		Bid bid1 = new Bid(7, Suit.HEARTS);
		Bid bid2 = new Bid(8, Suit.CLUBS);
		Bid bid3 = new Bid(9, Suit.SPADES);
		Bid bid4 = new Bid(10, Suit.DIAMONDS);
		Bid[] bids = {bid1, bid2, bid3, bid4};
		
		RandomCardExchangeStrategy randomCardExStrategy = new RandomCardExchangeStrategy ();
		Hand h = new Hand();
		h.add(new Card(Rank.FOUR,Suit.SPADES));
		h.add(new Card(Rank.FIVE,Suit.CLUBS));
		h.add(new Card(Rank.SIX,Suit.DIAMONDS));
		h.add(new Card(Rank.FOUR,Suit.DIAMONDS));
		h.add(new Card(Rank.FIVE,Suit.SPADES));
		h.add(new Card(Rank.SIX,Suit.CLUBS));
		h.add(new Card(Rank.FOUR,Suit.CLUBS));
		h.add(new Card(Rank.FIVE,Suit.DIAMONDS));
		h.add(new Card(Rank.SIX,Suit.SPADES));
		h.add(new Card(Rank.FOUR, Suit.HEARTS));
		h.add(new Card(Rank.FIVE, Suit.CLUBS));
		h.add(new Card(Rank.SIX, Suit.HEARTS));
		h.add(new Card(Rank.EIGHT, Suit.CLUBS));
		h.add(new Card(Rank.EIGHT, Suit.HEARTS));
		h.add(new Card(Rank.EIGHT, Suit.DIAMONDS));
		h.add(new Card (Rank.NINE, Suit.DIAMONDS));
		h.add(new Card(Rank.NINE, Suit.CLUBS));

		CardList cl = randomCardExStrategy.selectCardsToDiscard(bids, 1, h);	
		assertTrue(cl.size()==6);
	}
	

}
