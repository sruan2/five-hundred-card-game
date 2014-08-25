package comp303.fivehundred.gui;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * @author Ioannis Fytilis 260482744
 * Displays a dialog which asks the user for input, to customize players.
 * Inspired partially by : http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 */

@SuppressWarnings("serial")
public class InputDialog extends JPanel
{

	/**
	 * All possible IPlayer types.
	 */
	public enum PlayerType 
	{Random, Basic, Advanced};
	
	private static final int TEXT_BOX_SIZE = 10;
	
	private String[] aNames;
	private PlayerType[] aDifficulty;
	private JTextField[] aTxtFields;
	private JPanel[] aPanels;
	private JPanel aOptionPanel;
	private ButtonGroup[] aAIButtons;
	private JCheckBox aHumanPlayBox;
	private JRadioButton[][] aGroupButtons;
	
	/**
	 * Constructs the dialog.
	 */
	public InputDialog()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		aAIButtons = new ButtonGroup[4];
		aPanels = new JPanel[4];
		aTxtFields = new JTextField[4];
		aNames = new String[4];
		aDifficulty = new PlayerType[4];
		aGroupButtons = new JRadioButton[4][3];
		for (int i = 0; i<4; i++)
		{
			aAIButtons[i] = new ButtonGroup();
			aPanels[i] = new JPanel();
			aTxtFields[i] = new JTextField(TEXT_BOX_SIZE);
			aPanels[i].add(aTxtFields[i]);
			add(aPanels[i]);
			aNames[i] = "Player " + (i+1); 
			aTxtFields[i].setText(aNames[i]);
			aDifficulty[i] = PlayerType.Advanced;
			
			aGroupButtons[i][0] = new JRadioButton("Random");
			aGroupButtons[i][1] = new JRadioButton("Basic");
			aGroupButtons[i][2] = new JRadioButton("Advanced", true);
			
			aAIButtons[i].add(aGroupButtons[i][0]);
			aAIButtons[i].add(aGroupButtons[i][1]);
			aAIButtons[i].add(aGroupButtons[i][2]);
			
			aPanels[i].add(aGroupButtons[i][0]);
			aPanels[i].add(aGroupButtons[i][1]);
			aPanels[i].add(aGroupButtons[i][2]);
			
		}
		
		aOptionPanel = new JPanel();
		add(aOptionPanel);	
		aHumanPlayBox = new JCheckBox("<html>Enable Human Mode (Player 1 will be played by you!)<br>" +
										"In which case, the AI-level specified for <br> Player 1 will be the level of your Autoplay</html>"); 
		
		aOptionPanel.add(aHumanPlayBox);
	}
	
	
	/**
	 * Shows the dialog which asks for input.
	 */
	public void generatePlayers()
	{

		int closedHow;
		do
		{
			closedHow = JOptionPane.showConfirmDialog(null, this, "Player Setup",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		} while (closedHow == JOptionPane.CLOSED_OPTION);

		for (int i = 0; i < 4; i++)
		{
			aNames[i] = aTxtFields[i].getText();
			aDifficulty[i] = getActiveButtonType(aAIButtons[i]);
		}
	}

	/**
	 * @return the names of the players
	 */
	public String[] getNames()
	{
		return aNames;
	}
	
	/**
	 * @return the types of the players
	 */
	public PlayerType[] getPlayerTypes()
	{
		return aDifficulty;
	}
	
	/**
	 * @return whether a human is playing
	 */
	public boolean isHumanMode()
	{
		return aHumanPlayBox.isSelected();
	}
	
	private PlayerType getActiveButtonType(ButtonGroup pButtonGrp)
	{
		Enumeration<AbstractButton> elements = pButtonGrp.getElements();
    	while (elements.hasMoreElements())
    	{
    		AbstractButton button = elements.nextElement();
    		if (button.isSelected())
    		{
    			String type = button.getText();
    			for (PlayerType each : PlayerType.values())
    			{
    				if (each.toString().equals(type))
    				{
    					return each;
    				}
    			}
    		}
    	}
    	
    	throw new GUIException("No Selected Button from InputDialog");
	}

}
