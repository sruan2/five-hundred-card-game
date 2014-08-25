package comp303.fivehundred.model;

import comp303.fivehundred.util.*;
import comp303.fivehundred.util.Card.Suit;
import static comp303.fivehundred.util.AllBids.*;
import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author (Sherry) Shanshan Ruan 260471837
 * This is the test class for Bid
 */
public class TestBid
{
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testConstructor(){
		exception.expect(AssertionError.class);
		new Bid(1,Suit.CLUBS);	
	}
	
	@Test
	public void testConstructor2(){
		exception.expect(AssertionError.class);
		new Bid(20,Suit.DIAMONDS);
	}
	
	@Test
	public void testConstructor3(){
		exception.expect(AssertionError.class);
		new Bid(30);
	}
	
	@Test
	public void testConstructor4(){
		exception.expect(AssertionError.class);
		new Bid(-1);
	}
	
	@Test
	public void testGetSuit()
	{
		assertEquals(aBid6S.getSuit(), Card.Suit.SPADES);
		assertEquals(aBid6C2.getSuit(),Card.Suit.CLUBS);
		assertEquals(aBid10N.getSuit(), null);
		
		exception.expect(ModelException.class);
		aBidPass.getSuit();
	}
	
	@Test
	public void testGetTricksBid()
	{
		assertEquals(aBid6S.getTricksBid(), 6);
		assertEquals(aBid6C2.getTricksBid(),6);
		assertEquals(aBid10N.getTricksBid(),10);
		
		exception.expect(ModelException.class);
		aBidPass.getTricksBid();
	}
	
	@Test
	public void testIsPass()
	{
		assertEquals(aBid6S.isPass(),false);
		assertEquals(aBid6C2.isNoTrump(),false);
		assertEquals(aBid10N.isPass(),false);
		assertEquals(aBidPass.isPass(),true);
	}
	
	@Test
	public void testIsNoTrump()
	{
		assertEquals(aBid6S.isNoTrump(),false);
		assertEquals(aBid6C2.isNoTrump(),false);
		assertEquals(aBid7N.isNoTrump(),true);
		assertEquals(aBid10N.isNoTrump(),true);
		assertEquals(aBidPass.isNoTrump(),false);
	}
	
	@Test
	public void testCompareTo(){
		assertTrue(aBid6S.compareTo(aBid10N) < 0);
		assertTrue(aBid6S.compareTo(aBidPass) > 0);
		assertTrue(aBidPass.compareTo(aBid6C2)<0);
		assertTrue(aBid7H.compareTo(aBid7N)<0);
		assertTrue(aBidPass.compareTo(aBidPass)==0);
		assertTrue(aBid9N.compareTo(aBid9N)==0);
		assertTrue(aBid9H.compareTo(aBidPass)>0);
		assertTrue(aBid8S.compareTo(aBid7N)>0);
		assertTrue(aBid8S.compareTo(aBid8H)<0);
		assertTrue(aBid7N.compareTo(aBidPass)>0);
		assertTrue(aBid6H.compareTo(aBid7S)<0);
	}
	
	@Test
	public void testEquals(){
		assertTrue(aBid6S.equals(aBid6S));
		assertFalse(aBid6S.equals(aBid10N));
		assertFalse(aBid6S.equals(aBidPass));
		assertFalse(aBidPass.equals(aBid9C));
		assertTrue(aBidPass.equals(aBidPass));
		assertFalse(aBid6S.equals(aBid6H));
		assertFalse(aBid7S.equals(aBid9S));
		assertFalse(aBid6S.equals(null));
		assertFalse(aBid6S.equals(aAC));
	}
	
	@Test
	public void testToString(){
		assertEquals(aBid8D.toString(),"8 Diamonds");
	}
	
	@Test
	public void testHashCode(){
		assertTrue(aBid8D.hashCode() != aBid9D.hashCode());
		assertTrue(aBidPass.hashCode() != aBid9N.hashCode());
	}
	
	@Test
	public void testToIndex(){
		assertEquals(aBid6S.toIndex(),0);
		assertEquals(aBid10N.toIndex(),24);
		assertEquals(aBid9C.toIndex(), 16);
		assertEquals(aBid7H.toIndex(),8);
		assertEquals(aBid7N.toIndex(),9);
		
		exception.expect(ModelException.class);
		aBidPass.toIndex();
	}
	
	@Test
	public void testMax()
	{
		Bid[] aBidList = {aBid6S, aBidPass, aBid10N, aBid9H};
		Bid[] anEmptyBidList = {aBidPass,aBidPass};
		assertEquals(Bid.max(aBidList),aBid10N);
		assertEquals(Bid.max(anEmptyBidList),aBidPass);
	}
	
