package comp303.fivehundred.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.GameRunner.Mode;
import comp303.fivehundred.gui.external.layout.TableLayout;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardImages;

/**
 * @author Gerald Lang 260402748
 * This class manages the center panel of the GUI during the phase of the game where tricks are being played.
 */
@SuppressWarnings("serial")
public class TrickScreen extends JPanel implements IObserver
{
	private static final int TRICK_SIZE = 4;
	private static final int TOTAL_NUM_TRICKS = 10;
	private static final int TRICK_IMG_SIZE = 40;
	private static final int SUIT_IMG_SIZE = 60;
	private static final int FONT_SIZE = 15;
	private static String[] suitNames = {"Spade", "Club", "Diamond", "Heart", "No"};
	private static final double[][] SIZE = {{0.10, 0.80, 0.10},  // Columns
											{0.3, 0.42, 0.28}}; // Rows
	private GridBagConstraints aYGrid;
	
	private JLabel aTrumpLabel;
	private JLabel aNumLabel;
	private JButton aNextTrick;
	private ImageIcon aImg;
	private JButton aAutoplayTrickButton;
	private JPanel aDiscardedCardPanel;
	private JTextArea aInfo;
	/**
	 * Construct a TrickScreen.
	 */
	public TrickScreen()
	{
		GameRunner.addObserver(this);

	    setLayout(new TableLayout(SIZE));
	    
	    
	    aTrumpLabel = new JLabel();
	    aNumLabel = new JLabel();
	    
	    add(aTrumpLabel, "0,0,c,c");
		add(aNumLabel, "1,0,l,l");
		
		aDiscardedCardPanel = new JPanel(new GridBagLayout());
		aDiscardedCardPanel.setOpaque(false);
		
		aInfo = new JTextArea("The discarded cards are \n");
		aInfo.setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE));
		aInfo.setOpaque(false);
		aInfo.setEditable(false);
	    
		aAutoplayTrickButton = new JButton("Autoplay this trick");
		aAutoplayTrickButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		aAutoplayTrickButton.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode() == Mode.WAIT_PLAY)
				{
					GameRunner.autoplayTrick();
				}
				else
				{
					BoardPanel.displayErrorInfo("You should not autoplay right now");
				}
			}
		});
		
		aNextTrick = new JButton("Click me to play a new trick!");
		aNextTrick.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		add(aNextTrick, "1,1,c,c");
		aNextTrick.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode() == Mode.WAIT_CLICK)
				{
					removeAll();
					
					aTrumpLabel.setIcon(aImg);
					int bidTrick = GameRunner.getEngine().getBestBid().getTricksBid();
					aNumLabel.setText(" "+Integer.toString(bidTrick));
					aNumLabel.setFont(new Font("Arial", Font.BOLD, TRICK_IMG_SIZE));
					add(aTrumpLabel, "0,0,c,c");
					add(aNumLabel, "1,0,l,l");
					
					
					GameRunner.runBeforeHumanTrick();
					if (GameRunner.getMode() == Mode.WAIT_PLAY)
					{
						add(aAutoplayTrickButton, "1,1,c,c");
					}
					repaint();
					validate();
				}
				else
				{
					BoardPanel.displayErrorInfo("Not the time to do this");
				}
			}
		});
	}

	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
		case EXCHANGE:
			aYGrid = new GridBagConstraints();
			aYGrid.gridx = 0;
			aYGrid.gridy = GridBagConstraints.RELATIVE;
			aDiscardedCardPanel.removeAll();
			aInfo.setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE));
			aDiscardedCardPanel.add(aInfo, aYGrid);
			aDiscardedCardPanel.add(new WidowPanel(GameRunner.getEngine().getWidow()), aYGrid);
			aDiscardedCardPanel.add(new JTextArea("\n\n\n\n\n"), aYGrid);
			aDiscardedCardPanel.add(aNextTrick, aYGrid);
		
			add(aDiscardedCardPanel, "1,0,1,1");
			
			aNextTrick.setText("Click me to play a new trick!");
			add(aNextTrick, "1,1,c,c");
			aNextTrick.setOpaque(false);

			String trumpName = trumpToString(GameRunner.getEngine().getBestBid().getSuit());
			aImg = new ImageIcon(new ImageIcon(getClass().getResource("/images/"+trumpName+".png")).getImage()
	    			.getScaledInstance(SUIT_IMG_SIZE, SUIT_IMG_SIZE, java.awt.Image.SCALE_SMOOTH));
			aTrumpLabel.setIcon(aImg);
			int bidTrick = GameRunner.getEngine().getBestBid().getTricksBid();
			aNumLabel.setText(" "+Integer.toString(bidTrick));
			aNumLabel.setFont(new Font("Arial", Font.BOLD, TRICK_IMG_SIZE));
			add(aTrumpLabel, "0,0,c,c");
			add(aNumLabel, "1,0,l,l");
			break;
		case TRICK:
			int lastPlayersIndex = GameRunner.getEngine().getLatestPlayerIndex();
			Card cardPlayed = GameRunner.getEngine().getLatestCard();
			switch(lastPlayersIndex)
			{
				case 0:
					add(new JLabel(CardImages.getCard(cardPlayed)), "1,2,c,c");
					break;
				case 1:
					add(new JLabel(CardImages.getCard(cardPlayed)), "0,1,c,c");
					break;
				case 2:
					add(new JLabel(CardImages.getCard(cardPlayed)), "1,0,c,c");
					break;
				case 3:
					add(new JLabel(CardImages.getCard(cardPlayed)), "2,1,c,c");
					break;
				default:
					break;
			}
			remove(aAutoplayTrickButton);
			if (GameRunner.getEngine().getTrick().size()==TRICK_SIZE)
			{
				aNextTrick.setText("Next Trick");
				add(aNextTrick, "1,1,c,c");
			}
			if (GameRunner.getEngine().getTrickNbPlayed()== TOTAL_NUM_TRICKS)
			{
				aNextTrick.setText("Click me to see stats!");
			}
			repaint();
			validate();
			break;
		case SCORE:
			remove(aTrumpLabel);
			remove(aNumLabel);
			remove(aAutoplayTrickButton);
			break;
		case ENDROUND:
			removeAll();
			break;
		default:
			break;
		}
	}
	
	private String trumpToString(Suit pTrump)
	{
		if (pTrump==null)
		{
			return suitNames[4];
		}
		else
		{
			int suitOrdinal = pTrump.ordinal();
			return suitNames[suitOrdinal];
		}
	}
	
}
