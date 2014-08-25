package comp303.fivehundred.engine;
import java.text.DecimalFormat;

import comp303.fivehundred.engine.GameEngine.Message;

/**
 * @author Gerald Lang 260402748
 * Provides statistics for games played.
 */
public class GameStatistics implements IObserver
{
	private static final int NB_PLAYER = 4;
	private static final double ROUND_TOTAL = 10;
	private static final int PERCENTAGE = 100;
	private static final int WIN_POINTS = 500;
	
	private double[] aPlayerTricks;
	private double[] aPlayerContractsWon;
	private double[] aPlayerContractsMade;
	private double[] aPlayerGamesWon;
	private double[] aPlayerAccumulatedScore;
	private double aGamesPlayed;
	private double aRoundsPlayed;
	
	private double[] aPercentageTricks;
	private double[] aPercentageContractsWon;
	private double[] aPercentageContractsMade;
	private double[] aPercentageGamesWon;
	private double[] aScoreIndex;
	
	private String aOutput;
	private boolean aIsRecent;
	
	private GameEngine aGame;
	
	/**
	 * Creates a GameStatistics which will wait for updates from the Observable.
	 * @param pGame
	 * 		The Observable
	 */
	public GameStatistics(GameEngine pGame)
	{
		aGame = pGame;
		reset();
	}
	
	/**
	 * After being notified by the Observable, perform updates.
	 * @param pMessage
	 * 		The notification from the Observable
	 */
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
			case EXCHANGE:
				updateContractsWon();
				break;
			case TRICK:
				updateTricks();
				break;
			case SCORE:
				updateContractsMade();
				break;
			case GAMEOVER:
				updateGamesWon();
				updateAccumulatedScores();
				updateStatistics();
				aIsRecent = false;
				break;
		default:
			break;
		}
	}
	
	private void updateTricks()
	{
		if (aGame.getTrick().size()==NB_PLAYER)
		{
			aPlayerTricks[aGame.getTrickLeaderIndex()]++;
		}
	}
	
	private void updateContractsWon()
	{
		aPlayerContractsWon[aGame.getContractorIndex()]++;
	}
	
	private void updateContractsMade()
	{
		int contractTeamIndex = aGame.getContractorIndex() % 2;
		int tricksBid = aGame.getBestBid().getTricksBid();
		if( aGame.getTeamTricks(contractTeamIndex) >= tricksBid )
		{
			aPlayerContractsMade[aGame.getContractorIndex()]++;
		}
		aRoundsPlayed++;
	}
	
	private void updateGamesWon()
	{
		aGamesPlayed++;
		
		if(aGame.getScore(0) >= WIN_POINTS || aGame.getScore(1) <= -WIN_POINTS )
		{
			aPlayerGamesWon[0]++;
			aPlayerGamesWon[2]++;
		}
		else
		{
			aPlayerGamesWon[1]++;
			aPlayerGamesWon[3]++;
		}
	}
	
	private void updateAccumulatedScores()
	{
		double team1Score = aGame.getScore(0);
		double team2Score = aGame.getScore(1);
		
		aPlayerAccumulatedScore[0] += team1Score;
		aPlayerAccumulatedScore[1] += team2Score;
		aPlayerAccumulatedScore[2] += team1Score;
		aPlayerAccumulatedScore[3] += team2Score;
	}
	
	private void updateStatistics()
	{
		for( int i = 0; i<NB_PLAYER; i++)
		{
			aPercentageTricks[i] = roundTwoDecimals( ( aPlayerTricks[i] / ( aRoundsPlayed * ROUND_TOTAL ) ) * PERCENTAGE );
			aPercentageContractsWon[i] = roundTwoDecimals( ( aPlayerContractsWon[i] / aRoundsPlayed ) * PERCENTAGE );
			if (aPlayerContractsMade[i]==0 && aPlayerContractsWon[i] ==0)
			{
				aPercentageContractsMade[i] = 0;
			}
			else
			{
				aPercentageContractsMade[i] = roundTwoDecimals( ( aPlayerContractsMade[i] / aPlayerContractsWon[i] ) * PERCENTAGE );
			}
			aPercentageGamesWon[i] = roundTwoDecimals( ( aPlayerGamesWon[i] / aGamesPlayed ) * PERCENTAGE );
			aScoreIndex[i] = roundTwoDecimals( aPlayerAccumulatedScore[i] / ( aGamesPlayed * WIN_POINTS ) );
		}
	}
	
	/**
	 * Outputs accumulated statistics.
	 */
	private void generateOutput()
	{
		aOutput = "";
		aOutput += "===STATISTICS===\n\n";
		
		aOutput += "Games played: " + (int)aGamesPlayed + "\n\n";	
		
		aOutput += "---TRICKS WON---\n";
		for(int i = 0; i<NB_PLAYER; i++)
		{
			aOutput +=  aGame.getPlayer(i).getName() + " : " + aPercentageTricks[i] + "%\n";
		}
		
		aOutput += "\n---CONTRACTS MADE---\n";
		for(int i = 0; i<NB_PLAYER; i++)
		{
			aOutput +=  aGame.getPlayer(i).getName() + " : " + aPercentageContractsMade[i] + "%\n";
		}
		
		aOutput += "\n---CONTRACTS WON---\n";
		for(int i = 0; i<NB_PLAYER; i++)
		{
			aOutput +=  aGame.getPlayer(i).getName() + " : " + aPercentageContractsWon[i] + "%\n";
		}
		
		aOutput += "\n---GAMES WON---\n";
		for(int i = 0; i<NB_PLAYER; i++)
		{
			aOutput +=  aGame.getPlayer(i).getName() + " : " + aPercentageGamesWon[i] + "%\n";
		}
		
		aOutput += "\n---SCORE INDEXES---\n";
		for(int i = 0; i<NB_PLAYER; i++)
		{
			aOutput +=  aGame.getPlayer(i).getName() + " : " + aScoreIndex[i] + "\n";
		}
	}
	
	/**
	 * Get the statistics output.
	 * @return
	 * 		A string representation of the output
	 */
	public String getOutput()
	{
		if (!aIsRecent)
		{
			generateOutput();
			aIsRecent = true;
		}
		return aOutput;
	}
	
	/**
	 * Print the output.
	 */
	public void printStatistics()
	{
		if (!aIsRecent)
		{
			generateOutput();
			aIsRecent = true;
		}
		System.out.println(aOutput);
	}

	/**
	 * Reset all statistics.
	 */
	public void reset()
	{
		aOutput = "";

		aPlayerTricks = new double[NB_PLAYER];
		aPlayerContractsWon = new double[NB_PLAYER];
		aPlayerContractsMade = new double[NB_PLAYER];
		aPlayerGamesWon = new double[NB_PLAYER];
		aPlayerAccumulatedScore = new double[NB_PLAYER];
		aGamesPlayed = 0;
		aRoundsPlayed = 0;

		aPercentageTricks = new double[NB_PLAYER];
		aPercentageContractsWon = new double[NB_PLAYER];
		aPercentageContractsMade = new double[NB_PLAYER];
		aPercentageGamesWon = new double[NB_PLAYER];
		aScoreIndex = new double[NB_PLAYER];
		
		generateOutput();
	}

	// Helper method to round doubles to 2 decimal places
	private double roundTwoDecimals(double pValue) 
	{
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimalForm.format(pValue));
	}
	
}
