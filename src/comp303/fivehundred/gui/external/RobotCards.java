package comp303.fivehundred.gui.external;

import java.awt.Insets;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.GameRunner;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardImages;
import comp303.fivehundred.util.CardList;

/**
 * @author Shanshan Ruan (Sherry), based on CardPanel by Martin Robillard
 * Manages a CardList and supports selection of an individual card.
 *
 */
@SuppressWarnings("serial")
public class RobotCards extends JPanel implements IObserver
{

	private static final int HAND_SIZE = 10;
	private static final int INSET_MARGIN = 5;
	
	private int aIndex;
	// Read-only: not synchronized with the GameEngine
	private HashMap<JLabel, Card> aCards = new HashMap<JLabel, Card>();
	
	/**
	 * 
	 * @param pX xIndex for OverlapLayout
	 * @param pY yIndex for OverlapLayout
	 * @param pIndex the player's index
	 */
	public RobotCards(int pX, int pY, int pIndex)
	{
		super(new OverlapLayout(new Point(pX, pY)));
		
		GameRunner.addObserver(this);
		Insets ins = new Insets(INSET_MARGIN, INSET_MARGIN, INSET_MARGIN, INSET_MARGIN);
		aIndex = pIndex;
		((OverlapLayout)getLayout()).setPopupInsets(ins);
	}
	
	
	private void initialize(CardList pCards)
	{
		setBorder(new TitledBorder( GameRunner.getEngine().getPlayer(aIndex).getName()));
		aCards.clear();
		removeAll();
		for( Card card : pCards )
		{
			JLabel lLabel = new JLabel( CardImages.getBack());
			aCards.put(lLabel, card);
			add(lLabel);
		}
		validate();
		repaint();
	}

	private void discard(Card pCard)
	{
		JLabel lToRemove = null;
		for( JLabel label : aCards.keySet())
		{
			if( aCards.get(label).equals(pCard))
			{
				lToRemove = label;
				break;
			}
		}
		aCards.remove(lToRemove);
		removeAll();
		CardList lList = new CardList();
		for(Card card : aCards.values())
		{
			lList.add(card);
		}
		lList = lList.sort(new Card.ByRankComparator());
		initialize(lList);
		validate();
		repaint();
	}


	@Override
	public void update(Message pMessage)
	{
		switch (pMessage)
		{
			case DEALT:
				Hand hand = GameRunner.getEngine().getHand(aIndex);
				if (hand.size()==HAND_SIZE)
				{
					initialize(hand.sort(new Card.ByRankComparator()));
				}
				break;
			case EXCHANGE:
				hand = GameRunner.getEngine().getHand(aIndex);
				if (GameRunner.getEngine().getContractorIndex()==aIndex)
				{
					initialize(hand.sort(new Card.ByRankComparator()));
				}
				break;
			case TRICK:
				hand = GameRunner.getEngine().getHand(aIndex);
				if (GameRunner.getEngine().getLatestPlayerIndex()==aIndex)
				{
					discard(GameRunner.getEngine().getLatestCard());
				}
				break;
		default:
			break;
		}
		
	}

}