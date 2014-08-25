package comp303.fivehundred.util;

import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;
import java.util.Comparator;
import org.junit.Test;
import comp303.fivehundred.util.Card.BySuitNoTrumpComparator;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestBySuitNoTrumpComparator
{
	@Test
	public void testBySuitNoTrumpComparator()
	{
		BySuitNoTrumpComparator testC = new BySuitNoTrumpComparator();
		
		assertTrue(testC.compare(a5C, a5D)<0);

		assertTrue(testC.compare(aHJo, aHJo)==0); // high, high
		assertTrue(testC.compare(aHJo, aLJo)>0); // high, low
		assertTrue(testC.compare(aHJo, a5H)>0); // high, non-joker

		assertTrue(testC.compare(aLJo, aHJo)<0); // low, high
		assertTrue(testC.compare(aLJo, aLJo)==0); // low, low
		assertTrue(testC.compare(aLJo, a8C)>0); // low, non-joker

		assertTrue(testC.compare(a7H, a7H)==0); // same card
		assertTrue(testC.compare(a7D, a7D)==0); // same card , lead

		assertTrue(testC.compare(aQD, a8D)>0); // both lead suit, higher rank
		
		assertTrue(testC.compare(a4D, aTD)<0); // both lead suit, lower rank
		
		assertTrue(testC.compare(aQD, a8C)>0); // first lead suit
		assertTrue(testC.compare(a5D, a8H)<0); // first lead suit
		
		assertTrue(testC.compare(a5C, aAD)<0); // second lead suit
		assertTrue(testC.compare(aTS, a4D)<0); // second lead suit
		
		assertTrue(testC.compare(aQH, a8C)>0); // neither lead suit, higher suit
		assertTrue(testC.compare(a4S, aTH)<0); // neither lead suit, lower suit
		
		assertTrue(testC.compare(a4D, a4S)>0);
		assertTrue(testC.compare(a5D, a6C)>0);
		assertTrue(testC.compare(aJD, aLJo)<0);
		assertTrue(testC.compare(aJH, aQH)<0);
		assertTrue(testC.compare(a4H, aAD)>0);

	}
	
	Comparator<Card> comparator = new BySuitNoTrumpComparator();
	
	@Test
	public void testCompareIdenticalCards()
	{
		assertEquals( comparator.compare(a4D, a4D), 0);
		assertTrue( comparator.compare(aHJo, aLJo) > 0);
		assertTrue( comparator.compare(aLJo, aHJo) < 0);
		
		assertTrue( comparator.compare(aHJo, aAH) > 0);
		assertTrue( comparator.compare(aAH, aHJo) < 0);
		
		assertTrue( comparator.compare(aAD, aAS) > 0);
		assertTrue( comparator.compare(aKD, aQD) > 0);
	}
}