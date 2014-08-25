package comp303.fivehundred.ai;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.AllBids.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.ai.advanced.AdvancedCardExchangeStrategy;

/**
 * 
 * @author spatar 260407002
 * 
 */

public class TestAdvancedCardExchangeStrategy
{
	Bid[] pBidsN = { aBid6S, aBidPass, aBid7N, aBidPass }; // contract is No trump
	Bid[] pBidsS = { aBid6H, aBid7D, aBid8C, aBid10S }; // contract is Spades
	Bid[] pBidsC = { aBid7S, aBidPass, aBid8C, aBidPass }; // contract is Clubs
	Bid[] pBidsD = { aBid6C, aBid7D, aBidPass, aBid9D }; // contract is Diamonds
	Bid[] pBidsH = { aBid6D, aBid7H, aBidPass, aBidPass }; // contract is Hearts
	int pIndex;
	Hand pHand;
	AdvancedCardExchangeStrategy player;

	@Before
	public void init()
	{
		pIndex = 0;
		pHand = new Hand();
		player = new AdvancedCardExchangeStrategy();
	}

	// no trump
	// has all the cards of a certain suit A
	// should keep Jokers, aces  and then the rest of suit A
	@Test
	public void test01()
	{
		pHand.add(a4C);
		pHand.add(a5C);
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a8C); //
		pHand.add(a9C); //
		pHand.add(aTC); //
		pHand.add(aJC); //
		pHand.add(aQC); //
		pHand.add(aKC); //
		pHand.add(aAC); //
		pHand.add(aKD);
		pHand.add(aAS); //
		pHand.add(aKH);
		pHand.add(aLJo); //
		pHand.add(aHJo); //

		// no trump
		assertEquals(player.selectCardsToDiscard(pBidsN, pIndex, pHand).toString(), "<4C,5C,6C,7C,8C,9C>");
	}
	// spades
	// reducing the number of suits you have in your hand
	@Test
	public void test02()
	{
		pHand.add(a4C);
		pHand.add(a5S);
		pHand.add(a6C);
		pHand.add(a7S);
		pHand.add(a8C); 
		pHand.add(a9C); 
		pHand.add(aTD); 
		pHand.add(aJC); 
		pHand.add(aQC); 
		pHand.add(aJS); 
		pHand.add(aAC); 
		pHand.add(aKD);
		pHand.add(aAS); 
		pHand.add(aKH);
		pHand.add(aLJo); 
		pHand.add(aHJo); 

		// spades
		assertEquals(player.selectCardsToDiscard(pBidsS, pIndex, pHand).toString(), "<KH,TD,KD,4C,6C,8C>");
	}
	// spades
	// extreme case, get rid of a suit, then get rid og lowest cards by rank
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
		pHand.add(a6H); 
		pHand.add(a6S);
		pHand.add(a7C); 
		pHand.add(a7D);
		pHand.add(a7H); 
		pHand.add(a7S); 

		// spades
		assertEquals(player.selectCardsToDiscard(pBidsS, pIndex, pHand).toString(), "<4C,5C,6C,7C,4D,4H>");
	}
	// no trump
	// extreme case, get rid of lowest cards by rank
	@Test
	public void test04()
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
		pHand.add(a6H); 
		pHand.add(a6S);
		pHand.add(a7C); 
		pHand.add(a7D);
		pHand.add(a7H); 
		pHand.add(a7S); 

		// spades
		assertEquals(player.selectCardsToDiscard(pBidsN, pIndex, pHand).toString(), "<4S,4C,4D,4H,5S,5C>");
	}
}