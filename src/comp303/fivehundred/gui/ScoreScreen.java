package comp303.fivehundred.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.GameRunner.Mode;
import comp303.fivehundred.gui.external.layout.TableLayout;

/**
 * 
 * @author Sherry Ruan
 *
 */
@SuppressWarnings("serial")
public class ScoreScreen extends JPanel implements IObserver
{
	
	private static final double[][] SIZE = {{0.2, 0.6, 0.2},  // Columns
											{0.2, 0.4, 0.2, 0.2}}; // Rows
	private static final int FONT_SIZE = 17;
	private static final int BUTTON_FONT_SIZE = 15;
	private JTextArea aRoundFinishedLabel;
	private JTextArea aGameFinishedLabel;
	private JButton aNextRoundButton;
	

	/**
	 * Score Screen shown after a round of play.
	 */
	public ScoreScreen()
	{
		GameRunner.addObserver(this);

	    setLayout(new TableLayout(SIZE));

		aRoundFinishedLabel = new JTextArea();
		aGameFinishedLabel = new JTextArea();
		
		aRoundFinishedLabel.setEditable(false);
		aGameFinishedLabel.setEditable(false);
		
		
		aNextRoundButton = new JButton("Go to the next round!");
		aNextRoundButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode() == Mode.WAIT_NEXTROUND)
				{
					GameRunner.runBeforeHumanBid();
				}		
				else
				{
					BoardPanel.displayErrorInfo("Not the time to start the next round!");
				}
			}
		});
		
		add(aRoundFinishedLabel, "1,1");
		add(aGameFinishedLabel, "1,1");
		add(aNextRoundButton, "1,2,c,c");
		
		aRoundFinishedLabel.setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE));
		aGameFinishedLabel.setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE));
		aNextRoundButton.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE ));
	}

	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
		case ENDROUND:
			aRoundFinishedLabel.setVisible(false);
			aGameFinishedLabel.setVisible(false);
			aNextRoundButton.setVisible(false);
			if(!GameRunner.getEngine().hasGameEnded()) // move to next round
			{
				int contractorTeam = GameRunner.getEngine().getContractorIndex()%2+1;
				String contractorTeamName = null;
				if(contractorTeam == 1)
				{
					contractorTeamName = "Your team";
				}
				else
				{
					contractorTeamName = "The opponents' team";
				}
				
				int tricksWon = GameRunner.getEngine().getTeamTricks(contractorTeam-1);
				int tricksBid = GameRunner.getEngine().getBestBid().getTricksBid();
				
				String winOrLose = null;
				if (tricksWon >= tricksBid)
				{
					winOrLose = "wins";
				}
				else 
				{
					winOrLose = "loses";
				}
				
				int yourScore = GameRunner.getEngine().getScore(0);
				int opponentScore = GameRunner.getEngine().getScore(1);

				aRoundFinishedLabel.setText("==============    Information about this round    ============== \n\n" + 
				contractorTeamName + " bids " + tricksBid +" tricks and wins " + tricksWon +" tricks.\n" + 
				contractorTeamName +" "+ winOrLose + " this round. \n" + 
				"Your team's score is "+ yourScore +" now.\n" +
				"The opponents' score is "+opponentScore +" now.\n");
				
				
				aRoundFinishedLabel.setVisible(true);
				aNextRoundButton.setVisible(true);
			}
			else // move to next game
			{
				int yourScore = GameRunner.getEngine().getScore(0);
				int opponentScore = GameRunner.getEngine().getScore(1);
				
				if (yourScore > opponentScore ||(yourScore == opponentScore && GameRunner.getEngine().getContractorIndex()%2==0))
				{
					String winnerMessage;
					if(GameRunner.isHumanMode())
					{
						winnerMessage = "Congratulations, Your team has just won a game!";
					}
					else
					{
						winnerMessage = "Team 1 won the game.";
					}
					aGameFinishedLabel.setText("====== "+winnerMessage+" ======\n\n"+
							"Press CTRL+R to start a new game!\n"+
							"Press CTRL+W to customize robots!\n"+
							"Press CTRL+E to reset statistics!\n");
				}
				else
				{
					String loserMessage;
					if(GameRunner.isHumanMode())
					{
						loserMessage = "Oh no! Your team has just lost a game! ======\n\nDon't be depressed! Let's try again!\n";
					}
					else
					{
						loserMessage = "Team 2 won the game. ======\n\n";
					}
					aGameFinishedLabel.setText("====== "+loserMessage+
							"Press CTRL+R to start a new game!\n"+
							"Press CTRL+W to customize robots!\n"+
							"Press CTRL+E to reset statistics!\n");
				}
				
			
				aGameFinishedLabel.setVisible(true);
			}
			break;
		default:
			break;
		}
	}

}

