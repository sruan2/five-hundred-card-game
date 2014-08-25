package comp303.fivehundred.gui;

import comp303.fivehundred.ai.IPlayer;
import comp303.fivehundred.ai.advanced.AdvancedRobot;
import comp303.fivehundred.ai.basic.BasicRobot;
import comp303.fivehundred.ai.human.HumanPlayer;
import comp303.fivehundred.ai.random.RandomRobot;
import comp303.fivehundred.engine.GameEngine;
import comp303.fivehundred.engine.GameLogging;
import comp303.fivehundred.engine.GameStatistics;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.InputDialog.PlayerType;

/**
 * High-level GameEngine controller.
 * 
 * @author Ioannis Fytilis 260482744
 */
public final class GameRunner
{

	public static final int TRICK_NB = 10;

	private static GameEngine aGame;
	private static GameStatistics aStats;
	private static IPlayer[] aPlayers;
	private static InputDialog aDialog;
	private static Mode aCurrentMode = Mode.WAIT_NEW;

	private GameRunner()
	{
	}

	/**
	 * Represents the state of the game.
	 */
	public enum Mode
	{
		WAIT_NEW, WAIT_BID, WAIT_WIDOW, WAIT_DISCARD, WAIT_CLICK_EXCHANGE, WAIT_PLAY, WAIT_CLICK, WAIT_NEXTROUND, AUTOPLAY
	}

	/**
	 * Starts one game.
	 */
	public static void play()
	{
		if (isHumanMode())
		{
			aGame.newGame();
			runBeforeHumanBid();

		}
		else
		{
			nextGame();
		}
	}

	/**
	 * Players who must bid before the human player do so.
	 */
	public static void runBeforeHumanBid()
	{
		aGame.deal();
		aGame.bidBeforeHuman();
		aCurrentMode = Mode.WAIT_BID;
	}

	/**
	 * The human player and other players who must bid after him do so.
	 */
	public static void runAfterHumanBid()
	{
		aGame.bidAfterHuman();
		if (aGame.allPasses())
		{
			runBeforeHumanBid();
			return;
		}

		if (aGame.getContractorIndex() == 0)
		{
			aCurrentMode = Mode.WAIT_WIDOW;
		}
		else
		{
			aCurrentMode = Mode.WAIT_CLICK_EXCHANGE;
		}
	}

	/**
	 * Sets the bidding mode to auto-play, and obtains the rest of the bids.
	 */
	public static void setAutoplayBid()
	{
		aCurrentMode = Mode.AUTOPLAY;
		runAfterHumanBid();
	}

	/**
	 * One player receives the Widow.
	 */
	public static void addWidowToHand()
	{
		aCurrentMode = Mode.WAIT_DISCARD;
		aGame.addWidowToHand();

	}

	/**
	 * Sets the exchange part of the game to auto-mode, and exchanges the cards.
	 */
	public static void setAutoDiscard()
	{
		aCurrentMode = Mode.AUTOPLAY;
		exchangeWidow();
	}

	/**
	 * Exchanges cards between the contractor's hand and the Widow.
	 */
	public static void exchangeWidow()
	{
		aGame.exchangeCards();
		aCurrentMode = Mode.WAIT_CLICK;
	}

	/**
	 * Each player playing before the human selects a card to play.
	 */
	public static void runBeforeHumanTrick()
	{

		if (aGame.getTrickNbPlayed() < TRICK_NB)
		{
			aGame.playBeforeHuman();
			aCurrentMode = Mode.WAIT_PLAY;
		}
		else
		{
			aGame.computeScore();
			aCurrentMode = Mode.WAIT_NEXTROUND;
			aGame.isGameOver();
			if (aGame.hasGameEnded())
			{
				aCurrentMode = Mode.WAIT_NEW;
			}
		}
	}

	/**
	 * The Human player and players playing after him will select a card to play.
	 */
	public static void runAfterHumanTrick()
	{
		aGame.playAfterHuman();
		aCurrentMode = Mode.WAIT_CLICK;
	}

	/**
	 * Sets this trick to auto-mode, and all players remaining play a card.
	 */
	public static void autoplayTrick()
	{
		aCurrentMode = Mode.AUTOPLAY;
		runAfterHumanTrick();
	}

	/**
	 * Plays an entire non event-driven game (no human player).
	 */
	public static void nextGame()
	{
		if (aGame == null)
		{
			throw new GUIException("Initialize the GameEngine");
		}
		aGame.newGame();
		while (!aGame.isGameOver())
		{
			while (aGame.allPasses())
			{
				aGame.deal();
				aGame.bid();
			}

			aGame.exchange();

			for (int i = 0; i < TRICK_NB; i++)
			{
				aGame.playTrick();
			}
			aGame.computeScore();
		}
	}

	/**
	 * @return the GameEngine running the game
	 */
	public static GameEngine getEngine()
	{
		return aGame;
	}

	/**
	 * @return A string representing the output of the GameStatistics
	 */
	public static String getStatsOutput()
	{
		return aStats.getOutput();
	}

	/**
	 * Resets the GameStatistics.
	 */
	public static void resetStats()
	{
		aStats.reset();
		ScoringPanel.showStats();
	}

	/**
	 * @return the current state of the game
	 */
	public static Mode getMode()
	{
		return aCurrentMode;
	}

	/**
	 * Gets an IPlayer from the current game.
	 * 
	 * @param pIndex
	 *            The index of the IPlayer
	 * @return An IPlayer with index pIndex
	 */
	public static IPlayer getPlayer(int pIndex)
	{
		return aGame.getPlayer(pIndex);
	}

	/**
	 * Adds an IObserver to the GameEngine.
	 * 
	 * @param pObs
	 *            The IObserver
	 */
	public static void addObserver(IObserver pObs)
	{
		aGame.addObserver(pObs);
	}

	/**
	 * Removes an IObserver from the GameEngine.
	 * 
	 * @param pObs
	 *            The IObserver
	 */
	public static void removeObserver(IObserver pObs)
	{
		aGame.removeObserver(pObs);
	}

	/**
	 * @return Whether a Human is playing
	 */
	public static boolean isHumanMode()
	{
		return aDialog.isHumanMode();
	}

	/**
	 * Starts the GameEngine. Needs to be called for the class to function properly.
	 */
	public static void createEngine()
	{
		aDialog = new InputDialog();
		aGame = new GameEngine(null);
		createPlayers();

		GameLogging logger = new GameLogging(aGame);
		GameRunner.addObserver(logger);
		logger.logToFile(true);

		aStats = new GameStatistics(aGame);
		GameRunner.addObserver(aStats);
	}

	/**
	 * Asks the user for input as to how to create the IPlayers.
	 */
	public static void createPlayers()
	{
		aPlayers = new IPlayer[4];
		aDialog.generatePlayers();
		
		String[] pNames = aDialog.getNames();
		PlayerType[] pType = aDialog.getPlayerTypes();
		
		for (int i = 0; i<4; i++)
		{
			if (i==0 && aDialog.isHumanMode())
			{
				aPlayers[i] = new HumanPlayer(pNames[0], pType[0]);
			}
			else
			{
				switch (pType[i])
				{
					case Random:
						aPlayers[i] = new RandomRobot(pNames[i]);
						break;
					case Basic:
						aPlayers[i] = new BasicRobot(pNames[i]);
						break;
					case Advanced:
						aPlayers[i] = new AdvancedRobot(pNames[i]);
						break;
				default:
					throw new GUIException("Human mode initialized incorrectly");
				}
			}
		}
		
		aGame.changePlayers(aPlayers);
		
	}
}