	@Test
	public void testGetScore()
	{
		assertEquals(aBid6S.getScore(),40);
		assertEquals(aBid9C.getScore(),360);
		assertEquals(aBid8N.getScore(),320);
		assertEquals(aBid10N.getScore(),520);
		
		exception.expect(ModelException.class);
		aBidPass.getScore();
	}
	
	// added by ifytil
	@Test
	public void BidValue(){
		Bid newbid = new Bid(0);
		assertTrue(newbid.getTricksBid()==6);
		assertTrue(newbid.getSuit()==Card.Suit.SPADES);
		newbid = new Bid(24);
		assertTrue(newbid.getTricksBid()==10);
		assertTrue(newbid.getSuit()==null);
		newbid = new Bid(1);
		assertTrue(newbid.getTricksBid()==6);
		assertTrue(newbid.getSuit()==Card.Suit.CLUBS);
		newbid = new Bid(2);
		assertTrue(newbid.getTricksBid()==6);
		assertTrue(newbid.getSuit()==Card.Suit.DIAMONDS);
		newbid = new Bid(3);
		assertTrue(newbid.getTricksBid()==6);
		assertTrue(newbid.getSuit()==Card.Suit.HEARTS);
		newbid = new Bid(4);
		assertTrue(newbid.getTricksBid()==6);
		assertTrue(newbid.getSuit()==null);
		newbid = new Bid(0);
		assertTrue(newbid.getTricksBid()==6);
		assertTrue(newbid.getSuit()==Card.Suit.SPADES);
	}
	
	private static final Bid aPass = new Bid();
	private static final Bid a6S = new Bid(6,Suit.SPADES);
	private static final Bid a6C = new Bid(6,Suit.CLUBS);
	private static final Bid a6D = new Bid(6,Suit.DIAMONDS);
	private static final Bid a6H = new Bid(6,Suit.HEARTS);
	private static final Bid a6N = new Bid(6,null);
	private static final Bid a7S = new Bid(7,Suit.SPADES);
	private static final Bid a7C = new Bid(7,Suit.CLUBS);
	private static final Bid a7D = new Bid(7,Suit.DIAMONDS);
	private static final Bid a7H = new Bid(7,Suit.HEARTS);
	private static final Bid a7N = new Bid(7,null);
	private static final Bid a8S = new Bid(8,Suit.SPADES);
	private static final Bid a8C = new Bid(8,Suit.CLUBS);
	private static final Bid a8D = new Bid(8,Suit.DIAMONDS);
	private static final Bid a8H = new Bid(8,Suit.HEARTS);
	private static final Bid a8N = new Bid(8,null);
	private static final Bid a9S = new Bid(9,Suit.SPADES);
	private static final Bid a9C = new Bid(9,Suit.CLUBS);
	private static final Bid a9D = new Bid(9,Suit.DIAMONDS);
	private static final Bid a9H = new Bid(9,Suit.HEARTS);
	private static final Bid a9N = new Bid(9,null);
	private static final Bid a10S = new Bid(10,Suit.SPADES);
	private static final Bid a10C = new Bid(10,Suit.CLUBS);
	private static final Bid a10D = new Bid(10,Suit.DIAMONDS);
	private static final Bid a10H = new Bid(10,Suit.HEARTS);
	private static final Bid a10N = new Bid(10,null);
	
	@Test
	public void testCompareTo2()	
	{
		assertTrue(aPass.compareTo(a6S) < 0);
		assertTrue(aPass.compareTo(a6C) < 0);
		assertTrue(aPass.compareTo(a6D) < 0);
		assertTrue(aPass.compareTo(a6H) < 0);
		assertTrue(aPass.compareTo(a6N) < 0);
		
		assertTrue(aPass.compareTo(aPass) == 0);
		
		assertTrue(a6S.compareTo(a7S) < 0);
		assertTrue(a6N.compareTo(a7S) < 0);
				
		assertTrue(a6H.compareTo(a6C) > 0);		
	}
	
	@Test
	public void testCompareToSameTricksCount()
	{
		assertTrue(a6N.compareTo(a6C) > 0);
	}
	
	@Test
	public void testCompareToSameTricksCount2()
	{
		assertTrue(a6C.compareTo(a6N) < 0);
	}
	
	@Test 
	public void testEqualsNull()
	{
		assertFalse(a7C.equals(null));
	}
	
