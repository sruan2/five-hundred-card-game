package comp303.fivehundred.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

import comp303.fivehundred.gui.GameRunner.Mode;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Shanshan Ruan
 * Displays bid-related information
 */
@SuppressWarnings("serial")
public class BiddingPanel extends JPanel
{
	private static final int NB_OPTIONS = 5;
	private static final int TRICK_MIN = 6;
	private static final int FONT_SIZE = 15;
	private static final int SUIT_IMG_SIZE = 30;
	private static final int BUTTON_SIZE = 50;
	
	
	private static Suit aSuit;
	private static int aNum;
	private static boolean isPass;
	
	private static JRadioButtonMenuItem[] suitButtons;
	private static String[] suitNames = {"Spade", "Club", "Diamond", "Heart", "No"};
	
	private static JRadioButton[] trickButtons;
	
	private static JRadioButton passButton;
	private static JButton submitButton;
	private static JButton aAutoplayButton;
	
	/**
	 * Creates a panel for human players to select their bids.
	 */
	public BiddingPanel() 
	{
		
		setLayout(new GridBagLayout());
		
	    GridBagConstraints xGrid = new GridBagConstraints();
	    xGrid.gridy = 0;
	    xGrid.gridx = GridBagConstraints.RELATIVE;

	    GridBagConstraints yGrid = new GridBagConstraints();
	    yGrid.gridx = 0;
	    yGrid.gridy = GridBagConstraints.RELATIVE;
	    
	    suitButtons = new JRadioButtonMenuItem[NB_OPTIONS];
	    trickButtons = new JRadioButton[NB_OPTIONS];
	    
	    for (int i = 0; i<NB_OPTIONS; i++)
	    {
	    	suitButtons[i] = new JRadioButtonMenuItem(new ImageIcon(new ImageIcon(getClass().getResource("/images/"+suitNames[i]+".png")).getImage()
	    			.getScaledInstance(SUIT_IMG_SIZE, SUIT_IMG_SIZE, java.awt.Image.SCALE_SMOOTH)));
	    	suitButtons[i].setActionCommand(suitNames[i]);
	    	suitButtons[i].setSelected(true);
		    suitButtons[i].setBorderPainted(false);  
		    suitButtons[i].setFocusPainted(false);  
		    suitButtons[i].setContentAreaFilled(false); 
		    suitButtons[i].setSize(BUTTON_SIZE, BUTTON_SIZE);
		    
	    }
 
	    for (int i = 0; i<NB_OPTIONS; i++)
	    {
	    	final int lSuitIndex = i;
	    	suitButtons[i].addActionListener(new ActionListener()
	 		{
	 			public void actionPerformed(ActionEvent pEvent)
	 			{
	 				if (lSuitIndex==4)
	 				{
	 					aSuit = null;
	 				}
	 				else
	 				{
	 					aSuit = Suit.values()[lSuitIndex];
	 				}
	 			}
	 		});
	    
	    }

	    //Group the radio buttons for suits.
	    ButtonGroup suitGroup = new ButtonGroup();
	    for (int i = 0; i<NB_OPTIONS ; i++)
	    { 
	    	suitGroup.add(suitButtons[i]);
	    }
	    
	    for (int i = 0; i<NB_OPTIONS ; i++)
	    { 
	    	final int lTrickValue = i+TRICK_MIN;
	    	trickButtons[i] = new JRadioButton("" + lTrickValue);
	    	trickButtons[i].setActionCommand("" + lTrickValue);
	    	trickButtons[i].setSelected(true);
	    	trickButtons[i].setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		    aNum = lTrickValue;
		    trickButtons[i].addActionListener(new ActionListener()
		 		{
		 			public void actionPerformed(ActionEvent pEvent)
		 			{
		 				for (JRadioButtonMenuItem each : suitButtons)
		 				{
		 					each.setEnabled(true);
		 				}
		 				aNum = lTrickValue;
		 				isPass = false;
		 			}
		 		});
	    }
	    
	    // Set 6 and Spades to be selected
	    // because we can't set pass to be selected at the beginning
	    trickButtons[0].setSelected(true);
	    suitButtons[0].setSelected(true);
	    
	    String passString = "PASS";
	    passButton = new JRadioButton(passString);
	    passButton.setActionCommand(passString);
	    passButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
	    passButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
 				for (JRadioButtonMenuItem each : suitButtons)
 				{
 					each.setEnabled(false);
 				}
				isPass = true;
			}
		});

	    //Group the radio buttons for numbers and pass
	    ButtonGroup numGroup = new ButtonGroup();
	    for (int i = 0; i<NB_OPTIONS ; i++)
	    { 
	    	numGroup.add(trickButtons[i]);
	    }
	    numGroup.add(passButton);

	    submitButton = new JButton("Submit");
	    submitButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
	    submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode()==Mode.WAIT_BID)
				{
					if (analyzeBid().isPass() || GameRunner.getEngine().getBidNbPlaced() == 0 || 
							analyzeBid().compareTo(GameRunner.getEngine().getBestBid()) > 0)
					{
						GameRunner.runAfterHumanBid();
					}
					else
					{
						BoardPanel.displayErrorInfo("Invalid Bid!");
					}
				}
				else
				{
					BoardPanel.displayErrorInfo("YOU FOOL! It's no time to bid!");
				}
			}
		});
	    
	    aAutoplayButton = new JButton("Auto-Bid");
	    aAutoplayButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
	    aAutoplayButton.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent pEvent)
	    	{
	    		if (GameRunner.getMode()==Mode.WAIT_BID)
	    		{
	    			GameRunner.setAutoplayBid();
	    		}
	    		else
	    		{
	    			BoardPanel.displayErrorInfo("YOU FOOL! It's no time to bid!");
	    		}
	    	}
	    });
	    
	    JPanel suitPanel = new JPanel();
	    for (int i = 0; i<NB_OPTIONS ; i++)
	    { 
	    	suitPanel.add(suitButtons[i], xGrid);
	    }
	    add(suitPanel, yGrid);
	    
	    
	    JPanel numPanel = new JPanel();
	    for (int i = 0; i<NB_OPTIONS ; i++)
	    { 
	    	numPanel.add(trickButtons[i], xGrid);
	    }
	    numPanel.add(passButton, xGrid);
	    numPanel.add(submitButton);
	    numPanel.add(aAutoplayButton);
	    add(numPanel, yGrid);
	    
	    suitButtons[0].doClick();
	    trickButtons[0].doClick();
	    
	    biddingEnabled(true);
	}
	
	/**
	 * Turns the bidding panel on/off.
	 * @param pIsEnabled
	 * 		boolean representing if we want the panel active
	 */
	public void biddingEnabled(Boolean pIsEnabled)
	{		
		resetTo6Spade();
		
		for (int i = 0; i<NB_OPTIONS; i++)
	    { 
	    	suitButtons[i].setEnabled(pIsEnabled);
	    	trickButtons[i].setEnabled(pIsEnabled);
	    }
		passButton.setEnabled(pIsEnabled);
		submitButton.setEnabled(pIsEnabled);
		aAutoplayButton.setEnabled(pIsEnabled);
	}

	/**
	 * Sends the requested Bid.
	 * @return
	 * 		The Bid specified by the JButtons
	 */
	public static Bid analyzeBid()
	{
		if (!isPass)
		{
			return new Bid(aNum, aSuit);
		}
		return new Bid();
	}
	
	private void resetTo6Spade()
	{
		trickButtons[0].setSelected(true);
		suitButtons[0].setSelected(true);
		aNum = TRICK_MIN;
		aSuit = Suit.SPADES;
		isPass = false;
	}
}
