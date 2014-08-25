package comp303.fivehundred.ai.human;

import comp303.fivehundred.ai.IPlayer;
import comp303.fivehundred.ai.advanced.AdvancedRobot;
import comp303.fivehundred.ai.basic.BasicRobot;
import comp303.fivehundred.ai.random.RandomRobot;
import comp303.fivehundred.gui.BiddingPanel;
import comp303.fivehundred.gui.GUIException;
import comp303.fivehundred.gui.GameRunner;
import comp303.fivehundred.gui.InputDialog.PlayerType;
import comp303.fivehundred.gui.external.CardPanel;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * A human player.
 */

public class HumanPlayer implements IPlayer
{
	private String aName;
	private IPlayer aAutoplay;
	
	/**
	 * Constructor for a human player.
	 * @param pName
	 * 		The player's name
	 * @param pType 
	 */
	public HumanPlayer(String pName, PlayerType pType)
	{
		aName = pName;
		switch (pType)
		{
			case Random:
				aAutoplay = new RandomRobot("Random-autobot");
				break;
			case Basic:
				aAutoplay = new BasicRobot("Basic-autobot");
				break;
			case Advanced:
				aAutoplay = new AdvancedRobot("Advanced-autobot");
				break;
		default:
			throw new GUIException("Human mode initialized incorrectly");
		}
	}
	
	
	@Override
	public String getName()
	{
		return aName;
	}
	
	@Override
	public Bid selectBid(Bid[] pBids, Hand pHand)
	{
		if(GameRunner.getMode()==GameRunner.Mode.AUTOPLAY)
		{
			return aAutoplay.selectBid(pBids, pHand);
		}
		else
		{
			return BiddingPanel.analyzeBid();	
		}
	}

	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		if(GameRunner.getMode()==GameRunner.Mode.AUTOPLAY)
		{
			return aAutoplay.selectCardsToDiscard(pBids, pIndex, pHand);
		}
		else
		{
			return CardPanel.analyzeDiscardedCards();
		}
	}

	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		if(GameRunner.getMode()==GameRunner.Mode.AUTOPLAY)
		{
			return aAutoplay.play(pTrick, pHand);
		}
		else
		{
			return CardPanel.analyzeCards();
		}
	}

}
