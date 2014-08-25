package comp303.fivehundred.ai;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.ai.basic.BasicPlayingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;
import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestBasicPlayingStrategy
{

	Trick pTrick;
	Trick pTrick2;
	Hand pHand;
	BasicPlayingStrategy player;

	/* These cases may be tricky. Consider them wisely, post on the boards if you're not sure about one */

	@Before
	public void init()
	{
		pTrick = new Trick(new Bid(0));
		pTrick2 = new Trick(new Bid(24));
		pHand = new Hand();
		player = new BasicPlayingStrategy();
	}

	@Test
	// low joker lead, high joker played
	public void basicTrump()
	{
		pHand.add(a7S);
		pHand.add(a6D);
		pHand.add(a4H);
		pHand.add(aHJo);
		pHand.add(a4C);

		pTrick.add(aLJo);
		assertEquals(player.play(pTrick, pHand), aHJo); // Must follow LJ's suit (Spades) : HJ is Spades (WIN)
	}

	@Test
	// Right bower lead, left bower played
	public void basicTrump2()
	{
		pHand.add(aJC);
		pHand.add(a6D);
		pHand.add(a5H);
		pHand.add(a4C);

		pTrick.add(aJS);
		assertEquals(player.play(pTrick, pHand), aJC); // Must follow JS's suit (Spades) : JC is Spades (LOSE)
	}

	@Test
	// left lead, play right, higher of there two trump
	public void basicTrump3()
	{
		pHand.add(aJS);
		pHand.add(a4S);

		pTrick.add(aJC);
		assertEquals(player.play(pTrick, pHand), aJS); // Must follow JC's suit (Spades) : JS is Spades (WIN)
	}

	@Test
	// right lead, can't win, play lower trump
	public void basicTrump4()
	{
		pHand.add(aJC);
		pHand.add(a6S);
		pHand.add(a5H);
		pHand.add(a4S);

		pTrick.add(aJS);
		assertEquals(player.play(pTrick, pHand), a4S); // Must follow JS's suit (Spades) : 4S is Spades (LOSE)
	}

	// non-trump lead

	@Test
	// heart lead, no hearts in hand, no trump in hand, joker in hand
	public void trump01()
	{
		pHand.add(aHJo);
		pHand.add(a6D);
		pHand.add(a5D);
		pHand.add(a4C);

		pTrick.add(aTH);
		assertEquals(player.play(pTrick, pHand), aHJo); // Can't follow hearts : aHJo is highest (WIN)
	}

	@Test
	// heart lead, low Joker played, no hearts in hand, no trump in hand, high joker in hand
	public void trump02()
	{
		pHand.add(aLJo);
		pHand.add(a6D);
		pHand.add(a5D);
		pHand.add(a4C);

		pTrick.add(aTH);
		pTrick.add(aHJo);
		assertEquals(player.play(pTrick, pHand), a4C); // Can't follow suit, aHJo is winning, can't beat (LOSE)
	}

	@Test
	// heart lead, no hearts in hand, trump in hand, joker in hand
	public void trump03()
	{
		pHand.add(aLJo);
		pHand.add(a6D);
		pHand.add(a5S);
		pHand.add(a4C);

		pTrick.add(aTH);
		assertEquals(player.play(pTrick, pHand), a5S); // Can't follow suit, a5s is lowest winning card (WIN)
	}

	@Test
	// heart lead, low Joker played, no hearts in hand, trump in hand, high joker in hand
	public void trump04()
	{
		pHand.add(aHJo);
		pHand.add(a6D);
		pHand.add(a5S);
		pHand.add(a4C);

		pTrick.add(aTH);
		pTrick.add(aLJo);
		assertEquals(player.play(pTrick, pHand), aHJo); // Can't follow suit, aHJo is lowest winning card (WIN)
	}

	@Test
	// heart lead, higher heart in hand, joker and trump in hand
	public void trump05()
	{
		pHand.add(aHJo);
		pHand.add(aQH);
		pHand.add(a5S);
		pHand.add(a4C);

		pTrick.add(aTH);
		assertEquals(player.play(pTrick, pHand), aQH); // Can follow suit, aQH is lowest winning card (WIN)
	}

	@Test
	// heart lead, higher heart in hand, joker and trump in hand, Low joker played
	public void trump06()
	{
		pHand.add(aHJo);
		pHand.add(aQH);
		pHand.add(a5S);
		pHand.add(a4C);

		pTrick.add(aTH);
		pTrick.add(aLJo);
		assertEquals(player.play(pTrick, pHand), aQH); // follow suit, aQH is lowest following card (Lose)
	}

	@Test
	// heart lead, 2 higher hearts in hand, joker and trump in hand
	public void trump07()
	{
		pHand.add(aHJo);
		pHand.add(aTH);
		pHand.add(a5S);
		pHand.add(a6H);

		pTrick.add(a5H);
		assertEquals(player.play(pTrick, pHand), a6H); // follow suit, a6H is lowest winning card (WIN)
	}

	// trump lead

	@Test
	// Spade lead, no spades or jokers in hand
	public void trump08()
	{
		pHand.add(aKC);
		pHand.add(aTH);
		pHand.add(a5D);
		pHand.add(a6H);

		pTrick.add(aTS);
		assertEquals(player.play(pTrick, pHand), a5D); // Can't follow suit, a5D is lowest card (Lose)
	}

	@Test
	// Spade lead, 2 higher spades in hand, no jokers
	public void trump09()
	{
		pHand.add(aKC);
		pHand.add(aTH);
		pHand.add(aTS);
		pHand.add(a7S);

		pTrick.add(a6S);
		assertEquals(player.play(pTrick, pHand), a7S); // Follow suit, a7S is lowest winning card (WIN)
	}

	@Test
	// spade lead, no spades in hand, joker in hand
	public void trump10()
	{
		pHand.add(aLJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick.add(a6S);
		assertEquals(player.play(pTrick, pHand), aLJo); // Follow suit, aLJo is lowest winning card (WIN)
	}

	@Test
	// spade lead, no spades in hand, low joker played, high joker in hand
	public void trump11()
	{
		pHand.add(aHJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick.add(a6S);
		pTrick.add(aLJo);
		assertEquals(player.play(pTrick, pHand), aHJo); // Follow suit, aHJo is lowest winning card (WIN)
	}

	@Test
	// spade lead, no spades in hand, high joker played, low joker in hand
	public void trump12()
	{
		pHand.add(aLJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick.add(a6S);
		pTrick.add(aHJo);
		assertEquals(player.play(pTrick, pHand), aLJo); // Follow suit, aLJo is lowest card that follows (LOSE)
	}

	// no trump

	@Test
	// High J lead, low J in hand
	public void noTrump01()
	{
		pHand.add(aLJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(aHJo);
		assertEquals(player.play(pTrick2, pHand), a7D); // Can't follow suit, a7D is lowest card (LOSE)
	}

	@Test
	// Low J lead, High J in hand
	public void noTrump02()
	{
		pHand.add(aTC);
		pHand.add(aTH);
		pHand.add(aHJo);
		pHand.add(a7D);

		pTrick2.add(aLJo);
		
		assertEquals(player.play(pTrick2, pHand), aHJo); // aHJo is lowest winning card (WIN)
	}

	@Test
	// High J lead, no j in hand
	public void noTrump03()
	{
		pHand.add(aTD);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(aLJo);
		assertEquals(player.play(pTrick2, pHand), a7D); // a7D is lowest card (Lose)
	}

	@Test
	// Low J lead, no j's in hand
	public void noTrump04()
	{
		pHand.add(aLJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(aHJo);
		assertEquals(player.play(pTrick2, pHand), a7D); // a7D is lowest card (Lose)
	}

	@Test
	// Wrong!!!!
	// non j lead, High j in hand, need to follow suit
	public void noTrump05()
	{
		pHand.add(aHJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(a5H);
		assertEquals(player.play(pTrick2, pHand), aTH); // aTH is lowest lead suit card (Lose)
	}

	// non j lead, High j in hand, no need to follow suit
	public void noTrump06()
	{
		pHand.add(aHJo);
		pHand.add(aTD);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(a5H);
		assertEquals(player.play(pTrick2, pHand), aHJo); // aHJo is lowest winning card (win)
	}

	@Test
	// non j lead, Low j in hand, need to follow suit
	public void noTrump07()
	{
		pHand.add(aLJo);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(a5H);
		assertEquals(player.play(pTrick2, pHand), aTH); // aTH is lowest lead suit card (lose)
	}

	@Test
	// non j lead, Low j in hand, no need to follow suit
	public void noTrump08()
	{
		pHand.add(aLJo);
		pHand.add(aTD);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(a5H);
		assertEquals(player.play(pTrick2, pHand), aLJo); // aLJo is lowest winning card (Win)
	}

	@Test
	// ace of H lead, lower hearts in hand, need to follow suit
	public void noTrump09()
	{
		pHand.add(aQH);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(aAH);
		assertEquals(player.play(pTrick2, pHand), aTH); // aTH is lowest lead suit card (lose)
	}

	@Test
	// 5 of H lead, in hand :ace of H , lower heart
	public void noTrump10()
	{
		pHand.add(aQH);
		pHand.add(aAH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(a5H);
		assertEquals(player.play(pTrick2, pHand), aQH); // aQH is lowest lead suit card (Win)
	}

	@Test
	// 5 of H lead, beat by JokerL, High J in hand
	public void noTrump11()
	{
		pHand.add(aQH);
		pHand.add(aAH);
		pHand.add(aTC);
		pHand.add(aHJo);

		pTrick2.add(a5H);
		pTrick2.add(aLJo);
		assertEquals(player.play(pTrick2, pHand), aQH); // aQH is lowest lead suit card (lose)
	}

	@Test
	// 5 of H lead, beat by JokerH, Low J in hand
	public void noTrump12()
	{
		pHand.add(aQH);
		pHand.add(aAH);
		pHand.add(aTC);
		pHand.add(aLJo);

		pTrick2.add(a5H);
		pTrick2.add(aHJo);
		assertEquals(player.play(pTrick2, pHand), aQH); // aQH is lowest lead suit card (lose)
	}

	@Test
	// 5 H of H lead, beat by JokerH, no J in hand
	public void noTrump13()
	{
		pHand.add(aQH);
		pHand.add(aAH);
		pHand.add(aTC);
		pHand.add(a7D);

		pTrick2.add(a5H);
		pTrick2.add(aHJo);
		assertEquals(player.play(pTrick2, pHand), aQH); // aQH is lowest lead suit card (lose)
	}

	@Test
	// 5 H of H lead, beat by higher heart, no higher cards in hand
	public void noTrump14()
	{
		pHand.add(aQH);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(a6D);

		pTrick2.add(a5H);
		pTrick2.add(aAH);
		assertEquals(player.play(pTrick2, pHand), aTH); // aTH is lowest lead suit card (lose)
	}

	@Test
	// 5 H of H lead, beat by higher heart, higher heart in hand
	public void noTrump15()
	{
		pHand.add(aQH);
		pHand.add(aAH);
		pHand.add(aTC);
		pHand.add(aHJo);

		pTrick2.add(a5H);
		pTrick2.add(aTH);
		assertEquals(player.play(pTrick2, pHand), aQH); // aQH is lowest winning lead suit card (win)
	}

	@Test
	// 5 H of H lead, beat by higher heart, joker in hand
	public void noTrump16()
	{
		pHand.add(aQH);
		pHand.add(aTH);
		pHand.add(aTC);
		pHand.add(aHJo);

		pTrick2.add(a5H);
		pTrick2.add(aAH);
		assertEquals(player.play(pTrick2, pHand), aTH); // aQH is lowest lead suit card (lose)
	}
	
private BasicPlayingStrategy aStrategy = new BasicPlayingStrategy();
	
	/**
	 * Test case 1: leading is not a joker. Also tests that
	 * the returned card is always in the hand.
	 */
	@Test 
	public void test1()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a6D); 
		lHand.add(AllCards.a7D); 
		lHand.add(AllCards.a7H); 
		lHand.add(AllCards.a7C); 
		lHand.add(AllCards.a7S); 
		lHand.add(AllCards.a8D); 
		lHand.add(AllCards.a8C); 
		lHand.add(AllCards.aLJo);
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.a8H);
		lHand.add(AllCards.aAS);

		for( int i = 0; i < 10000; i++ )
		{
			Card lCard = aStrategy.play(lTrick, lHand);
			//assertFalse(lCard.isJoker());
			assertTrue(contains(lHand,lCard));
		}
	}
	
	/**
	 * Test case 2a: Picks the lowest card that can follow
	 * suit and win. Not a trump played.
	 */
	@Test 
	public void test2a()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.a6D);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a7D); 
		lHand.add(AllCards.a8D); 
		lHand.add(AllCards.a9D); 
		lHand.add(AllCards.a7H); 
		lHand.add(AllCards.a7S); 
		lHand.add(AllCards.a8H); 
		lHand.add(AllCards.a8C); 
		lHand.add(AllCards.aLJo);
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.aAC);
		lHand.add(AllCards.aAS);

		assertEquals(AllCards.a7D,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 2b: Picks the lowest card that can follow
	 * suit and win. A trump is played.
	 */
	@Test 
	public void test2b()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.aJC);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.aJS); // Next in line 
		lHand.add(AllCards.aLJo); 
		lHand.add(AllCards.aHJo); 
		lHand.add(AllCards.a7H); 
		lHand.add(AllCards.a7S); 
		lHand.add(AllCards.a8H); 
		lHand.add(AllCards.a8C); 
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a8S);
		lHand.add(AllCards.aAC);
		lHand.add(AllCards.aAS);

		assertEquals(AllCards.aJS,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 3: Picks the lowest card that can follow
	 * suit, in the case where no card can follow suit
	 * and win.
	 */
	@Test 
	public void test3()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.aQH);
		lTrick.add(AllCards.aAH);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a4H); // Should be played
		lHand.add(AllCards.a5H); 
		lHand.add(AllCards.a6H); 
		lHand.add(AllCards.a7H); 
		lHand.add(AllCards.a4C); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.aLJo); 
		lHand.add(AllCards.aHJo);
		lHand.add(AllCards.aJS);
		lHand.add(AllCards.aJC);
		lHand.add(AllCards.aAS);

		assertEquals(AllCards.a4H,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 4a: No card can follow suit. Play the lowest 
	 * trump card, no trump cards played so far.
	 */
	@Test 
	public void test4a()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.aQH);
		lTrick.add(AllCards.aAH);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a4S); // Should be played
		lHand.add(AllCards.a5S); 
		lHand.add(AllCards.a6S); 
		lHand.add(AllCards.a7S); 
		lHand.add(AllCards.a4C); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.a6C); 
		lHand.add(AllCards.a7C);
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a9C);
		lHand.add(AllCards.aTC);

		assertEquals(AllCards.a4S,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 4b: No card can follow suit. Play the lowest 
	 * trump card, a trump card was played.
	 */
	@Test 
	public void test4b()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.aQH);
		lTrick.add(AllCards.aAH);
		lTrick.add(AllCards.a5S);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a4S); 
		lHand.add(AllCards.a9S); 
		lHand.add(AllCards.a6S); // Should be played
		lHand.add(AllCards.a7S); 
		lHand.add(AllCards.a4C); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.a6C); 
		lHand.add(AllCards.a7C);
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a9C);
		lHand.add(AllCards.aTC);

		assertEquals(AllCards.a6S,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 5a: No card can follow suit and no trump card.
	 */
	@Test 
	public void test5a()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.aQH);
		lTrick.add(AllCards.aAH);
		lTrick.add(AllCards.a5S);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a4D); // Should be played
		lHand.add(AllCards.a5D); 
		lHand.add(AllCards.a6D); 
		lHand.add(AllCards.a7D); 
		lHand.add(AllCards.a8D); 
		lHand.add(AllCards.a9D); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.a7C);
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a9C);
		lHand.add(AllCards.aTC);

		assertEquals(AllCards.a4D,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 5b: No card can follow suit and no sufficiently
	 * high trump card.
	 */
	@Test 
	public void test5b()
	{
		Trick lTrick = new Trick(new Bid(7,Suit.SPADES));
		lTrick.add(AllCards.aQH);
		lTrick.add(AllCards.aAH);
		lTrick.add(AllCards.aQS);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a4D); // Should be played
		lHand.add(AllCards.a5D); 
		lHand.add(AllCards.a6D); 
		lHand.add(AllCards.a7D); 
		lHand.add(AllCards.a8S); 
		lHand.add(AllCards.a9S); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.a7C);
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a9C);
		lHand.add(AllCards.aTC);

		assertEquals(AllCards.a4D,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 6a: Joker led: dump the lowest card.
	 */
	@Test 
	public void test6a()
	{
		Trick lTrick = new Trick(new Bid(7,null));
		lTrick.add(AllCards.aHJo);
		lTrick.add(AllCards.aAH);
		lTrick.add(AllCards.a4S);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.a4D); // Should be played
		lHand.add(AllCards.a5D); 
		lHand.add(AllCards.a6D); 
		lHand.add(AllCards.a7D); 
		lHand.add(AllCards.a8S); 
		lHand.add(AllCards.a9S); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.a7C);
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a9C);
		lHand.add(AllCards.aTC);

		assertEquals(AllCards.a4D,aStrategy.play(lTrick, lHand));
	}
	
	/**
	 * Test case 6b: Joker led: beat it
	 */
	@Test 
	public void test6b()
	{
		Trick lTrick = new Trick(new Bid(7,null));
		lTrick.add(AllCards.aLJo);
		lTrick.add(AllCards.aAH);
		lTrick.add(AllCards.a4S);
				
		Hand lHand = new Hand();
		lHand.add(AllCards.aHJo); // Should be played
		lHand.add(AllCards.a5D); 
		lHand.add(AllCards.a6D); 
		lHand.add(AllCards.a7D); 
		lHand.add(AllCards.a8S); 
		lHand.add(AllCards.a9S); 
		lHand.add(AllCards.a5C); 
		lHand.add(AllCards.a7C);
		lHand.add(AllCards.a8C);
		lHand.add(AllCards.a9C);
		lHand.add(AllCards.aTC);

		assertEquals(AllCards.aHJo,aStrategy.play(lTrick, lHand));
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
