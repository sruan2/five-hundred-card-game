package comp303.fivehundred.util;

import static org.junit.Assert.*;
import static comp303.fivehundred.util.AllCards.*;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import comp303.fivehundred.util.Card.ByRankComparator;

/**
 * @author Ioannis Fytilis 260482744
 */

public class TestCardList
{
	
	CardList testList;
	
	@Before
	public void init(){
		testList = new CardList();
	}
	
	@Test
	public void addRemoveTest(){
		
		assertEquals(0, testList.size());
		
		testList.add(a4C);
		assertEquals(1, testList.size());
		testList.add(a4C);
		assertEquals(1, testList.size());
		
		assertEquals(a4C, testList.getFirst());
		assertEquals(a4C, testList.getLast());
		assertEquals(a4C, testList.random());
		
		testList.remove(a5H);
		testList.remove(aLJo);
		testList.remove(a7D);
		
		assertEquals(1, testList.size());

		testList.remove(a4C);
		
		assertEquals(0,testList.size());
		
	}
	
	@Test
	public void cloneTest(){
		
		Deck cardPack = new Deck();
		for (int i = 0; i<46; i++){
			testList.add(cardPack.draw());
		}
		
		CardList cloned = testList.clone();
		assertNotNull(cloned);
		assertNotSame(cloned, testList);
		assertFalse(cloned.equals(testList));
		
		int listSize = testList.size();
		
		for (int i = 0; i<listSize; i++){
			assertTrue(cloned.getFirst().equals(testList.getFirst()));
			assertNotNull(cloned.getFirst());
			assertNotSame(cloned.getFirst(), testList.getFirst());
			
			cloned.remove(cloned.getFirst());
			testList.remove(testList.getFirst());
		}
		
	}
	
	@Test
	public void stringTest(){
		
		assertEquals("<>", testList.toString());
		
		testList.add(a7D);
		
		assertEquals("<7D>", testList.toString());
		
		testList.add(aHJo);
		
		assertEquals("<7D,HJ>", testList.toString());
		
		testList.add(aTD);
		testList.add(aLJo);
		testList.add(aLJo);
		testList.add(aQC);
		testList.add(aLJo);
		testList.add(aKS);
		testList.add(a5H);
		testList.add(aTD);
		testList.add(aLJo);
		testList.add(aLJo);
		testList.remove(aLJo);
		
		assertEquals("<7D,HJ,TD,QC,KS,5H>", testList.toString());
		
	}
	
	@Test (expected = GameUtilException.class)
	public void firstCard(){
		testList.getFirst();
	}
	
	@Test (expected = GameUtilException.class)
	public void lastCard(){
		testList.getLast();
	}
	
	@Test (expected = AssertionError.class)
	public void addCard(){
		testList.add(null);
	}
	
	@Test (expected = AssertionError.class)
	public void deleteCard(){
		testList.remove(null);
	}
	
	@Test
	public void iterateTest(){
		
		Deck cardPack = new Deck();
		for (int i = 0; i<46; i++){
			testList.add(cardPack.draw());
		}	
		int count = 0;
		for (Card card : testList){
			if (card!=null){
				count++;
			}
		}
		assertEquals(46,count);
	}
	
	@Test
	public void iterateTest2(){
		
		Deck cardPack = new Deck();
		for (int i = 0; i<46; i++){
			testList.add(cardPack.draw());
		}	
		int count = 0;
		for (Iterator<Card> itr = testList.iterator(); itr.hasNext();){
			if (itr.next()!=null){
				count++;
			}
		}
		assertEquals(46,count);
	}
	
	@Test(expected=GameUtilException.class)
	public void testEmptyCardListGetFirst() 
	{
		CardList lList = new CardList();
		lList.getFirst();
	}
	
	@Test(expected=GameUtilException.class)
	public void testEmptyCardListGetLast() 
	{
		CardList lList = new CardList();
		lList.getLast();
	}
	
	@Test
	public void testGetFirstAndLast()
	{
		CardList lList = new CardList();
		lList.add(a4D);
		lList.add(a6D);
		lList.sort(new ByRankComparator());
		assertEquals(a4D, lList.getFirst());
		assertEquals(a6D, lList.getLast());
	}
	
	@Test
	public void testAdd()
	{
		CardList lList = new CardList();
		lList.add(a4C);		
		assertEquals(1, lList.size());
		assertTrue(lList.getFirst().equals(a4C));
	}

	@Test
	public void testRemove()
	{
		CardList lList = new CardList();
		lList.add(a4C);
		lList.remove(a4C);
		assertTrue(lList.size() == 0);	
	}
	
	@Test
	public void testDoubleAdd()
	{
		CardList lCards = new CardList();
		lCards.add(a4C);
		lCards.add(a4C);
		assertEquals(1, lCards.size());
		
	}
	
	@Test
	public void testCloneDistinct()
	{
		CardList list = new CardList();
		CardList clone = list.clone();		
		assertTrue( list != clone );
	}
	
	@Test
	public void testSort() 
	{
		CardList lList = new CardList();
		
		lList.add(a4D);
		lList.add(a6D);
		
		CardList aList = lList.sort(new ByRankComparator());
		assertNotSame(lList, aList);
		assertEquals(a4D, aList.getFirst());
		assertEquals(a4D, lList.getFirst());
	}
}