	@Test 
	public void testEqualsWrongClass()
	{
		assertFalse(a7C.equals(aKS));
	}
	
	@Test
	public void testEquals2()	
	{		
		assertTrue(a7S.equals(a7S));
		
		assertTrue(a7S.equals(new Bid(7, Card.Suit.SPADES)));
		assertFalse(a7S.equals(a7D));
		assertFalse(a7S.equals(a9S));
		assertFalse(a7S.equals(a9C));
	}
	
	@Test(expected=ModelException.class)
	public void toIndexPass()
	{
		aPass.toIndex();
	}
	
	@Test 
	public void toIndex()
	{
		assertEquals(0, a6S.toIndex());
		assertEquals(1, a6C.toIndex());
		assertEquals(2, a6D.toIndex());
		assertEquals(3, a6H.toIndex());
		assertEquals(4, a6N.toIndex());
		
		assertEquals(5, a7S.toIndex());
		assertEquals(6, a7C.toIndex());
		assertEquals(7, a7D.toIndex());
		assertEquals(8, a7H.toIndex());
		assertEquals(9, a7N.toIndex());
		
		assertEquals(10, a8S.toIndex());
		assertEquals(11, a8C.toIndex());
		assertEquals(12, a8D.toIndex());
		assertEquals(13, a8H.toIndex());
		assertEquals(14, a8N.toIndex());
		
		assertEquals(15, a9S.toIndex());
		assertEquals(16, a9C.toIndex());
		assertEquals(17, a9D.toIndex());
		assertEquals(18, a9H.toIndex());
		assertEquals(19, a9N.toIndex());
		
		assertEquals(20, a10S.toIndex());
		assertEquals(21, a10C.toIndex());
		assertEquals(22, a10D.toIndex());
		assertEquals(23, a10H.toIndex());
		assertEquals(24, a10N.toIndex());		
	}
	
	@Test
	public void testIsPass2()
	{
		assertTrue(aPass.isPass());
		
		assertFalse(a6S.isPass());
		assertFalse(a6C.isPass());
		assertFalse(a6D.isPass());
		assertFalse(a6H.isPass());
		assertFalse(a6N.isPass());
		
		assertFalse(a7S.isPass());
		assertFalse(a7C.isPass());
		assertFalse(a7D.isPass());
		assertFalse(a7H.isPass());
		assertFalse(a7N.isPass());
		
		assertFalse(a8S.isPass());
		assertFalse(a8C.isPass());
		assertFalse(a8D.isPass());
		assertFalse(a8H.isPass());
		assertFalse(a8N.isPass());
		
		assertFalse(a9S.isPass());
		assertFalse(a9C.isPass());
		assertFalse(a9D.isPass());
		assertFalse(a9H.isPass());
		assertFalse(a9N.isPass());
		
		assertFalse(a10S.isPass());
		assertFalse(a10C.isPass());
		assertFalse(a10D.isPass());
		assertFalse(a10H.isPass());
		assertFalse(a10N.isPass());
	}
	
	@Test
	public void testIsNoTrump2()
	{
		assertFalse(a6S.isNoTrump());
		assertFalse(a6C.isNoTrump());
		assertFalse(a6D.isNoTrump());
		assertFalse(a6H.isNoTrump());
		assertTrue(a6N.isNoTrump());
		
		assertFalse(a7S.isNoTrump());
		assertFalse(a7C.isNoTrump());
		assertFalse(a7D.isNoTrump());
		assertFalse(a7H.isNoTrump());
		assertTrue(a7N.isNoTrump());
		
		assertFalse(a8S.isNoTrump());
		assertFalse(a8C.isNoTrump());
		assertFalse(a8D.isNoTrump());
		assertFalse(a8H.isNoTrump());
		assertTrue(a8N.isNoTrump());
		
		assertFalse(a9S.isNoTrump());
		assertFalse(a9C.isNoTrump());
		assertFalse(a9D.isNoTrump());
		assertFalse(a9H.isNoTrump());
		assertTrue(a9N.isNoTrump());
		
		assertFalse(a10S.isNoTrump());
		assertFalse(a10C.isNoTrump());
		assertFalse(a10D.isNoTrump());
		assertFalse(a10H.isNoTrump());
		assertTrue(a10N.isNoTrump());
	}
	
	@Test
	public void testTricksBid()
	{
		assertEquals(6, a6C.getTricksBid());
		assertEquals(7, a7C.getTricksBid());
		assertEquals(8, a8C.getTricksBid());
		assertEquals(9, a9C.getTricksBid());
		assertEquals(10, a10C.getTricksBid());
	}
	
