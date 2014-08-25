package comp303.fivehundred.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardImages;
import comp303.fivehundred.util.CardList;

/**
 * @author Shanshan Ruan (Sherry)
 *
 */
@SuppressWarnings("serial")
public class WidowPanel extends JPanel
{
	private static final int WIDOW_SIZE = 6;
	
	private GridBagConstraints aGrid;
	
	/**
	 * Constructs a WidowPanel with hidden cards.
	 */
	public WidowPanel()
	{
		super(new GridBagLayout());
		
		GridBagConstraints yGrid = new GridBagConstraints();
	    yGrid.gridx = GridBagConstraints.RELATIVE;
	    yGrid.gridy = GridBagConstraints.RELATIVE;
	    initializeBack();
	}
	
	/**
	 * Constructs a WidowPanel with the pCards.
	 * @param pCards
	 * 		The cards to show
	 */		
	public WidowPanel(CardList pCards)
	{
		super(new GridBagLayout());
		
		GridBagConstraints yGrid = new GridBagConstraints();
	    yGrid.gridx = GridBagConstraints.RELATIVE;
	    yGrid.gridy = GridBagConstraints.RELATIVE;
	    initializeFront(pCards);
	}
	

	private void initializeBack()
	{
		for (int i = 0; i<WIDOW_SIZE; i++)
		{
			add(new JLabel(CardImages.getBack()));
		}
		validate();
		repaint();
	}


	private void initializeFront(CardList pCards)
	{
		for( Card card : pCards )
		{
			JLabel lLabel = new JLabel( CardImages.getCard(card));
			add(lLabel, aGrid);
		}
		validate();
		repaint();
	}
}