package comp303.fivehundred.engine;

import comp303.fivehundred.ai.IPlayer;
import comp303.fivehundred.ai.advanced.AdvancedRobot;
import comp303.fivehundred.ai.basic.BasicRobot;

/**
 * 
 * @author Ioannis Fytilis 260482744
 *
 */
public final class Driver
{
	
	private static final int HAND_SIZE = 10;
	private static final int NB_GAMES = 10000;
	
	private static GameLogging aGameLogging;
	
	private Driver(){}
	
	/**
	 * Runs multiple games.
	 * @param pArgs
	 * 		Arguments
	 */
	public static void main(String[] pArgs)
	{
		IPlayer[] pPlayers = {new BasicRobot("BasicA"), new AdvancedRobot("AdvancedA"), new BasicRobot("BasicB"), new AdvancedRobot("AdvancedB")};
		runGame(pPlayers, false, NB_GAMES);	// LOGGING INSTRUCTIONS: if the boolean parameter of the runGame method call is false, logging is OFF
	}										// if the boolean parameter is true, logging is ON
	
	
	/**
	 * Runs a game.
	 * @param pPlayers
	 * 		The behavior of each player (random robot, basic robot, human...)
	 * @param pIsLoggingOn 
	 * 		decides whether logging should be on or off
	 * @param pGames
	 * 		The number of games to play
	 */
	private static void runGame(IPlayer[] pPlayers, boolean pIsLoggingOn, int pGames)
	{
		GameEngine pGame = new GameEngine(pPlayers);
		
		if (pIsLoggingOn)
		{
			aGameLogging = new GameLogging(pGame);
			pGame.addObserver(aGameLogging);
			aGameLogging.logToFile(true);
		}
		
		GameStatistics aGameStats = new GameStatistics(pGame);
		pGame.addObserver(aGameStats);
		
		for ( int i = 0; i<pGames; i++)
		{
			pGame.newGame();
			while (!pGame.isGameOver())
			{
				while (pGame.allPasses())
				{
					pGame.deal();
					pGame.bid();
				}
				
				pGame.exchange();
				
				for( int j = 0; j < HAND_SIZE; j++ )
				{
					pGame.playTrick();
				}
				pGame.computeScore();
			}
		}
		if (pIsLoggingOn)
		{
			aGameLogging.logToFile(false);
		}
		aGameStats.printStatistics();
		
	}
	
}
