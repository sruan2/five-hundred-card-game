package comp303.fivehundred.ai;

import comp303.fivehundred.ai.random.RandomBiddingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import static comp303.fivehundred.util.AllBids.*;
import static comp303.fivehundred.util.AllCards.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/** 
 * @author (Sherry) Shanshan Ruan 260471837
 * This is the test class for RandomBiddingStrategy
 */
public class TestRandomBiddingStrategy
{
	private RandomBiddingStrategy aRBS = new RandomBiddingStrategy ();
	private RandomBiddingStrategy aSpecifiedRBS = new RandomBiddingStrategy(0);
	private Bid[] aBidList1 = {aBid6H,aBidPass,aBidPass};
	private Bid[] aBidList2 = {aBid8D,aBidPass,aBid7N};
	private Bid[] aBidList3 = {aBid7H,aBidPass,aBid10H};
	private Bid[] aBidList4 = {aBid10N};
	private Hand pHand = new Hand();
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
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
	}
	
	@Test
	public void testBidsSize(){
		exception.expect(AssertionError.class);
		Bid[] aBidList1 = {aBid6H,aBidPass,aBidPass,aBidPass};
		aRBS.selectBid(aBidList1, pHand);
	}
	
	@Test
	public void testHandSize(){
		exception.expect(AssertionError.class);
		Hand h2 = pHand.clone();
		h2.add(aKD);
		aRBS.selectBid(aBidList1, h2);
	}
	
	@Test
	public void testSelectBid(){
		Bid aBid = aRBS.selectBid(aBidList1, pHand);
		assertTrue(aBid.compareTo(aBid6H)>0 || aBid.isPass());
		assertTrue(aSpecifiedRBS.selectBid(aBidList2, pHand).compareTo(aBid8D)>0);
		assertEquals(aSpecifiedRBS.selectBid(aBidList3, pHand),aBid10N);
		assertEquals(aRBS.selectBid(aBidList4, pHand), aBidPass);
		// test for randomness
		int sum = 0;
		for(int i = 0; i<10000; i++)
		{
			if (aRBS.selectBid(aBidList3, pHand).equals(aBid10N))
			{
				sum++;
			}
		}
		assertTrue(Math.abs(5000 - sum) < 1000);
		
		Bid[] bidList6 = new Bid[0];
		Bid[] bidList7 = {aBidPass};
		assertTrue(aSpecifiedRBS.selectBid(bidList6, pHand).compareTo(aBidPass)>0);
		assertTrue(aSpecifiedRBS.selectBid(bidList7, pHand).compareTo(aBidPass)>0);
	}
	
	@Test
	public void testMultipleCalls()
	{
		new RandomBiddingStrategy (0);	
	}
	
	@Test
	public void testRandomBiddingStrategyCorrectInput()
	{
		new RandomBiddingStrategy (0);
		new RandomBiddingStrategy (40);
		new RandomBiddingStrategy (100);
	}

	@Test
	public void testRandomBiddingStrategySelectBid()
	{
	
		RandomBiddingStrategy input100 = new RandomBiddingStrategy (100);
		
		Bid[] bids = new Bid[3];
		bids[0] = new Bid(7);
		bids[1] = new Bid(8);
		bids[2] = new Bid(9);
		
		
		Bid result = input100.selectBid(bids, pHand);
		assertTrue(result.isPass());
		
	}
	
	@Test	
	public void testCertain10NoTrump()
	{
		//strategy cannot pass
		RandomBiddingStrategy strategy = new RandomBiddingStrategy(0);
		// previous bid is the second strongest in the game
		Bid [] bids = {new Bid(10, Card.Suit.HEARTS)}; 
		Bid selectedBid = strategy.selectBid(bids, pHand);
		//We expect the strategy to produce the strongest bid ( since it cannot pass )
		assertTrue(selectedBid.equals(new Bid(10, null)));
	} 
}
