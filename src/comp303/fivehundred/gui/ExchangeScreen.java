package comp303.fivehundred.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.external.layout.TableLayout;
import comp303.fivehundred.util.Card.Suit;

/**
 * 
 * @author Sherry Ruan
 * This class manages the center panel of the GUI during the phase of the discarding cards.
 */
@SuppressWarnings("serial")
public class ExchangeScreen extends JPanel implements IObserver
{
	private static final int TRICK_IMG_SIZE = 40;
	private static final int SUIT_IMG_SIZE = 60;
	private static final double[][] SIZE = {{0.1, 0.1, 0.6, 0.2},  // Columns
											{0.22, 0.58, 0.2}}; // Rows
	private static String[] suitNames = {"Spade", "Club", "Diamond", "Heart", "No"};
	private static JButton aAutoExchangeButton;
	private static final int FONT_SIZE = 15;
	
	private JTextArea aExchangeLabel;
	private JLabel aTrumpLabel;
	private JLabel aNumLabel;
	private ImageIcon aImg;
	
	/**
	 * Constructs the screen in which players discard cards (only used for Human players).
	 */
	public ExchangeScreen()
	{
		GameRunner.addObserver(this);
		
		setLayout(new TableLayout(SIZE));
		 
		aExchangeLabel = new JTextArea();
		aExchangeLabel.setText("            The widow has been added to your hand \n \n===== Please select six of your cards to discard =====");
		aExchangeLabel.setEditable(false);
		
		aAutoExchangeButton = new JButton("Auto-Discard");
		add(aExchangeLabel, "2,1,c,c");
		add(aAutoExchangeButton, "2,2,c,c");
		
		aTrumpLabel = new JLabel();
		aNumLabel = new JLabel();
		
		add(aTrumpLabel, "0,0,c,c");
		add(aNumLabel, "1,0,l,l");
		
		aAutoExchangeButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		aExchangeLabel.setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE+2));
		
		
		aAutoExchangeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pEvent)
			{
				GameRunner.setAutoDiscard();
			}
		});
	}

	/**
	 * Disables the auto-discard button.
	 * @param pEnable
	 * 		Whether we should enable the button or not
	 */
	public static void enableAutoDiscard(boolean pEnable)
	{
		aAutoExchangeButton.setEnabled(pEnable);
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

	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
		case WIDOW:
			String trumpName = trumpToString(GameRunner.getEngine().getBestBid().getSuit());
			aImg = new ImageIcon(new ImageIcon(getClass().getResource("/images/" + trumpName + ".png")).getImage()
					.getScaledInstance(SUIT_IMG_SIZE, SUIT_IMG_SIZE, java.awt.Image.SCALE_SMOOTH));
			aTrumpLabel.setIcon(aImg);
			int bidTrick = GameRunner.getEngine().getBestBid().getTricksBid();
			aNumLabel.setText(" " + Integer.toString(bidTrick));
			aNumLabel.setFont(new Font("Arial", Font.BOLD, TRICK_IMG_SIZE));
			break;
		case ENDROUND:
			enableAutoDiscard(true);
			break;
		default:
			break;
		}
	}
}
