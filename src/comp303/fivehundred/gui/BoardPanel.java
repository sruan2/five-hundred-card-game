package comp303.fivehundred.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.GameRunner.Mode;
import comp303.fivehundred.gui.external.CardPanel;
import comp303.fivehundred.gui.external.RobotCards;
import comp303.fivehundred.gui.external.layout.TableLayout;

/**
 * @author Ioannis Fytilis 260482744 
 * Shows a graphic representation of the GameEngine states.
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel implements IObserver
{
	
	private static final int CARD_SPACING = 30;
	private static final Color COLOR = new Color(143, 183, 183);
	private static final double[][] SIZE = {{0.10, 0.80, 0.10},  // Columns
											{0.20, 0.56, 0.20, 0.04}}; // Rows
	
	private static JLabel aErrorInfo;
	private static int aErrorCounter;
	private static final int FONT_SIZE = 15;
	private static final int WELCOME_SIZE = 20;
	
	private GridBagConstraints aYGrid;
	private JPanel aSouth;
	private JPanel aWest;
	private JPanel aNorth;
	private JPanel aEast;
	private BiddingScreen aBidScreen;
	private ExchangeScreen aExchangeScreen;
	private TrickScreen aTrickScreen;
	private ScoreScreen aScoreScreen;
	private JButton aPlayButton;
	private JPanel aInitialPanel;
	private JTextArea aWelcome;
	
	/**
	 * Represents which game view is active.
	 */
	private enum ScreenType
	{BidOn, WidowOn, TrickOn, Score}

	/**
	 * Construct a BoardPanel.
	 */
	public BoardPanel()
	{
		setOpaque(false);
		GameRunner.addObserver(this);

		aYGrid = new GridBagConstraints();
		aYGrid.gridx = 0;
		aYGrid.gridy = GridBagConstraints.RELATIVE;

	    setLayout(new TableLayout(SIZE));
	    
		aSouth = new JPanel(new GridBagLayout());
		aWest = new JPanel(new GridBagLayout());
		aNorth = new JPanel(new GridBagLayout());
		aEast = new JPanel(new GridBagLayout());
		
		initHands();
		
		aErrorCounter = 0;
		aErrorInfo = new JLabel("   Welcome to Five Hundred!");
		aBidScreen = new BiddingScreen();
		aExchangeScreen = new ExchangeScreen();
		aScoreScreen = new ScoreScreen();
		aTrickScreen = new TrickScreen();
		
		aPlayButton = new JButton("Start a new game!");
		aPlayButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		aPlayButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode()==Mode.WAIT_NEW)
				{
					GameRunner.play();
				}
			}
		});
		
		aInitialPanel = new JPanel(new GridBagLayout());
		aWelcome = new JTextArea("              Gerald, Ioannis,Sherry,and Stephanie\n\n    warmly welcome you to the world of Five Hundred!!!\n\n");
		aWelcome.setFont(new Font("sansserif", Font.ITALIC, WELCOME_SIZE));
		aWelcome.setEditable(false);
		aInitialPanel.add(aWelcome, aYGrid);
		aInitialPanel.add(aPlayButton, aYGrid);
		add(aInitialPanel, "1,1,c,c");
		
		add(aSouth, "1,2");
		add(aWest, "0,0,0,2");
		add(aNorth, "1,0");
		add(aEast, "2,0,2,2");
		add(aErrorInfo, "0,3,2,3");
		
		setOpaqueComponents(this);
		setOpaqueComponents(aBidScreen);
		setOpaqueComponents(aTrickScreen);
		setOpaqueComponents(aScoreScreen);
		setOpaqueComponents(aExchangeScreen);
		
		aErrorInfo.setBackground(Color.white);
		aErrorInfo.setOpaque(true);
		
		setBackground(COLOR);
		
		setOpaque(true);
		
	}
	
	/**
	 * Displays to the player information relevant to the game.
	 * @param pInfo
	 * 		Information for the player
	 */
	public static void displayErrorInfo(String pInfo)
	{
		aErrorCounter ++;
		aErrorInfo.setText("   WARNING [" + aErrorCounter + "]:  " +pInfo);
	}

	
	
	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
		case DEALT:
			aErrorCounter = 0;
			remove(aInitialPanel);
			selectActiveScreen(ScreenType.BidOn);
			break;
		case WIDOW:
			if(GameRunner.getMode()==Mode.WAIT_DISCARD)
			{
				selectActiveScreen(ScreenType.WidowOn);
			}
			if(GameRunner.getMode()==Mode.WAIT_CLICK)
			{
				selectActiveScreen(ScreenType.TrickOn);
			}
			break;
		case EXCHANGE:
			selectActiveScreen(ScreenType.TrickOn);
			remove(aExchangeScreen);
			break;
		case SCORE:
			selectActiveScreen(ScreenType.Score);	
			break;
		default:
			break;
		}
		repaint();
		validate();
	}
	
	private void initHands()
	{
		aSouth.add(new CardPanel(), aYGrid);

		aWest.add(new RobotCards(0, CARD_SPACING, 1), aYGrid);

		aNorth.add(new RobotCards(CARD_SPACING, 0, 2), aYGrid);

		aEast.add(new RobotCards(0, CARD_SPACING, 3), aYGrid);
	
	}
	
	private void selectActiveScreen(ScreenType pType)
	{
		switch (pType)
		{
		case Score:
			remove(aTrickScreen);
			remove(aBidScreen);
			add(aScoreScreen, "1,1");
			break;
		case BidOn:
			remove(aScoreScreen);
			remove(aTrickScreen);
			remove(aPlayButton);
			add(aBidScreen, "1,1");
			break;
		case WidowOn:
			remove(aTrickScreen);
			remove(aBidScreen);
			
			add(aExchangeScreen, "1,1");
			break;
		case TrickOn:
			remove(aBidScreen);
			remove(aExchangeScreen);
			add(aTrickScreen, "1,1");
			break;
		default:
			break;
		}
	}
	
	private static void setOpaqueComponents(Container pContainer) 
	{
		if (pContainer.getClass() != BoardPanel.class)
        {
        	((JComponent) pContainer).setOpaque(false);
        }
        Component[] components = pContainer.getComponents();
        for (Component component : components) 
        {
            ((JComponent) component).setOpaque(false);
            if (component instanceof Container) 
            {
            	setOpaqueComponents((Container)component);
			}
		}
	}

}
