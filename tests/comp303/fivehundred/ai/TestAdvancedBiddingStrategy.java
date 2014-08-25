package comp303.fivehundred.ai;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllBids.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.ai.advanced.AdvancedBiddingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestAdvancedBiddingStrategy
{

	Bid pBid;
	Bid[] pBids0 = new Bid[0];
	Bid[] pBids = new Bid[3];

	Hand pHand;
	AdvancedBiddingStrategy bidding;

	@Before
	public void init()
	{

		pHand = new Hand();
		bidding = new AdvancedBiddingStrategy();
	}

	// has an excellent deal, 8 contract, in D, no one has bid
	@Test
	public void test01()
	{
		pHand.add(aHJo);
		pHand.add(aJD);
		pHand.add(aAD);
		pHand.add(aKD);
		pHand.add(aQD);
		pHand.add(aJH);
		pHand.add(aTD);
		pHand.add(a5D);
		pHand.add(a5H);
		pHand.add(a5C);


		assertEquals(bidding.selectBid(pBids0, pHand), aBid8D);
	}
	
	// has a hand good for a 8 contract, but partner already bid 9 of different suit
	// partners bid worth more points
	@Test
	public void test04()
	{
		pHand.add(aHJo);
		pHand.add(aJD);
		pHand.add(aAD);
		pHand.add(aKD);
		pHand.add(aQD);
		pHand.add(aJH);
		pHand.add(aTD);
		pHand.add(a5D);
		pHand.add(a5H);
		pHand.add(a5C);
		
		// opponent has bid Diamonds
		pBids[0] = new Bid(0);
		pBids[1] = new Bid(20);
		pBids[2] = new Bid(1);
		assertEquals(bidding.selectBid(pBids, pHand), aBidPass);
	}
	// extreme: should not bid
	@Test
	public void test03()
	{
		pHand.add(a4C);
		pHand.add(a4D);
		pHand.add(a4H);
		pHand.add(a4S);
		pHand.add(a5C); 
		pHand.add(a5D); 
		pHand.add(a5H); 
		pHand.add(a5S); 
		pHand.add(a6C); 
		pHand.add(a6D);
		
		// opponent has bid Diamonds
		pBids[0] = new Bid(0);
		pBids[1] = new Bid(5);
		pBids[2] = new Bid(8);
		assertEquals(bidding.selectBid(pBids, pHand), aBidPass);
	}

}
