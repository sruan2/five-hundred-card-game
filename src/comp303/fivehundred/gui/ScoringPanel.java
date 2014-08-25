package comp303.fivehundred.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.external.layout.TableLayout;

/**
 * @author Ioannis Fytilis 260482744
 * Shows statistics and scores about the game.
 */
@SuppressWarnings("serial")
public class ScoringPanel extends JPanel implements IObserver
{
	
	private static final int NB_PLAYERS = 4;
	private static final int TEXT_BOX_SIZE = 11;
	private static final double[][] SIZE = {{0.99, 0.01},  // Columns
        									{0.755, 0.10, 0.105, 0.04}}; // Rows
	
	private static JTextArea aStatsTxt;
	private JTextArea aScoreTxt;
	private JTextArea aTricksTxt;
	private JButton aShowStats;

	/**
	 * Constructs the Score panel.
	 */
	public ScoringPanel()
	{	
		GameRunner.addObserver(this);

	    setLayout(new TableLayout(SIZE));
		
		aStatsTxt = new JTextArea(GameRunner.getStatsOutput());
		aStatsTxt.setEditable(false);
		aStatsTxt.setFont(new Font("sansserif", Font.BOLD, TEXT_BOX_SIZE));
		add(aStatsTxt, "0,0");
		aStatsTxt.setVisible(false);
		
		aScoreTxt = new JTextArea(getScoresString());
		aScoreTxt.setEditable(false);
		aScoreTxt.setFont(new Font("sansserif", Font.BOLD, TEXT_BOX_SIZE));
		add(aScoreTxt, "0,1");
		
		aTricksTxt = new JTextArea(getTricksString());
		aTricksTxt.setEditable(false);
		aTricksTxt.setFont(new Font("sansserif", Font.BOLD, TEXT_BOX_SIZE));
		add(aTricksTxt, "0,2");
		
		aShowStats = new JButton("Show Statistics");
		add(aShowStats, "0,3");
		aShowStats.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if(!aStatsTxt.isVisible())
				{
					aStatsTxt.setVisible(true);
					aShowStats.setText("Hide Statistics");
				}
				else
				{
					aStatsTxt.setVisible(false);
					aShowStats.setText("Show Statistics");
					
				}
			}
		});
		
	}	
	
	private String getScoresString()
	{
		String output = "";
		output += "Your Score :  \n       " + GameRunner.getEngine().getScore(0) + "\n";
		output += "Opponents' Score :  \n       " + GameRunner.getEngine().getScore(1) + "\n";
		
		return output;
	}
	
	private String getTricksString()
	{
		String output = "";
		output += "Your Tricks :  \n       " + GameRunner.getEngine().getTeamTricks(0) + "\n";
		output += "Opponents' Tricks :  \n       " + GameRunner.getEngine().getTeamTricks(1) + "\n";
		
		return output;
	}
	

	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
		case DEALT:
			aScoreTxt.setText(getScoresString());
			aTricksTxt.setText(getTricksString());
			break;
		case SCORE:
			aScoreTxt.setText(getScoresString());
			break;
		case TRICK:
			if (GameRunner.getEngine().getTrick().size() == NB_PLAYERS)
			{
				aTricksTxt.setText(getTricksString());
			}
			break;
		case GAMEOVER:
			showStats();
			break;
		default:
			break;
		}
	}
	
	/**
	 * Updates the changes in statistics.
	 */
	public static void showStats()
	{
		aStatsTxt.setText(GameRunner.getStatsOutput());
	}
}
