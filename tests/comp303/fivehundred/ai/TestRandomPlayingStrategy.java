package comp303.fivehundred.ai;

import org.junit.Before;
import org.junit.Test;
import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;
import comp303.fivehundred.ai.random.RandomPlayingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/** 
 * @author Ioannis Fytilis 260482744
 * This is the test class for RandomBiddingStrategy
 */

public class TestRandomPlayingStrategy
{
	
	Hand pHand;
	Trick pTrickNoTrump;
	Trick pTrickHearts;
	Trick pTrickSpades;
	RandomPlayingStrategy player;
	
	@Before
	public void init(){
		pHand = new Hand();
		pHand.add(a8C);
		pHand.add(a5H);
		pHand.add(aAD);
		pHand.add(aLJo);
		pHand.add(aHJo);
		pHand.add(a4C);
		pHand.add(a9S);
		pHand.add(a6H);
		pHand.add(a8S);
		pHand.add(a8H);
		
		pTrickNoTrump = new Trick(new Bid(24));	// Contract is no trump
		pTrickHearts = new Trick(new Bid(3));	// Contract is 6 of hearts
		pTrickSpades = new Trick(new Bid(0));	// Contract is 6 of spades
		
		player = new RandomPlayingStrategy();
	}
	
	@Test
	public void testLead(){
		boolean jokerPlayed = false;
		for (int i=0; i<1000; i++){
			Card cardPlayed = player.play(pTrickHearts, pHand);
			if (cardPlayed.isJoker()){
				jokerPlayed = true; 
			}
		}
		assertTrue(jokerPlayed);
	}
	
	@Test
	public void testPlay(){
		for (int i=0; i<10; i++){
			Card cardPlayed = player.play(pTrickNoTrump, pHand);
			if (pHand.getNonJokers().size()!=0){
				assertTrue(!cardPlayed.equals(aLJo));
				assertTrue(!cardPlayed.equals(aHJo));
			}
			pHand.remove(cardPlayed);
		}
	}
	
	@Test
	public void followHeart(){
		pTrickHearts.add(a7H);
		for (int i=0; i<100; i++){
			Card cardPlayed = player.play(pTrickHearts, pHand);
			if (!cardPlayed.isJoker()){
				assertEquals(cardPlayed.getSuit(), Suit.HEARTS);
			}
		}
	}
	
	@Test
	public void jokerLeadTest(){
		pTrickHearts.add(aLJo);
		for (int i=0; i<100; i++){
			Card cardPlayed = player.play(pTrickHearts, pHand);
			if (!cardPlayed.isJoker()){
				assertEquals(cardPlayed.getSuit(), Suit.HEARTS);
			}
		}
	}
	
	@Test
	public void jackLeadtest(){
		pTrickHearts.add(aJH);
		for (int i=0; i<100; i++){
			Card cardPlayed = player.play(pTrickHearts, pHand);
			if (!cardPlayed.isJoker()){
				assertEquals(cardPlayed.getSuit(), Suit.HEARTS);
			}
		}
	}
	
	@Test
	public void jackConverseLeadtest(){
		pTrickHearts.add(aJD);
		for (int i=0; i<100; i++){
			Card cardPlayed = player.play(pTrickHearts, pHand);
			if (!cardPlayed.isJoker()){
				assertEquals(cardPlayed.getSuit(), Suit.HEARTS);
			}
		}
	}
	
	@Test
	public void testRandomPlayingStrategyConstructorOnly()
	{
		new RandomPlayingStrategy ();	
	}
	

/*	@Test(expected = NullPointerException.class)
	public void testRandomPlayingStrategyPlayEmptyArgumentHand()
	{
		RandomPlayingStrategy randomPlayingStrategy = new RandomPlayingStrategy ();
		randomPlayingStrategy.play(null, null);
	}
	
*/
	
/*	@Test(expected = NullPointerException.class)
	public void testRandomPlayingStrategyPlayEmptyArgumentTrick()
	{
		RandomPlayingStrategy randomPlayingStrategy = new RandomPlayingStrategy ();
		Hand h = new Hand();
		h.add(new Card(Rank.FOUR,Suit.SPADES));
		h.add(new Card(Rank.FIVE,Suit.CLUBS));
		randomPlayingStrategy.play(null, h);
	}
	
*/	
	@Test
	public void testRandomPlayingStrategyPlay()
	{
		RandomPlayingStrategy randomPlayingStrategy = new RandomPlayingStrategy ();
		
		Hand h = new Hand();
		h.add(new Card(Rank.FIVE,Suit.SPADES));
		//Card c = randomPlayingStrategy.play(null, h);
		//assertTrue((c.getRank()==Rank.FIVE)&&(c.getSuit()==Suit.SPADES));
		
		//h.add(new Card(Rank.FIVE,Suit.SPADES));
		Card c = randomPlayingStrategy.play(new Trick(new Bid(10)), h);
		
		assertTrue((c.getRank()==Rank.FIVE)&&(c.getSuit()==Suit.SPADES));
		
		h.add(new Card(Rank.FIVE,Suit.DIAMONDS));		
		h.add(new Card(Rank.FIVE,Suit.CLUBS));		
		h.add(new Card(Rank.FIVE,Suit.HEARTS));
		h.add(new Card(Rank.FOUR,Suit.DIAMONDS));		
		h.add(new Card(Rank.FOUR,Suit.SPADES));		
		h.add(new Card(Rank.FOUR,Suit.CLUBS));		
		h.add(new Card(Rank.FOUR,Suit.HEARTS));
		h.add(new Card(Rank.SIX,Suit.DIAMONDS));		
		h.add(new Card(Rank.SIX,Suit.SPADES));		
		h.add(new Card(Rank.SIX,Suit.CLUBS));		
		h.add(new Card(Rank.SIX,Suit.HEARTS));
		Card myCard = randomPlayingStrategy.play(new Trick(new Bid(20)), h);

		assertTrue(myCard.getRank()==Rank.FOUR || myCard.getRank()==Rank.FIVE || myCard.getRank()==Rank.SIX); 
		
	}
	
}
