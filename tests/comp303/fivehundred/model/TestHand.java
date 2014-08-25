package comp303.fivehundred.model;

import static org.junit.Assert.*;
import static comp303.fivehundred.util.AllCards.*;
import comp303.fivehundred.util.*;
import comp303.fivehundred.util.Card.Suit;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author (Sherry) Shanshan Ruan 260471837
 * This is the test class for Bid
 */

public class TestHand
{
	Hand h1;
	Hand h2;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		h1 = new Hand();
		h1.add(a4S);
		h1.add(aJS);
		h1.add(a5C);
		h1.add(a9C);
		h1.add(a5D);
		h1.add(aAD);
		h1.add(a8H);
		h1.add(aKH);
		h1.add(aLJo);
		h1.add(aHJo);
		
		h2 = new Hand();
		h2.add(a6S);
		h2.add(a8S);
		h2.add(aTC);
		h2.add(aJC);
		h2.add(a4D);
		h2.add(aJD);
		h2.add(aQD);
		h2.add(a7H);
		h2.add(aJH);
		h2.add(aKH);
	}
	
	@Test
	public void cloneTest(){
		Hand cloned = h1.clone();
		assertNotNull(cloned);
		assertNotSame(cloned, h1);
		assertFalse(cloned.equals(h1));
		
		int listSize = h1.size();
		
		for (int i = 0; i<listSize; i++){
			assertTrue(cloned.getFirst().equals(h1.getFirst()));
			assertNotNull(cloned.getFirst());
			assertNotSame(cloned.getFirst(), h1.getFirst());
			
			cloned.remove(cloned.getFirst());
			h1.remove(h1.getFirst());
		}
		
	}
	
	@Test
	public void lowestNoTrump(){
		
		Hand hand = new Hand();
		hand.add(a5C);
		hand.add(aAD);
		
		assertTrue(h1.selectLowest(null).equals(a4S));
		h1.remove(a4S);
		assertTrue(h1.selectLowest(null).equals(a5C));
		h1.remove(a5C);
		assertTrue(h1.selectLowest(null).equals(a5D));
		h1.remove(a5D);
		assertTrue(h1.selectLowest(null).equals(a8H));
		h1.remove(a8H);
		assertTrue(h1.selectLowest(null).equals(a9C));
		h1.remove(a9C);
		assertTrue(h1.selectLowest(null).equals(aJS));
		h1.remove(aJS);
		assertTrue(h1.selectLowest(null).equals(aKH));
		h1.remove(aKH);
		assertTrue(h1.selectLowest(null).equals(aAD));
		h1.remove(aAD);
		assertTrue(h1.selectLowest(null).equals(aLJo));
		h1.remove(aLJo);
		assertTrue(h1.selectLowest(null).equals(aHJo));
		h1.remove(aHJo);
		assertTrue(h1.size()==0);
	}
	
	@Test
	public void SimpleCase(){
		Hand phand = new Hand();
		phand.add(a5S);
		phand.add(a4C);
		assertEquals(phand.selectLowest(Suit.SPADES), a4C);
	}
	
	@Test
	public void lowestClubs(){
		h1.remove(aLJo);
		h1.remove(aHJo);
		h1.add(aJC);
		h1.add(a9H);
		assertTrue(h1.size()==10);
		assertEquals(h1.selectLowest(Suit.CLUBS),a4S);
		h1.remove(a4S);
		assertEquals(h1.selectLowest(Suit.CLUBS),a5D);
		h1.remove(a5D);
		assertEquals(h1.selectLowest(Suit.CLUBS),a8H);
		h1.remove(a8H);
		assertEquals(h1.selectLowest(Suit.CLUBS),a9H);
		h1.remove(a9H);
		assertEquals(h1.selectLowest(Suit.CLUBS),aKH);
		h1.remove(aKH);
		assertEquals(h1.selectLowest(Suit.CLUBS),aAD);
		h1.remove(aAD);
		assertEquals(h1.selectLowest(Suit.CLUBS),a5C);
		h1.remove(a5C);
		assertEquals(h1.selectLowest(Suit.CLUBS),a9C);
		h1.remove(a9C);
		assertEquals(h1.selectLowest(Suit.CLUBS),aJS);
		h1.remove(aJS);
		assertEquals(h1.selectLowest(Suit.CLUBS),aJC);
		h1.remove(aJC);
		assertTrue(h1.size()==0);
	}
	
	@Test
	public void testClone(){
		Hand aHand2 = h1.clone();
		Hand aHand3 = aHand2;
		assertFalse(aHand2==h1);
		assertTrue(isSameList(aHand2,h1));
		assertTrue(aHand2==aHand3);
	}
	
	@Test
	public void testCanLead(){
		CardList l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		l1.add(a5C);
		l1.add(a9C);
		l1.add(a5D);
		l1.add(aAD);
		l1.add(a8H);
		l1.add(aKH);
		assertTrue(isSameList(h1.canLead(true),l1));
		assertTrue(isSameList(h1.canLead(false),h1));
	}
	
	@Test
	public void testGetJokers(){
		CardList l1 = new CardList();
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.getJokers(),(l1)));
		assertTrue(isSameList(h2.getJokers(),new CardList()));
	}
	
	@Test
	public void testGetNonJokers(){
		CardList l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		l1.add(a5C);
		l1.add(a9C);
		l1.add(a5D);
		l1.add(aAD);
		l1.add(a8H);
		l1.add(aKH);
		assertTrue(isSameList(h1.getNonJokers(),l1));
		assertTrue(isSameList(h2.getNonJokers(),h2));
	}
	
	@Test
	public void testGetTrumpCards(){
		exception.expect(AssertionError.class);
		h1.getTrumpCards(null);
		
		CardList l1 = new CardList();
		l1.add(aJS);
		l1.add(a5C);
		l1.add(a9C);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.getTrumpCards(Suit.CLUBS),l1));
		
		l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.getTrumpCards(Suit.SPADES),l1));
		
		l1 = new CardList();
		l1.add(a5D);
		l1.add(aAD);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.getTrumpCards(Suit.DIAMONDS),l1));
		
		
		l1 = new CardList();
		l1.add(a8H);
		l1.add(aKH);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.getTrumpCards(Suit.HEARTS),l1));
		
		CardList l2 = new CardList();
		l2.add(a6S);
		l2.add(a8S);
		l2.add(aJC);
		assertTrue(isSameList(h2.getTrumpCards(Suit.SPADES),l2));
		
		l2 = new CardList();
		l2.add(aTC);
		l2.add(aJC);
		assertTrue(isSameList(h2.getTrumpCards(Suit.CLUBS),l2));
		
		l2 = new CardList();
		l2.add(a4D);
		l2.add(aJD);
		l2.add(aJH);
		assertTrue(isSameList(h2.getTrumpCards(Suit.DIAMONDS),l2));
		
		l2 = new CardList();
		l2.add(aJD);
		l2.add(aJH);
		assertTrue(isSameList(h2.getTrumpCards(Suit.HEARTS),l2));
	}
	
	@Test
	public void testGetNonTrumpCards(){
		CardList l1 = new CardList();
		l1.add(a5C);
		l1.add(a9C);
		l1.add(a5D);
		l1.add(aAD);
		l1.add(a8H);
		l1.add(aKH);
		assertTrue(isSameList(h1.getNonTrumpCards(Suit.SPADES),l1));
		
		l1 = new CardList();
		l1.add(a4S);
		l1.add(a5D);
		l1.add(aAD);
		l1.add(a8H);
		l1.add(aKH);
		assertTrue(isSameList(h1.getNonTrumpCards(Suit.CLUBS),l1));
		
		l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		l1.add(a5C);
		l1.add(a9C);
		l1.add(a8H);
		l1.add(aKH);
		assertTrue(isSameList(h1.getNonTrumpCards(Suit.DIAMONDS),l1));
		
		l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		l1.add(a5C);
		l1.add(a9C);
		l1.add(a5D);
		l1.add(aAD);
		assertTrue(isSameList(h1.getNonTrumpCards(Suit.HEARTS),l1));
		
		CardList l2 = new CardList();
		l2 = new Hand();
		l2.add(aTC);
		l2.add(a4D);
		l2.add(aJD);
		l2.add(aQD);
		l2.add(a7H);
		l2.add(aJH);
		l2.add(aKH);
		assertTrue(isSameList(h2.getNonTrumpCards(Suit.SPADES),l2));
		
		l2 = new CardList();
		l2.add(a6S);
		l2.add(a8S);
		l2.add(a4D);
		l2.add(aJD);
		l2.add(aQD);
		l2.add(a7H);
		l2.add(aJH);
		l2.add(aKH);
		assertTrue(isSameList(h2.getNonTrumpCards(Suit.CLUBS),l2));
		
		l2 = new CardList();
		l2.add(a6S);
		l2.add(a8S);
		l2.add(aTC);
		l2.add(aJC);
		l2.add(a7H);
		l2.add(aKH);
		assertTrue(isSameList(h2.getNonTrumpCards(Suit.DIAMONDS),l2));

		l2 = new CardList();
		l2.add(a6S);
		l2.add(a8S);
		l2.add(aTC);
		l2.add(aJC);
		l2.add(a4D);
		l2.add(aQD);
		assertTrue(isSameList(h2.getNonTrumpCards(Suit.HEARTS),l2));
	}
	
	@Test
	public void testSelectLowest()
	{
		assertEquals(h1.selectLowest(null),a4S);
		assertEquals(h1.selectLowest(Suit.SPADES),a5C);
		assertEquals(h1.selectLowest(Suit.CLUBS),a4S);
		assertEquals(h1.selectLowest(Suit.DIAMONDS),a4S);
		assertEquals(h1.selectLowest(Suit.HEARTS),a4S);
		
		assertEquals(h2.selectLowest(null),a4D);
		assertEquals(h2.selectLowest(Suit.SPADES),a4D);
		assertEquals(h2.selectLowest(Suit.CLUBS),a4D);
		assertEquals(h2.selectLowest(Suit.DIAMONDS),a6S);
		assertEquals(h2.selectLowest(Suit.HEARTS),a4D);
		
		Hand h3 = new Hand();
		h3.add(aLJo);
		h3.add(aHJo);
		assertEquals(h3.selectLowest(null),aLJo);
		assertEquals(h3.selectLowest(Suit.DIAMONDS),aLJo);
		
		Hand h4 = new Hand();
		h4.add(aJS);
		h4.add(aJC);
		h4.add(aJD);
		h4.add(aJH);
		h4.add(aQS);
		h4.add(aQC);
		h4.add(aQD);
		h4.add(aQH);
		assertEquals(h4.selectLowest(null),aJS);
		assertEquals(h4.selectLowest(Suit.SPADES),aJD);
		assertEquals(h4.selectLowest(Suit.CLUBS),aJD);
		assertEquals(h4.selectLowest(Suit.DIAMONDS),aJS);
		assertEquals(h4.selectLowest(Suit.HEARTS),aJS);
	}

	@Test
	public void testPlayableCards(){
		
		CardList l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		assertTrue(isSameList(h1.playableCards(Suit.SPADES, null), l1));
		
		l1 = new CardList();
		l1.add(a4S);
		l1.add(aJS);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.playableCards(Suit.SPADES, Suit.SPADES), l1));
		
		l1 = new CardList();
		l1.add(aJS);
		l1.add(a5C);
		l1.add(a9C);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.playableCards(Suit.CLUBS, Suit.CLUBS), l1));
		
		l1 = new CardList();
		l1.add(a5D);
		l1.add(aAD);
		l1.add(aLJo);
		l1.add(aHJo);
		assertTrue(isSameList(h1.playableCards(Suit.DIAMONDS, Suit.DIAMONDS), l1));
		
		l1 = new CardList();
		l1.add(a8H);
		l1.add(aKH);
		assertTrue(isSameList(h1.playableCards(Suit.HEARTS, Suit.DIAMONDS), l1));
		
		Hand h3 = new Hand();
		h3.add(a4C);
		h3.add(aJC);
		h3.add(aJS);
		h3.add(aLJo);
		
		CardList l3 = new CardList();
		l3.add(aJS);
		assertTrue(isSameList(h3.playableCards(Suit.SPADES, null), l3));
		
		l3 = new CardList();
		l3.add(a4C);
		l3.add(aJC);
		l3.add(aJS);
		l3.add(aLJo);
		assertTrue(isSameList(h3.playableCards(Suit.DIAMONDS, null), l3));
		
		l3 = new CardList();
		l3.add(aJS);
		assertTrue(isSameList(h3.playableCards(Suit.SPADES, null), l3));
		
		l3 = new CardList();
		l3.add(aJC);
		l3.add(aJS);
		l3.add(aLJo);
		assertTrue(isSameList(h3.playableCards(Suit.SPADES, Suit.SPADES), l3));
		
		l3 = new CardList();
		l3.add(a4C);
		assertTrue(isSameList(h3.playableCards(Suit.CLUBS, Suit.SPADES), l3));
		
		l3 = new CardList();
		l3.add(aLJo);
		assertTrue(isSameList(h3.playableCards(Suit.HEARTS, Suit.HEARTS), l3));
		
		l3 = new CardList();
		l3.add(a4C);
		l3.add(aJC);
		l3.add(aJS);
		l3.add(aLJo);
		assertTrue(isSameList(h3.playableCards(Suit.DIAMONDS, Suit.HEARTS), l3));
		
		
		Hand h4 = new Hand();
		h4.add(aJS);
		h4.add(aJC);
		h4.add(aJD);
		h4.add(aJH);
		h4.add(aQS);
		h4.add(aQC);
		h4.add(aQD);
		h4.add(aQH);
		h4.add(aLJo);
		
		CardList l4 = new CardList();
		l4.add(aJS);
		l4.add(aQS);
		assertTrue(isSameList(h4.playableCards(Suit.SPADES, null),l4));
		
		l4 = new CardList();
		l4.add(aJS);
		l4.add(aJC);
		l4.add(aQS);
		l4.add(aLJo);
		assertTrue(isSameList(h4.playableCards(Suit.SPADES, Suit.SPADES),l4));
		
		l4 = new CardList();
		l4.add(aQS);
		assertTrue(isSameList(h4.playableCards(Suit.SPADES, Suit.CLUBS),l4));
		
		l4 = new CardList();
		l4.add(aJS);
		l4.add(aQS);
		assertTrue(isSameList(h4.playableCards(Suit.SPADES, Suit.DIAMONDS),l4));
	}
	
	@Test
	public void testNumberOfCards(){
		assertEquals(h1.numberOfCards(Suit.SPADES, null), 2);
		assertEquals(h1.numberOfCards(Suit.SPADES, Suit.SPADES), 2);
		assertEquals(h1.numberOfCards(Suit.SPADES, Suit.CLUBS), 1);
		assertEquals(h1.numberOfCards(Suit.HEARTS, Suit.DIAMONDS), 2);
		assertEquals(h1.numberOfCards(Suit.CLUBS, Suit.CLUBS), 3);
		
		assertEquals(h2.numberOfCards(Suit.SPADES, null), 2);
		assertEquals(h2.numberOfCards(Suit.SPADES, Suit.SPADES), 3);
		assertEquals(h2.numberOfCards(Suit.SPADES, Suit.CLUBS), 2);
		assertEquals(h2.numberOfCards(Suit.HEARTS, Suit.DIAMONDS), 2);
		assertEquals(h2.numberOfCards(Suit.CLUBS, Suit.CLUBS), 2);
	}
	
	// helper method
	// decide if two cardLists are equivalent
	private boolean isSameList(CardList l1, CardList l2){
		if (l1.size()!=l2.size())
		{
			return false;
		}
		Iterator<Card> i1 = l1.iterator();
		Iterator<Card> i2 = l2.iterator();
		while(i1.hasNext()){
			if (!i1.next().equals(i2.next()))
			{
				return false;
			}
		}
		return true;
	}
	
	private Hand h;
	private Hand jokerhand;
	private Hand allClubs;
	private Hand jackHand;

	@Before
	public void setUp() throws Exception
	{
		h = new Hand();
		
		h.add(AllCards.a7D);
		h.add(AllCards.aQD);
		h.add(AllCards.aKD);		
		h.add(AllCards.a9H);
		h.add(AllCards.aTS);
		h.add(AllCards.a7S);
		h.add(AllCards.aJS);
		h.add(AllCards.aKS);
		h.add(AllCards.a6C);
		h.add(AllCards.aTC);
		h.add(AllCards.aLJo);
		
		jokerhand = new Hand();
		jokerhand.add(AllCards.aLJo);
		jokerhand.add(AllCards.aHJo);
		
		allClubs = new Hand();
		allClubs.add(AllCards.a4C);
		allClubs.add(AllCards.a9C);
		allClubs.add(AllCards.aJC);
		allClubs.add(AllCards.aHJo);
		
		jackHand = new Hand();
		jackHand.add(AllCards.aJS);
		jackHand.add(AllCards.aJC);
		
	}

	@Test
	public void testClone2()
	{	
		Hand hClone = h.clone();
		Assert.assertTrue(((CardList) h).toString().equals(((CardList) hClone).toString()));
	}

