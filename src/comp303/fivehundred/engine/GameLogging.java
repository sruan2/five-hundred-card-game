package comp303.fivehundred.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comp303.fivehundred.ai.IPlayer;
import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.model.Hand;

/**
 * @author (Sherry) Shanshan Ruan 260471837
 */
public class GameLogging implements IObserver
{

	private static final int TRICK_SIZE = 4;
	private static final int WIN_PTS = 500;
	private final Logger aLogger = LoggerFactory.getLogger(GameLogging.class);
	
	private GameEngine aGame;
	private File aFile;
	private FileOutputStream aFileout;
	private PrintStream aLogStream;
	private PrintStream aOriginalStream;
	private boolean aIsLogToFile;
	private boolean aNewDeal;

	/**
	 * @param pGame
	 *            The game which is being logged
	 */
	public GameLogging(GameEngine pGame)
	{
		aGame = pGame;
		System.setProperty("org.slf4j.simplelogger.defaultlog", "info");
		aFile = new File("transcript.log");
		aFileout = null;
		try
		{
			aFileout = new FileOutputStream(aFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		aLogStream = new PrintStream(aFileout);
		aOriginalStream = new PrintStream(System.out);
		
	}
	
	/**
	 * Sets whether we want to log to a file.
	 * @param pEnable
	 * 		Whether we log to a file or not
	 */
	public void logToFile(boolean pEnable)
	{
		if (pEnable)
		{
			System.setOut(aLogStream);
		}
		else
		{
			System.setOut(aOriginalStream);
		}
		aIsLogToFile = pEnable;
	}

	@Override
	public void update(Message pArg)
	{
		switch (pArg)
		{
			case NEWGAME:
				initialLogging();
				break;
			case DEALT:
				handLogging();
				break;
			case BID:
				bidLogging();
				break;
			case PASS:
				passLogging();
				break;
			case EXCHANGE:
				exchangeLogging();
				break;
			case TRICK:
				trickLogging();
				break;
			case SCORE:
				endLogging();
				break;
			case GAMEOVER:
				gameOverLogging();
				break;
		default:
			break;
		}
	}

	private void initialLogging()
	{
		aNewDeal = true;
		logMsg("Game initialized.");
		logMsg("============================== NEW GAME ==============================");
	}
	
	private void handLogging()
	{
		if (aNewDeal)
		{
			String dealerName = aGame.getPlayer(aGame.getDealerIndex()).getName();
			aNewDeal = false;
			logMsg("**************** NEW DEAL ****************");
			logMsg("Players are dealt cards. "+dealerName+" will deal");
			logMsg("The widow contains: "+aGame.getWidow());
		}
		int player = aGame.getLatestPlayerIndex();
		Hand hand = aGame.getHand(player);
		logMsg(""+aGame.getPlayer(player).getName()+"'s cards: "+hand);
	}

	private void bidLogging()
	{
		int player = aGame.getLatestPlayerIndex();
		logMsg(""+aGame.getPlayer(player).getName()+" bids "+aGame.getBid(player));
	}
	
	private void passLogging()
	{
		aNewDeal = true;
		logMsg("All players have passed. New cards are being dealt.");
	}

	private void exchangeLogging()
	{
		int contractor = aGame.getContractorIndex();
		String contractorName = aGame.getPlayer(contractor).getName();
		logMsg(""+contractorName+" picks up the Widow with contract "+aGame.getBestBid());
		logMsg(""+contractorName+" discards "+aGame.getWidow());
		logMsg(""+contractorName+"'s cards: "+aGame.getHand(contractor));
	}

	private void trickLogging()
	{
		if (aGame.getTrick().size()==1)
		{
			int trickNumber = aGame.getTeamTricks(0) + aGame.getTeamTricks(1) + 1;
			logMsg("----------- TRICK "+trickNumber+" ------------");
		}
		int player = aGame.getLatestPlayerIndex();
		
		logMsg(aGame.getPlayer(player).getName() + "'s cards: " + aGame.getHand(player) + " plays " + aGame.getLatestCard());
		
		if (aGame.getTrick().size()==TRICK_SIZE)
		{
			logMsg(aGame.getPlayer(aGame.getTrickLeaderIndex()).getName() + " wins the trick");
		}
	}

	private void endLogging()
	{
		aNewDeal = true;
		
		IPlayer p0 = aGame.getPlayer(0);
		IPlayer p1 = aGame.getPlayer(1);
		IPlayer p2 = aGame.getPlayer(2);
		IPlayer p3 = aGame.getPlayer(3);
		
		logMsg(p0.getName() + " and " + p2.getName() + " won " + aGame.getTeamTricks(0) + " tricks");
		logMsg(p1.getName() + " and " + p3.getName() + " won " + aGame.getTeamTricks(1) + " tricks");
		
		int leadingTeam = aGame.getContractorIndex() % 2;
		int nonLeadingTeam = (leadingTeam + 1)%2;
		String result;
		
		if (aGame.getTeamTricks(aGame.getContractorIndex() % 2) >= aGame.getBestBid().getTricksBid())
		{
			result = "makes";
		}
		else
		{
			result = "fails";
		}
		
		logMsg(aGame.getPlayer(aGame.getContractorIndex()).getName() + "'s team" + " "+result+" their contract!!");

		
		logMsg("Contractor round score: "+aGame.getRoundScore(leadingTeam));
		logMsg("Defenders round score: "+aGame.getRoundScore(nonLeadingTeam)); 
		
		logMsg(p0.getName() + " and " + p2.getName() + "'s total score: "+aGame.getScore(0));
		logMsg(p1.getName() + " and " + p3.getName() + "'s total score: "+aGame.getScore(1));
	}
	

	private void gameOverLogging()
	{
		if (aGame.getScore(0) >= WIN_PTS || aGame.getScore(1) <= -WIN_PTS)
		{
			logMsg(aGame.getPlayer(0).getName() + " and " + aGame.getPlayer(2).getName() + " win!");
		}
		else
		{
			logMsg(aGame.getPlayer(1).getName() + " and " + aGame.getPlayer(3).getName() + " win!");
		}	
	}
	
	private void logMsg(String pMsg)
	{
		if (aIsLogToFile)
		{
			System.out.println(pMsg);
		}
		aLogger.info(pMsg);
	}

}
