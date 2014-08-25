package comp303.fivehundred.ai;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.ai.advanced.AdvancedPlayingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;

/**
 * @author Stephanie Pataracchia 260407002
 */

public class TestAdvancedPlayingStrategy
{

	Trick pTrickSpades;
	Trick pTrickNoTrump;
	Hand pHand;
	AdvancedPlayingStrategy player;

	/* These cases may be tricky. Consider them wisely, post on the boards if you're not sure about one */

	@Before
	public void init()
	{
		// spades
		pTrickSpades = new Trick(new Bid(0));
		// no trump
		pTrickNoTrump = new Trick(new Bid(24));
		
		player = new AdvancedPlayingStrategy();
	}



	// *********************
	//	     Leading
	// *********************

	// WITH TRUMP

	// no trump, no jokers in hand
	// should lead highest card of suit they have the least of
	@Test
	public void leadingTest01()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a5C); 
		pHand.add(a7C);
		pHand.add(a8C);
		pHand.add(a9C);
		pHand.add(aTC);

		assertEquals(player.play(pTrickSpades, pHand), a9H);
	}
	// Jokers in hand, shouldn't lead with a joker
	@Test
	public void leadingTest02()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a5C); 
		pHand.add(aTC);
		pHand.add(aLJo);
		pHand.add(a9C);
		pHand.add(aTC);

		assertEquals(player.play(pTrickSpades, pHand), a9H);
	}
	// No jokers, has trump
	// Should lead highest non joker trump 
	@Test
	public void leadingTest03()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a5C); 
		pHand.add(aTC);
		pHand.add(aTS);
		pHand.add(a9C);
		pHand.add(aTC);

		assertEquals(player.play(pTrickSpades, pHand), aTS);
	}
	// NO TRUMP

	// has jokers, 
	// leads the highest card of suit there is the least of
	@Test
	public void leadingTest04()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a5C); 
		pHand.add(aLJo);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		assertEquals(player.play(pTrickNoTrump, pHand), a9H);
	}
	// no jokers
	// leads the highest card of suit there is the least of
	@Test
	public void leadingTest05()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a5C); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		assertEquals(player.play(pTrickNoTrump, pHand), a9H);
	}
	// *************************************************
	//	     Going Last and Partner has already won
	// *************************************************

	// TRUMP

	// Partner has won with a joker
	// Should play lowest card that can follow suit
	@Test
	public void winningPartner01()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aLJo);
		pTrickSpades.add(a5H);
		assertEquals(player.play(pTrickSpades, pHand), a6H);
	}
			

	// Partner has won with trump
	// Should play lowest card that can follow suit
	@Test
	public void winningPartner02()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aJC);
		pTrickSpades.add(a5H);
		assertEquals(player.play(pTrickSpades, pHand), a6H);
	}
	// Partner has won with non trump
	// Should play lowest card that can follow suit
	@Test
	public void winningPartner03()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aAH);
		pTrickSpades.add(a5H);
		assertEquals(player.play(pTrickSpades, pHand), a6H);
	}
	// Partner has won with non trump
	// trump in hand
	// Should play lowest card that can follow suit
	@Test
	public void winningPartner04()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6S);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aAH);
		pTrickSpades.add(a5H);
		assertEquals(player.play(pTrickSpades, pHand), a6H);
	}	
	// NO TRUMP

	// Partner has won with joker
	// Should play lowest card that can follow suit
	@Test
	public void winningPartner05()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickNoTrump.add(aQH);
		pTrickNoTrump.add(aLJo);
		pTrickNoTrump.add(a5H);
		assertEquals(player.play(pTrickNoTrump, pHand), a6H);
	}
	// Partner has won with non joker
	// Should play lowest card that can follow suit
	@Test
	public void winningPartner06()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickNoTrump.add(aQH);
		pTrickNoTrump.add(aAH);
		pTrickNoTrump.add(a5H);
		assertEquals(player.play(pTrickNoTrump, pHand), a6H);
	}
	// *******************************************************
	//	    Otherwise
	// *******************************************************

	// opponent has won with high joker
	// Should play lowest card that can follow suit
	@Test
	public void winningOponent01()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(a9H); 
		pHand.add(a6H); 
		pHand.add(a6C);
		pHand.add(a7C);
		pHand.add(a9C);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aHJo);
		pTrickSpades.add(a5H);

		assertEquals(player.play(pTrickSpades, pHand), a6H);
	}
	// opponent has won with low joker
	// High joker in hand, can't follow suit
	// Should play high Joker
	@Test
	public void winningOponent02()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(aHJo); 
		pHand.add(a6S); 
		pHand.add(a6C);
		pHand.add(aTD);//when clubs, 5 of diamonds
		pHand.add(a9D);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aLJo);

		assertEquals(player.play(pTrickSpades, pHand), aHJo);
	}
	// opponent has won with low joker
	// High joker in hand, can follow suit
	// Should play lowest card that can follow suit
	@Test
	public void winningOponent03()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(aHJo); 
		pHand.add(a6S); 
		pHand.add(a6C);
		pHand.add(aHJo);//when clubs, 5 of diamonds
		pHand.add(a9D);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aLJo);

		assertEquals(player.play(pTrickSpades, pHand), aHJo);
	}

	// opponent has won with non trump
	// higher card of that suit in hand
	// Should play lowest card of lead suit that can win
	@Test
	public void winningOponent04()
	{
		pHand = new Hand();
		
		pHand.add(a5D); 
		pHand.add(a6D); 
		pHand.add(a7D); 
		pHand.add(a8D); 
		pHand.add(aAH); 
		pHand.add(aKC); 
		pHand.add(a6C);
		pHand.add(aKD);//when clubs, 5 of diamonds
		pHand.add(a9D);
		pHand.add(aTC);

		pTrickSpades.add(aQH);
		pTrickSpades.add(aKH);

		assertEquals(player.play(pTrickSpades, pHand), aAH);
	}



}
