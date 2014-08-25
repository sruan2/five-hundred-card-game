package comp303.fivehundred.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.GameEngine;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.gui.GameRunner.Mode;
import comp303.fivehundred.gui.external.layout.TableLayout;

/**
 * 
 * @author Shanshan Ruan
 * Shows a graphic representation of a bidding state
 *
 */
@SuppressWarnings("serial")
public class BiddingScreen extends JPanel implements IObserver
{
	
	private static final double[][] SIZE = {{0.24, 0.52, 0.24},  // Columns
											{0.14, 0.11, 0.40, 0.10, 0.25}}; // Rows

	private static final int FONT_SIZE = 15;
	private BiddingPanel aBiddingPanel;
	private JLabel aInfoLabel;
	private JLabel[] aBidsPlaced;
	private JButton aExchangeButton;
	private WidowPanel aWidowPanel;

	/**
	 * Shows each player's bid, and includes a bidding panel for human players.
	 */
	public BiddingScreen()
	{
		GameRunner.addObserver(this);

	    setLayout(new TableLayout(SIZE));

		aBidsPlaced = new JLabel[4];
		for (int i = 0; i < 4; i++)
		{
			aBidsPlaced[i] = new JLabel();
			aBidsPlaced[i].setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE));
		}

		add(aBidsPlaced[0], "1,3,c,c");
		add(aBidsPlaced[1], "0,2,l,l");
		add(aBidsPlaced[2], "1,0,c,c");
		add(aBidsPlaced[3], "2,2,r,r");
		
		aWidowPanel = new WidowPanel();
		add(aWidowPanel, "1,2");

		aInfoLabel = new JLabel();
		aInfoLabel.setFont(new Font("sansserif", Font.ITALIC, FONT_SIZE));
		add(aInfoLabel, "1,1,l,l");

		aBiddingPanel = new BiddingPanel();
		add(aBiddingPanel, "0,4,2,4");
		
		aExchangeButton = new JButton("Card Exchange");
		aExchangeButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		aExchangeButton.setVisible(false);
		aExchangeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode() == Mode.WAIT_WIDOW)
				{
					GameRunner.addWidowToHand();
				}
				else if(GameRunner.getMode() == Mode.WAIT_CLICK_EXCHANGE)
				{
					GameRunner.addWidowToHand();
					GameRunner.exchangeWidow();
				}
					
				else
				{
					BoardPanel.displayErrorInfo("Not the time to do this");
				}
			}
		});
		add(aExchangeButton, "1,1,r,r");
	}

	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
		case DEALT:
			aInfoLabel.setText("  =====   It's your turn to bid now   =====");
			aExchangeButton.setVisible(false);
			aBiddingPanel.biddingEnabled(true);
			setBidMsgBusy();
			shownWidow(false);
			break;
		case BID:
			GameEngine game = GameRunner.getEngine();
			int bidderIndex = game.getLatestPlayerIndex();
			displayBidPlaced(bidderIndex, game.getBid(bidderIndex), game.getPlayer(bidderIndex).getName());

			aInfoLabel.setText("  =====   It's your turn to bid now   =====");
			if (game.getBidNbPlaced() == 4 && !game.allPasses())
			{
				aInfoLabel.setText("   "+game.getPlayer(game.getContractorIndex()).getName() + " wins the contract: " + game.getBestBid());
				aExchangeButton.setVisible(true);
				aBiddingPanel.biddingEnabled(false);
				if (game.getContractorIndex()==0)
				{
					shownWidow(true);
				}
			}
			break;
		default:
			break;
		}
	}

	// display every player's bidding information
	private void displayBidPlaced(int pIndex, Bid pBid, String pName)
	{
		aBidsPlaced[pIndex].setText(GameRunner.getEngine().getPlayer(pIndex).getName()+ " bids " + pBid);
	}

	private void setBidMsgBusy()
	{
		for (int i = 0; i < 4; i++)
		{
			aBidsPlaced[i].setText("Waiting......");
		}
	}
	
	private void shownWidow(boolean pShown)
	{
		remove(aWidowPanel);
		if (pShown)
		{
			aWidowPanel = new WidowPanel(GameRunner.getEngine().getWidow());
		}
		else
		{
			aWidowPanel = new WidowPanel();
		}
		aWidowPanel.setOpaque(false);
		add(aWidowPanel, "1,2");
	}

}
