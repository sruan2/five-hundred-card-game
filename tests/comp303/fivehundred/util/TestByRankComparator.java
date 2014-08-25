package comp303.fivehundred.util;

import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;

import org.junit.Test;

import comp303.fivehundred.util.Card.ByRankComparator;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestByRankComparator
{
	@Test
	public void testByRankComparator()
	{
		ByRankComparator testC = new ByRankComparator();

		assertTrue(testC.compare(aHJo, aHJo)==0); // high, high
		assertTrue(testC.compare(aHJo, aLJo)>0); // high, low
		assertTrue(testC.compare(aHJo, a5H)>0); // high, non-joker

		assertTrue(testC.compare(aLJo, aHJo)<0); // low, high
		assertTrue(testC.compare(aLJo, aLJo)==0); // low, low
		assertTrue(testC.compare(aLJo, a8C)>0); // low, non-joker

		assertTrue(testC.compare(a7H, a7H)==0); // same card

		assertTrue(testC.compare(aQC, aQS)>0); // same rank, higher suit
		assertTrue(testC.compare(a4C, a4H)<0); // same rank, lower suit

		assertTrue(testC.compare(aJH, a6S)>0); // higher rank
		assertTrue(testC.compare(a6H, aJS)<0); // lower rank

	}
	
	ByRankComparator comparator = new ByRankComparator();
	
	@Test
	public void testCompareJokers()
	{
		assertTrue(comparator.compare(aHJo, aLJo) > 0);
		assertTrue(comparator.compare(aLJo, aHJo) < 0);
		assertEquals(comparator.compare(aHJo, aHJo), 0);
		assertEquals(comparator.compare(aLJo, aLJo), 0);
	}
	
	@Test
	public void testCompareJokerNonJoker()
	{
		assertTrue(comparator.compare(aHJo, aAH) > 0);
		assertTrue(comparator.compare(aAH, aHJo) < 0);
	}
	
	@Test
	public void testCompareNonJokers()
	{
		assertTrue(comparator.compare(aAH, aQH) > 0);
		assertTrue(comparator.compare(aQS, aKS) < 0);
		assertTrue(comparator.compare(aQH, aQS) > 0);		
	}
}
