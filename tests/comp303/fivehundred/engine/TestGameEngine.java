package comp303.fivehundred.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp303.fivehundred.ai.IPlayer;
import comp303.fivehundred.ai.random.RandomRobot;
import comp303.fivehundred.engine.GameEngine;

/**
 * @author Ioannis Fytilis 260482744
 */

public class TestGameEngine
{
	RandomRobot p1;
	RandomRobot p2;
	RandomRobot p3;
	RandomRobot p4;
	GameEngine game;
	
	@Before
	public void init(){
		RandomRobot p1 = new RandomRobot("Alice");
		RandomRobot p2 = new RandomRobot("Bob");
		RandomRobot p3 = new RandomRobot("Carl");
		RandomRobot p4 = new RandomRobot("Don");
		
		IPlayer[] players = {p1,p2,p3,p4};
		
		game = new GameEngine(players);
		while (game.allPasses())
		{
			game.deal();
			game.bid();
		}
	}
	
	@Test
	public void bidTest()
	{
		for (int i = 0; i<500; i++)
		{
			game.newGame();
			while (game.allPasses())
			{
				game.deal();
				game.bid();
			}
			assertTrue(!game.getBid(0).isPass() || !game.getBid(1).isPass() || !game.getBid(2).isPass() || !game.getBid(3).isPass());
		}
	}
	
	@Test
	public void HandTest()
	{
		for (int i = 0; i<500; i++)
		{
			game.newGame();
			while (game.allPasses())
			{
				game.deal();
				game.bid();
			}
			game.exchange();
			assertTrue(game.getHand(0).size()==10 && game.getHand(1).size()==10 && game.getHand(2).size()==10 && game.getHand(3).size()==10);
		}
	}
	
	@Test
	public void trickTest()
	{
		for (int i = 0; i<500; i++)
		{
			game.newGame();
			while (game.allPasses())
			{
				game.deal();
				game.bid();
			}
			game.exchange();
			game.playTrick();
			assertTrue(game.getTrick().size()==4);
			assertTrue(game.getHand(0).size()==9 && game.getHand(1).size()==9 && game.getHand(2).size()==9 && game.getHand(3).size()==9);

		}
	}
	
}
