package comp303.fivehundred.util;

import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;
import org.junit.Test;
import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestCard
{
	@Test
	public void testToString()
	{
		assertEquals( "ACE of CLUBS", aAC.toString());
		assertEquals( "TEN of CLUBS", aTC.toString());
		assertEquals( "JACK of CLUBS", aJC.toString());
		assertEquals( "QUEEN of HEARTS", aQH.toString());
		assertEquals( "KING of SPADES", aKS.toString());
		assertEquals( "QUEEN of DIAMONDS", aQD.toString());
		assertEquals("LOW Joker",aLJo.toString());
	}
	
	@Test
	public void testCompare()
	{
		
		assertTrue(a4C.compareTo(a4H)==0);
		assertTrue(a4C.compareTo(a4C)==0);
		assertTrue(a4C.compareTo(a5H)<0);
		assertTrue(a6C.compareTo(a7H)<0);
		assertTrue(aTC.compareTo(aQH)<0);
		assertTrue(aHJo.compareTo(aHJo)==0);
		assertTrue(aLJo.compareTo(aLJo)==0);
		assertTrue(aHJo.compareTo(aLJo)>0);
		assertTrue(aLJo.compareTo(aHJo)<0);
		assertTrue(aLJo.compareTo(a7C)>0);
		assertTrue(aKH.compareTo(aHJo)<0);
		assertTrue(aKH.compareTo(aLJo)<0);
		assertTrue(aHJo.compareTo(aQH)>0);
		assertTrue(aHJo.compareTo(a6C)>0);
		assertTrue(aHJo.compareTo(a4C)>0);
	}
	
	@Test
	public void testEquals2()
	{
		Card card1 = new Card(Joker.LOW);
		Card card2 = new Card(Joker.HIGH);
		Card card3 = new Card(Rank.FOUR, Suit.CLUBS);
		Card card4 = new Card(Rank.FOUR, Suit.HEARTS);
		Card card5 = new Card(Rank.FOUR, Suit.SPADES);
		Card card6 = new Card(Rank.FIVE, Suit.CLUBS);
		Card card7 = new Card(Rank.FOUR, Suit.CLUBS);
		
		assertTrue(card1.equals(card1));
		assertFalse(card1.equals(card2));
		assertFalse(card1.equals(card3));
		assertFalse(card1.equals(card4));
		assertFalse(card1.equals(card5));
		assertFalse(card1.equals(card6));
		assertFalse(card1.equals(card7));
		assertTrue(card3.equals(card7));
		assertFalse(card2.equals(card1));

	}
	
	@Test
	public void testHashCode()
	{
		Card card1 = new Card(Joker.LOW);
		Card card2 = new Card(Joker.HIGH);
		Card card3 = new Card(Rank.FOUR, Suit.SPADES);
		Card card4 = new Card(Rank.SEVEN, Suit.CLUBS);
		Card card5 = new Card(Rank.JACK, Suit.DIAMONDS);
		Card card6 = new Card(Rank.ACE, Suit.HEARTS);
		
		assertEquals(46,card2.hashCode());
		assertEquals(45,card1.hashCode());
		assertEquals(0,card3.hashCode());
		assertEquals(14,card4.hashCode());
		assertEquals(29,card5.hashCode());
		assertEquals(43,card6.hashCode());


	}
	
	// extra tests added by Sherry
	@Test
	public void testGetEffectiveSuit()
	{
		assertEquals(a9S.getEffectiveSuit(Suit.DIAMONDS),Suit.SPADES);
		assertEquals(aJH.getEffectiveSuit(Suit.DIAMONDS),Suit.DIAMONDS);
		// What if the card is a joker here?
		assertEquals(aLJo.getEffectiveSuit(Suit.CLUBS),null);
	}
	
	@Test
	public void testToShortString()
	{
		assertEquals(a9C.toShortString(),"9C");
		assertEquals(aTS.toShortString(),"TS");
		assertEquals(aLJo.toShortString(),"LJ");
		assertEquals(aJH.toShortString(),"JH");
		assertEquals(aKS.toShortString(),"KS");
	}
	
	@Test
	public void testJokerConstruction()
	{
		assertTrue( aHJo.isJoker() );
		assertTrue( aLJo.isJoker() );
		assertTrue( aHJo.getJokerValue().equals(Joker.HIGH) );
		assertTrue( aLJo.getJokerValue().equals(Joker.LOW) );
	}
	
	@Test
	public void testGetRank()
	{
		assertEquals(Card.Rank.ACE, aAD.getRank()); 
	}
	
	@Test
	public void testGetSuit()
	{
		assertEquals(Card.Suit.DIAMONDS, aAD.getSuit());
	}

	@Test
	public void testEffectiveSuitNoTrump()
	{
		assertEquals( a7C.getEffectiveSuit(null), Card.Suit.CLUBS );
	}
	
	@Test
	public void testEffectiveSuitConverseJack()
	{
		assertEquals(aJS.getEffectiveSuit(Card.Suit.CLUBS), Card.Suit.CLUBS);
		assertEquals(aJC.getEffectiveSuit(Card.Suit.SPADES), Card.Suit.SPADES);
		assertEquals(aJD.getEffectiveSuit(Card.Suit.HEARTS), Card.Suit.HEARTS);
		assertEquals(aJH.getEffectiveSuit(Card.Suit.DIAMONDS), Card.Suit.DIAMONDS);		
	}
	
	@Test
	public void testEffectiveSuitNotJack()
	{
		assertEquals(aAS.getEffectiveSuit(Card.Suit.CLUBS), Card.Suit.SPADES);
		assertEquals(aAC.getEffectiveSuit(Card.Suit.SPADES), Card.Suit.CLUBS);
		assertEquals(aAD.getEffectiveSuit(Card.Suit.HEARTS), Card.Suit.DIAMONDS);
		assertEquals(aAH.getEffectiveSuit(Card.Suit.DIAMONDS), Card.Suit.HEARTS);
	}
	
	@Test
	public void testEffectiveSuitRegularJack()
	{
		assertEquals(Card.Suit.CLUBS, aJC.getEffectiveSuit(Card.Suit.HEARTS));
	}

	@Test
	public void testEqualsNull()
	{
		assertFalse(aAC.equals(null));
	}
	
	@Test
	public void testEqualsWrongClass()
	{
		assertFalse(aAC.equals(new Object()));
	}
	
	
	@Test
	public void testEqualsTrivial()
	{
		assertTrue(aAC.equals(aAC));
		assertTrue(aHJo.equals(new Card(Card.Joker.HIGH)));
	}
	
	@Test
	public void testEquals()
	{
		assertFalse(aHJo.equals(aLJo));
		assertFalse(aHJo.equals(aAC));
		assertFalse(aAC.equals(aHJo));
		
		assertTrue(aJD.equals(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS)));
		assertFalse(aJD.equals(a4D));
		assertFalse(aJD.equals(aJC));		
	}
	
	@Test
	public void testCompareTo()
	{
		assertTrue(aJD.compareTo(a4D) > 0);
		assertEquals(a5D.compareTo(a5D), 0);
		
		// Same tests as in ByRankComparator: 
		assertTrue(aHJo.compareTo(aLJo) > 0);
		assertTrue(aLJo.compareTo(aHJo) < 0);
		assertEquals(aHJo.compareTo(aHJo), 0);
		assertEquals(aLJo.compareTo(aLJo), 0);
		
		assertTrue(aHJo.compareTo(aAH) > 0);
		assertTrue(aAH.compareTo(aHJo) < 0);
		
		assertTrue(aAH.compareTo(aQH) > 0);
		assertTrue(aQS.compareTo(aKS) < 0);
		
		///////////////
	}
	
	@Test
	public void testHashcodeConsistent()
	{		
		assertEquals(a4C.hashCode(), a4C.hashCode());
	}
}