	@Test
	public void testMax2()
	{
		Bid[] t2 = {aPass, aPass, aPass};
		assertEquals(aPass, Bid.max(t2));
		
		Bid[] t3 = {a6S};
		assertEquals(a6S, Bid.max(t3));
		
		Bid[] t4 = {a6S, a7C};
		assertEquals(a7C, Bid.max(t4));
		
		Bid[] t5 = {a6S, aPass, a7C, aPass};
		assertEquals(a7C, Bid.max(t5));
		
	}
	
	@Test(expected=ModelException.class)
	public void testScore()
	{
		assertEquals( 40, new Bid(6,Suit.SPADES).getScore());
		assertEquals( 60, new Bid(6,Suit.CLUBS).getScore());
		assertEquals( 80, new Bid(6,Suit.DIAMONDS).getScore());
		assertEquals( 100, new Bid(6,Suit.HEARTS).getScore());
		assertEquals( 120, new Bid(6,null).getScore());
		
		assertEquals( 140, new Bid(7,Suit.SPADES).getScore());
		assertEquals( 160, new Bid(7,Suit.CLUBS).getScore());
		assertEquals( 180, new Bid(7,Suit.DIAMONDS).getScore());
		assertEquals( 200, new Bid(7,Suit.HEARTS).getScore());
		assertEquals( 220, new Bid(7,null).getScore());
		
		assertEquals( 240, new Bid(8,Suit.SPADES).getScore());
		assertEquals( 260, new Bid(8,Suit.CLUBS).getScore());
		assertEquals( 280, new Bid(8,Suit.DIAMONDS).getScore());
		assertEquals( 300, new Bid(8,Suit.HEARTS).getScore());
		assertEquals( 320, new Bid(8,null).getScore());
		
		assertEquals( 340, new Bid(9,Suit.SPADES).getScore());
		assertEquals( 360, new Bid(9,Suit.CLUBS).getScore());
		assertEquals( 380, new Bid(9,Suit.DIAMONDS).getScore());
		assertEquals( 400, new Bid(9,Suit.HEARTS).getScore());
		assertEquals( 420, new Bid(9,null).getScore());
		
		assertEquals( 440, new Bid(10,Suit.SPADES).getScore());
		assertEquals( 460, new Bid(10,Suit.CLUBS).getScore());
		assertEquals( 480, new Bid(10,Suit.DIAMONDS).getScore());
		assertEquals( 500, new Bid(10,Suit.HEARTS).getScore());
		assertEquals( 520, new Bid(10,null).getScore());
		
		new Bid().getScore();
	}
	
	@Test(expected=ModelException.class)
	public void testPassSuit()
	{
		Bid bid = new Bid();
		bid.getSuit();		
	}
	
	@Test
	public void testGetSuit2()
	{
		assertEquals(Card.Suit.SPADES, a6S.getSuit());
		assertEquals(Card.Suit.CLUBS, a6C.getSuit());
		assertEquals(Card.Suit.DIAMONDS, a6D.getSuit());
		assertEquals(Card.Suit.HEARTS, a6H.getSuit());
		assertEquals(null, a6N.getSuit());
	}
	
	@Test
	public void testIndexedBid()
	{
		assertEquals(new Bid(0), a6S);
		assertEquals(new Bid(1), a6C);
		assertEquals(new Bid(2), a6D);
		assertEquals(new Bid(3), a6H);		
		
		assertEquals(new Bid(5), a7S);
		assertEquals(new Bid(6), a7C);
		assertEquals(new Bid(7), a7D);
		assertEquals(new Bid(8), a7H);
				
		assertEquals(new Bid(10), a8S);
		assertEquals(new Bid(11), a8C);
		assertEquals(new Bid(12), a8D);
		assertEquals(new Bid(13), a8H);
			
		assertEquals(new Bid(15), a9S);
		assertEquals(new Bid(16), a9C);
		assertEquals(new Bid(17), a9D);
		assertEquals(new Bid(18), a9H);
				
		assertEquals(new Bid(20), a10S);
		assertEquals(new Bid(21), a10C);
		assertEquals(new Bid(22), a10D);
		assertEquals(new Bid(23), a10H);
				
	}
	
	@Test
	public void testIndexedBidNoTrump()
	{
		assertEquals(new Bid(4), a6N);
		assertEquals(new Bid(9), a7N);
		assertEquals(new Bid(14), a8N);
		assertEquals(new Bid(19), a9N);
		assertEquals(new Bid(24), a10N);
	}
}
