package comp303.fivehundred.ai;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllBids.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.ai.basic.BasicBiddingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestBasicBiddingStrategy
{

	Bid pBid;
	Bid[] pBids1 = new Bid[0];
	Bid[] pBids2 = new Bid[3];
	Bid[] pBids3 = new Bid[3];
	Bid[] pBids4 = new Bid[3];
	Hand pHand;
	BasicBiddingStrategy bidding;

	@Before
	public void init()
	{
		// not partner nor opponent has bid Diamonds
		pBids2[0] = new Bid(0);
		pBids2[1] = new Bid(5);
		pBids2[2] = new Bid(11);

		// partner has bid Diamonds
		pBids3[0] = new Bid(0);
		pBids3[1] = new Bid(2);
		pBids3[2] = new Bid(11);

		// opponent has bid Diamonds
		pBids4[0] = new Bid(0);
		pBids4[1] = new Bid(5);
		pBids4[2] = new Bid(2);

		pHand = new Hand();
		bidding = new BasicBiddingStrategy();
	}

	/********************************
	 * #1 BIDDING first
	 ********************************/
	// bid 7 diamonds
	// 9 pts= 4(HJo) + 2(RJ) + 1(Ace) +2(threshold)
	@Test
	public void firstBid06()
	{
		pHand.add(aHJo);
		pHand.add(aJD);
		pHand.add(aAD);
		
		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);
		
		pHand.add(a5S);
		pHand.add(a5H);
		pHand.add(a5C);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid7D);
	}

	// bid 7 diamonds
	// 10 pts= 3(LJo) + 2(RJ) + 2(LJ) + 1(K) +2(threshold)
	@Test
	public void firstBid07()
	{
		pHand.add(aLJo);
		pHand.add(aJD);
		pHand.add(aJH);
		pHand.add(aKD);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);

		pHand.add(a7S);
		pHand.add(a5H);
		pHand.add(a5C);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid7D);
	}

	// bid 8 diamonds
	// 11 pts= 3(LJo) + 2(RJ) + 2(LJ) + 1(K) + 1(Ace) +2(threshold)
	@Test
	public void firstBid08()
	{
		pHand.add(aLJo);
		pHand.add(aJD);
		pHand.add(aJH);
		pHand.add(aKD);
		pHand.add(aAD);

		pHand.add(a4D);
		pHand.add(a5D);

		pHand.add(a6C);
		pHand.add(a7S);
		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid8D);
	}

	// bid 8 diamonds
	// 12 pts= 3(LJo) + 2(RJ) + 2(LJ) + 1(K) +1(Q) +1(Ace) +2(threshold)
	@Test
	public void firstBid09()
	{
		pHand.add(aLJo);
		pHand.add(aJD);
		pHand.add(aJH);
		
		pHand.add(aKD);
		pHand.add(aQD);
		pHand.add(aAD);
		pHand.add(a9D);
		
		pHand.add(a6C);
		pHand.add(a7S);
		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid8D);
	}

	// bid 9 diamonds
	// 13 pts= 4(HJo) +3(LJo) + 2(LJ) + 4(threshold)
	@Test
	public void firstBid10()
	{
		pHand.add(aHJo);
		pHand.add(aLJo);
		pHand.add(aJH);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);
		pHand.add(a8D);
		pHand.add(a9D);

		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid9D);
	}

	// FIRST BID - NO TRUMP

	// bid 7 no-trump
	// 9 pts= 4(HJo) + 2(A)+ 2(A) + 1(Q)
	@Test
	public void firstNTBid06()
	{
		pHand.add(aHJo);
		pHand.add(aAS);
		pHand.add(aAD);
		pHand.add(aQS);

		pHand.add(a4H);
		pHand.add(a4S);
		pHand.add(a4C);
		pHand.add(a5D);
		pHand.add(a5C);
		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid7N);
	}

	// bid 7 no-trump
	// 10 pts= 2(A)+ 2(A) +2(A)+ 2(A) + 1(Q) + 1(J)
	@Test
	public void firstNTBid07()
	{
		pHand.add(aAH);
		pHand.add(aAS);
		pHand.add(aAD);
		pHand.add(aAC);
		pHand.add(aQH);
		pHand.add(aJS);

		pHand.add(a4C);
		pHand.add(a5D);
		pHand.add(a5C);
		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid7N);
	}

	// bid 8 no-trump
	// 10 pts= 1(Q) + 1(Q) + 1(Q) + 1(Q) + 1(J) + 1(J) + 1(J) + 1(J) + 2(A)
	@Test
	public void firstNTBid08()
	{
		pHand.add(aQH);
		pHand.add(aQS);
		pHand.add(aQD);
		pHand.add(aQC);
		pHand.add(aJH);
		pHand.add(aJS);
		pHand.add(aJD);
		pHand.add(aJC);
		pHand.add(aAS);
		
		pHand.add(a4H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid7N);
	}

	// bid 8 no-trump
	// 11 pts= 4(HJo) + 3(LJo) + 2(A)+ 1(K) + 1(Q)
	@Test
	public void firstNTBid09()
	{
		pHand.add(aHJo);
		pHand.add(aLJo);
		pHand.add(aAD);
		pHand.add(aKS);
		pHand.add(aQS);

		pHand.add(a4S);
		pHand.add(a4C);
		pHand.add(a5D);
		pHand.add(a5C);
		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid8N);
	}

	// bid 8 no-trump
	// 11 pts= 3(LJo) + 1(K) + 1(Q) + 1(Q) + 1(Q) + 1(Q) + 1(J) + 1(J) + 1(J) + 1(J)
	@Test
	public void firstNTBid10()
	{
		pHand.add(aLJo);
		pHand.add(aKS);
		pHand.add(aQH);
		pHand.add(aQS);
		pHand.add(aQD);
		pHand.add(aQC);
		pHand.add(aJH);
		pHand.add(aJS);
		pHand.add(aJD);
		pHand.add(aJC);

		assertEquals(bidding.selectBid(pBids1, pHand), aBid8N);
	}

	/*****************************************
	 * #2 LAST BID - NO ONE BID SUIT
	 *******************************************/

	// 9 pts= 4(HJo) + 2(RJ) + 1(Ace) + 2(threshold), but previous bids are better
	@Test
	public void lastBid06()
	{
		pHand.add(aHJo);
		pHand.add(aJD);
		pHand.add(aAD);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);

		pHand.add(a5S);
		pHand.add(a5H);
		pHand.add(a5C);

		assertEquals(bidding.selectBid(pBids2, pHand), aBidPass);
	}

	// 13 pts= 4(HJo) + +3(LJo) + 2(LJ) + 4(threshold)
	@Test
	public void lastBid10()
	{

		pHand.add(aHJo);
		pHand.add(aLJo);
		pHand.add(aJH);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);
		pHand.add(a8D);
		pHand.add(a9D);

		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids2, pHand), aBid9D);
	}

	// 9 pts= 4(HJo) + 2(A)+ 2(A) + 1(Q), but there is a better bid
	@Test
	public void lastNTBid06()
	{
		pHand.add(aHJo);
		pHand.add(aAS);
		pHand.add(aAD);
		pHand.add(aQS);

		pHand.add(a4H);
		pHand.add(a4S);
		pHand.add(a4C);
		pHand.add(a5D);
		pHand.add(a5C);
		pHand.add(a5H);

		assertEquals(bidding.selectBid(pBids2, pHand), aBidPass);
	}

	// 12 pts= 3(LJo) + 1(K) + 1(Q) + 1(Q) + 1(Q) + 1(Q) + 1(J) + 1(J) + 1(J) + 1(J)
	@Test
	public void lastNTBid10()
	{
		pHand.add(aLJo);
		pHand.add(aKS);
		pHand.add(aQH);
		pHand.add(aQS);
		pHand.add(aQD);
		pHand.add(aQC);
		pHand.add(aJH);
		pHand.add(aJS);
		pHand.add(aJD);
		pHand.add(aJC);

		assertEquals(bidding.selectBid(pBids2, pHand), aBid8N);
	}

	/******************************************
	 * #3 LAST BID - PARTNER BID SUIT
	 *******************************************/

	// 9 pts= 4(HJo) + 2(RJ) + 1(Ace) + 1(threshold) +1(Partner), but there is a better bid
	@Test
	public void lastPartnerBid06()
	{
		pHand.add(aHJo);
		pHand.add(aJD);
		pHand.add(aAD);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		
		pHand.add(a7H);
		pHand.add(a5S);
		pHand.add(a5H);
		pHand.add(a5C);
		
		assertEquals(bidding.selectBid(pBids3, pHand), aBidPass);
	}

	// 13 pts= 4(HJo) + +3(LJo) + 2(LJ) + 3(threshold) +1(Partner)
	@Test
	public void lastPartnerBid10()
	{
		pHand.add(aHJo);
		pHand.add(aLJo);
		pHand.add(aJH);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);
		pHand.add(a8D);
		
		pHand.add(a9S);
		pHand.add(a5H);
		
		assertEquals(bidding.selectBid(pBids3, pHand), aBid9D);
	}
	
	// 9 pts= 4(HJo) + 2(A)+ 2(A) +1(Partner), but there is a better bid
	@Test
	public void lastPartnerNTBid06()
	{
		pHand.add(aHJo);
		pHand.add(aAS);
		pHand.add(aAD);
		
		pHand.add(a7S);
		pHand.add(a4H);
		pHand.add(a4S);
		pHand.add(a4C);
		pHand.add(a5D);
		pHand.add(a5C);
		pHand.add(a5H);

		
		assertEquals(bidding.selectBid(pBids3, pHand), aBidPass);
	}
	
	// 11 pts= 3(LJo) + 1(K) + 1(Q) + 1(Q) + 1(Q) + 1(Q) + 1(J) + 1(J) + 1(J)
	@Test
	public void lastPartnerNTBid10()
	{
		pHand.add(aLJo);
		pHand.add(aKS);
		pHand.add(aQH);
		pHand.add(aQS);
		pHand.add(aQD);
		pHand.add(aQC);
		pHand.add(aJH);
		pHand.add(aJS);
		pHand.add(aJD);
		
		pHand.add(a4C);
		
		assertEquals(bidding.selectBid(pBids3, pHand), aBid8N);
	}

	/******************************************
	 * #4 LAST BID - OPPONENT BID SUIT
	 ******************************************/
	
	// 9 pts= 4(HJo) + 2(RJ) + 1(Ace) + 3(threshold) -1(Opponent)
	@Test
	public void lastOpponentBid06()
	{

		pHand.add(aHJo);
		pHand.add(aJD);
		pHand.add(aAD);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);
		pHand.add(a8D);
		
		pHand.add(a5H);
		pHand.add(a5C);
		
		assertEquals(bidding.selectBid(pBids4, pHand), aBid7D);
	}

	// 13 pts= 4(HJo) + +3(LJo) + 2(LJ) + 5(threshold) -1(Opponent)
	@Test
	public void lastOpponentBid10()
	{
		pHand.add(aHJo);
		pHand.add(aLJo);
		pHand.add(aJH);

		pHand.add(a4D);
		pHand.add(a5D);
		pHand.add(a6D);
		pHand.add(a7D);
		pHand.add(a8D);
		pHand.add(a9D);
		pHand.add(aTD);
		
		assertEquals(bidding.selectBid(pBids4, pHand), aBid9D);
	}

	// 9 pts= 4(HJo) + 2(A)+ 2(A) + 1(Q) + 1(Q) -1(Opponent)
	@Test
	public void lastOpponentNTBid06()
	{
		pHand.add(aHJo);
		pHand.add(aAS);
		pHand.add(aAD);
		pHand.add(aQS);
		pHand.add(aQC);

		pHand.add(a4S);
		pHand.add(a4C);
		pHand.add(a5D);
		pHand.add(a5C);
		pHand.add(a5H);
		
		assertEquals(bidding.selectBid(pBids4, pHand), aBid7N);
	}

	// 11 pts= 3(LJo) + 1(K)  + 1(K) + 1(Q) + 1(Q) + 1(Q) + 1(J) + 1(J) + 1(J) + 1(J) -1(Opponent)
	@Test
	public void lastOpponentNTBid10()
	{
		pHand.add(aLJo);
		pHand.add(aKS);
		pHand.add(aKH);
		pHand.add(aQS);
		pHand.add(aQD);
		pHand.add(aQC);
		pHand.add(aJH);
		pHand.add(aJS);
		pHand.add(aJD);
		pHand.add(aJC);
		
		assertEquals(bidding.selectBid(pBids4, pHand), aBid8N);
	}

}
