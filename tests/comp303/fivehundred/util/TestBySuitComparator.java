package comp303.fivehundred.util;

import static comp303.fivehundred.util.AllCards.*;
import static comp303.fivehundred.util.Card.*;
import static org.junit.Assert.*;

import org.junit.Test;

import comp303.fivehundred.util.Card.BySuitComparator;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestBySuitComparator
{
	@Test
	public void testBySuitComparator()
	{
		BySuitComparator testC = new BySuitComparator(Suit.DIAMONDS);
		BySuitComparator testNT = new BySuitComparator(null);
		
		assertTrue(testNT.compare(a5C, a5D)<0);

		assertTrue(testC.compare(aHJo, aHJo)==0); // high, high
		assertTrue(testC.compare(aHJo, aLJo)>0); // high, low
		assertTrue(testC.compare(aHJo, a5H)>0); // high, non-joker

		assertTrue(testC.compare(aLJo, aHJo)<0); // low, high
		assertTrue(testC.compare(aLJo, aLJo)==0); // low, low
		assertTrue(testC.compare(aLJo, a8C)>0); // low, non-joker

		assertTrue(testC.compare(a7H, a7H)==0); // same card
		assertTrue(testC.compare(a7D, a7D)==0); // same card trump	

		assertTrue(testC.compare(aQC, a8C)>0); // same non trump suit, higher rank
		assertTrue(testC.compare(a4H, aTH)<0); // same non trump suit, lower rank
		
		assertTrue(testC.compare(aKD, a8D)>0); // trump suit, not jack, higher rank
		assertTrue(testC.compare(a5D, a9D)<0); // trump suit, not jack, lower rank

		assertTrue(testC.compare(aJH, a6S)>0); // higher non trump suit,non trump suit
		assertTrue(testC.compare(a6C, aJH)<0); // lower non trump suit,non trump suit
		
		assertTrue(testC.compare(aJD, aJD)==0); // right, right
		assertTrue(testC.compare(aJH, aJH)==0); // left,left
		
		assertTrue(testC.compare(aJD, aJH)>0); // right, left
		assertTrue(testC.compare(aJH, aJD)<0); // left,right
		
		assertTrue(testC.compare(aJD, a6D)>0); // right, other trump
		assertTrue(testC.compare(aJH, aQD)>0); // left,other trump
		
		assertTrue(testC.compare(a7D, aJD)<0); // other trump,right
		assertTrue(testC.compare(a6D, aJH)<0); // other trump,left
		
		assertTrue(testC.compare(aJD, a6S)>0); // right, other suit
		assertTrue(testC.compare(aJH, aKC)>0); // left, other suit
		
		assertTrue(testC.compare(a8S, aJD)<0); // other suit, right
		assertTrue(testC.compare(a6C, aJH)<0); // other suit, left
		
		assertTrue(testC.compare(aJS,a4C)<0); // Jack of no trump
		assertTrue(testC.compare(aJC,aAH)<0);// Jack of no trump
		
		assertTrue(testC.compare(aLJo,aJD)>0);// joker and jack of trump suit
		assertTrue(testC.compare(aHJo, aJH)>0);// joker and jack of trump suit
		
		assertTrue(testC.compare(a6H, aLJo)<0);
		assertTrue(testC.compare(aLJo, a6H)>0);
		
		assertTrue(testC.compare(a5S, a4H)<0);
		assertTrue(testC.compare(a4H, a8C)>0);
		assertTrue(testC.compare(a8C, a4D)<0);
		assertTrue(testC.compare(a5H, aJS)>0);
		assertTrue(testC.compare(aJH, aQH)>0);
		assertTrue(testC.compare(a9C, a5H)<0);
		
	}
	
	private BySuitComparator comparator = new BySuitComparator(Card.Suit.DIAMONDS);
	private BySuitComparator comparatorNoTrump = new BySuitComparator(null);
	
	@Test
	public void testNoTrumpJokers()
	{
		assertNotSame(0, comparatorNoTrump.compare(aHJo, a5D));
		assertNotSame(0, comparatorNoTrump.compare(a5D, aHJo));
	}
	
	@Test
	public void testNoTrumpRegularCards()
	{
		assertTrue(comparatorNoTrump.compare(a5C, a5D)<0);
	}
	
	@Test
	public void testTrumps()
	{
		BySuitComparator comparator = new BySuitComparator(Card.Suit.DIAMONDS);
		
		assertTrue(comparator.compare(a4D, aAH) > 0 );
		assertTrue(comparator.compare(a4H, a4D) < 0);
		assertTrue(comparator.compare(a4S, a4C) < 0);		
		assertTrue(comparator.compare(a4D, a5D) < 0);		
	}
	
	@Test
	public void testNonTrumps()
	{
		assertSame(0, comparator.compare(a4C, a4C));
		assertTrue(comparator.compare(a4C, a7C) < 0);
	}
	
	@Test
	public void testJokersVsTrump()
	{		
		assertTrue(comparator.compare(aHJo, a4D) > 0);
		assertTrue(comparator.compare(a4D, aHJo) < 0);
	}
	
	@Test
	public void testHighBower()
	{
		assertTrue(comparator.compare(aJD, a4C) > 0);
		assertTrue(comparator.compare(a4C, aJD) < 0);
	}
	
	@Test
	public void testLowBower()
	{
		BySuitComparator comparator = new BySuitComparator(Card.Suit.DIAMONDS);
		assertTrue(comparator.compare(aJH, aAH) > 0 );
		assertTrue(comparator.compare(aAH, aJH) < 0 );
	}
	
	@Test
	public void testNonBowerJacks()
	{
		assertTrue(comparator.compare(aJC, aAC) < 0 );
		assertTrue(comparator.compare(aAC, aJC) > 0 );
	}
}