//	@Test
//	public void testCanLead()
//	{
//		// cards beside jokers
//		
//		CardList nonJokerList = new CardList();
//		nonJokerList.add(AllCards.a7D);
//		nonJokerList.add(AllCards.aQD);
//		nonJokerList.add(AllCards.aKD);
//		nonJokerList.add(AllCards.a9H);		
//		nonJokerList.add(AllCards.aTS);
//		nonJokerList.add(AllCards.aJS);		
//		nonJokerList.add(AllCards.a7S);		
//		nonJokerList.add(AllCards.aKS);
//		nonJokerList.add(AllCards.a6C);
//		nonJokerList.add(AllCards.aTC);		
//		nonJokerList = nonJokerList.sort(new Card.ByRankComparator());
//		
//		assertTrue(h.canLead().sort(new Card.ByRankComparator()).toString().equals(nonJokerList.toString()));
//
//		// only jokers left
//		CardList jokerList = new CardList();
//		jokerhand.remove(AllCards.aHJo);
//		jokerList.add(AllCards.aLJo);
//		assertTrue(jokerhand.canLead().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));
//		
//		jokerhand.add(AllCards.aHJo);
//		jokerList.add(AllCards.aHJo);
//		jokerList = jokerList.sort(new Card.ByRankComparator());		
//		assertTrue(jokerhand.canLead().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));
//	}

	@Test
	public void testGetJokers2()
	{

		h.remove(AllCards.aLJo);		
		assertEquals(h.getJokers().size(),0);
		
		CardList jokerList = new CardList();
		jokerhand.remove(AllCards.aHJo);
		jokerList.add(AllCards.aLJo);		
		assertTrue(jokerhand.getJokers().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));
		
		jokerhand.add(AllCards.aHJo);
		jokerList.add(AllCards.aHJo);
		jokerList = jokerList.sort(new Card.ByRankComparator());		
		assertTrue(jokerhand.getJokers().sort(new Card.ByRankComparator()).toString().equals(jokerList.toString()));
		
		assertEquals(jackHand.getJokers().size(), 0);
	}

	@Test
	public void testGetNonJokers2()
	{
		CardList nonJokerList = new CardList();
		nonJokerList.add(AllCards.a7D);
		nonJokerList.add(AllCards.aQD);
		nonJokerList.add(AllCards.aKD);
		nonJokerList.add(AllCards.a9H);		
		nonJokerList.add(AllCards.aTS);
		nonJokerList.add(AllCards.aJS);		
		nonJokerList.add(AllCards.a7S);		
		nonJokerList.add(AllCards.aKS);
		nonJokerList.add(AllCards.a6C);
		nonJokerList.add(AllCards.aTC);		
		nonJokerList = nonJokerList.sort(new Card.ByRankComparator());
		
		assertTrue( h.getNonJokers().sort(new Card.ByRankComparator()).toString().equals(nonJokerList.toString()));
		
		assertEquals( jokerhand.getNonJokers().size(), 0);
		
		CardList jackList = new CardList();
		jackList.add(AllCards.aJS);
		jackList.add(AllCards.aJC);
		assertTrue(jackHand.getNonJokers().toString().equals(jackList.toString()));

	}

	@Test
	public void testGetTrumpCards2()
	{
		
		CardList clubList = new CardList();
		clubList.add(AllCards.a6C);
		clubList.add(AllCards.aTC);		
		clubList.add(AllCards.aJS);
		clubList.add(AllCards.aLJo);
		clubList = clubList.sort(new Card.ByRankComparator());
		
		CardList diamondsList = new CardList();
		diamondsList.add(AllCards.a7D);
		diamondsList.add(AllCards.aQD);
		diamondsList.add(AllCards.aKD);
		diamondsList.add(AllCards.aLJo);
		diamondsList = diamondsList.sort(new Card.ByRankComparator());
		
		CardList heartsList = new CardList();
		heartsList.add(AllCards.a9H);
		heartsList.add(AllCards.aLJo);
		heartsList = heartsList.sort(new Card.ByRankComparator());		
		
		CardList spadesList = new CardList();
		spadesList.add(AllCards.aTS);
		spadesList.add(AllCards.a7S);
		spadesList.add(AllCards.aJS);
		spadesList.add(AllCards.aKS);
		spadesList.add(AllCards.aLJo);

		spadesList = spadesList.sort(new Card.ByRankComparator());		

		assertTrue(h.getTrumpCards(Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(clubList.toString()));
		assertTrue(h.getTrumpCards(Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(diamondsList.toString()));
		assertTrue(h.getTrumpCards(Suit.HEARTS).sort(new Card.ByRankComparator()).toString().equals(heartsList.toString()));
		assertTrue(h.getTrumpCards(Suit.SPADES).sort(new Card.ByRankComparator()).toString().equals(spadesList.toString()));
	}

	@Test
	public void testGetNonTrumpCards2()
	{
		
		CardList nonClubList = new CardList();
		nonClubList.add(AllCards.a7D);
		nonClubList.add(AllCards.aQD);
		nonClubList.add(AllCards.aKD);
		nonClubList.add(AllCards.a9H);		
		nonClubList.add(AllCards.aTS);
		nonClubList.add(AllCards.a7S);		
		nonClubList.add(AllCards.aKS);		
		nonClubList = nonClubList.sort(new Card.ByRankComparator());
		
		assertTrue( h.getNonTrumpCards(Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(nonClubList.toString()));
		
		
		CardList nonDiamondList = new CardList();		
		nonDiamondList.add(AllCards.a6C);
		nonDiamondList.add(AllCards.aTC);		
		nonDiamondList.add(AllCards.a9H);		
		nonDiamondList.add(AllCards.aTS);
		nonDiamondList.add(AllCards.a7S);
		nonDiamondList.add(AllCards.aJS);
		nonDiamondList.add(AllCards.aKS);		
		nonDiamondList = nonDiamondList.sort(new Card.ByRankComparator());
		
		assertTrue( h.getNonTrumpCards(Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(nonDiamondList.toString()));
		
		CardList nonHeartList = new CardList();		
		nonHeartList.add(AllCards.a6C);
		nonHeartList.add(AllCards.aTC);
		nonHeartList.add(AllCards.a7D);
		nonHeartList.add(AllCards.aQD);
		nonHeartList.add(AllCards.aKD);
		nonHeartList.add(AllCards.aTS);
		nonHeartList.add(AllCards.a7S);
		nonHeartList.add(AllCards.aJS);
		nonHeartList.add(AllCards.aKS);		
		nonHeartList = nonHeartList.sort(new Card.ByRankComparator());
		
		assertTrue( h.getNonTrumpCards(Suit.HEARTS).sort(new Card.ByRankComparator()).toString().equals(nonHeartList.toString()));
		
		CardList nonSpadesList = new CardList();		
		nonSpadesList.add(AllCards.a6C);
		nonSpadesList.add(AllCards.aTC);
		nonSpadesList.add(AllCards.a7D);
		nonSpadesList.add(AllCards.aQD);
		nonSpadesList.add(AllCards.aKD);
		nonSpadesList.add(AllCards.a9H);
		nonSpadesList = nonSpadesList.sort(new Card.ByRankComparator());
		
		assertTrue( h.getNonTrumpCards(Suit.SPADES).sort(new Card.ByRankComparator()).toString().equals(nonSpadesList.toString()));
		
		assertEquals(jackHand.getNonTrumpCards(Suit.SPADES).size(), 0);
		assertEquals(jackHand.getNonTrumpCards(Suit.CLUBS).size(), 0);
	}

	@Test
	public void testSelectLowest2()
	{
		// no-trump		
		assertTrue(h.selectLowest(null).equals(AllCards.a6C));
		
		//trump
		assertTrue(h.selectLowest(Suit.CLUBS).equals(AllCards.a7S));
		assertTrue(h.selectLowest(Suit.DIAMONDS).equals(AllCards.a6C));
		assertTrue(h.selectLowest(Suit.HEARTS).equals(AllCards.a6C));
		assertTrue(h.selectLowest(Suit.SPADES).equals(AllCards.a6C));
		
		assertTrue(jokerhand.selectLowest(Suit.SPADES).equals(AllCards.aLJo));
		
		assertTrue(allClubs.selectLowest(Suit.CLUBS).equals(AllCards.a4C));
	}

	@Test
	public void testPlayableCards2()
	{
//		// no-Trump not specified
		
		CardList clubList = new CardList();
		clubList.add(AllCards.a6C);
		clubList.add(AllCards.aTC);		
//		assertTrue( h.playableCards(Suit.CLUBS, null).toString().equals(clubList.toString()));
		
		CardList diamondsList = new CardList();
		diamondsList.add(AllCards.a7D);
		diamondsList.add(AllCards.aQD);
		diamondsList.add(AllCards.aKD);
//		assertTrue( h.playableCards(Suit.DIAMONDS, null).toString().equals(diamondsList.toString()));
		
		CardList heartsList = new CardList();
		heartsList.add(AllCards.a9H);		
//		assertTrue( h.playableCards(Suit.HEARTS, null).toString().equals(heartsList.toString()));
		
		CardList spadesList = new CardList();
		spadesList.add(AllCards.aTS);
		spadesList.add(AllCards.a7S);
		spadesList.add(AllCards.aJS);
		spadesList.add(AllCards.aKS);
//		assertTrue( h.playableCards(Suit.SPADES, null).toString().equals(spadesList.toString()));
		
		CardList joker = new CardList();			
		joker.add(AllCards.aLJo);		
		joker.add(AllCards.aHJo);
//		assertTrue(jokerhand.playableCards(Suit.DIAMONDS, null).sort(new Card.ByRankComparator()).toString().equals(joker.toString()));
		
		
		// Trump
		clubList.add(AllCards.aJS);
		clubList.add(AllCards.aLJo);
		clubList = clubList.sort(new Card.ByRankComparator());
		assertTrue( h.playableCards(Suit.CLUBS, Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(clubList.toString()));
		
		diamondsList.add(AllCards.aLJo);
		diamondsList = diamondsList.sort(new Card.ByRankComparator());
		assertTrue( h.playableCards(Suit.DIAMONDS, Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(diamondsList.toString()));

		heartsList = heartsList.sort(new Card.ByRankComparator());		
		assertTrue( h.playableCards(Suit.HEARTS, Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(heartsList.toString()));
		
		spadesList.remove(AllCards.aJS);
		spadesList = spadesList.sort(new Card.ByRankComparator());
		assertTrue( h.playableCards(Suit.SPADES, Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(spadesList.toString()));
		
		// no Trump in hand
		assertTrue(jokerhand.playableCards(Suit.DIAMONDS, Suit.DIAMONDS).sort(new Card.ByRankComparator()).toString().equals(joker.toString()));
		assertTrue(jokerhand.playableCards(Suit.DIAMONDS, Suit.CLUBS).sort(new Card.ByRankComparator()).toString().equals(joker.toString()));
		
		CardList jackList = new CardList();
		jackList.add(AllCards.aJS);
		jackList.add(AllCards.aJC);
		assertTrue(jackHand.playableCards(Suit.CLUBS, Suit.SPADES).sort(new Card.ByRankComparator()).toString().equals(jackList.toString()));
		
	}

	@Test
	public void testNumberOfCards2()
	{
		// Trump
		assertTrue(h.numberOfCards(Suit.CLUBS, Suit.CLUBS) == 3); // +1 for jack of spades 
		assertTrue(h.numberOfCards(Suit.DIAMONDS, Suit.DIAMONDS) == 3);
		assertTrue(h.numberOfCards(Suit.HEARTS, Suit.DIAMONDS) == 1);
		assertTrue(h.numberOfCards(Suit.SPADES, Suit.CLUBS) == 3); //-1 for jack of spades 
		
	}
}
