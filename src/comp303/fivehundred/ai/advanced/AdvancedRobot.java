package comp303.fivehundred.ai.advanced;

import comp303.fivehundred.ai.IBiddingStrategy;
import comp303.fivehundred.ai.ICardExchangeStrategy;
import comp303.fivehundred.ai.IPlayingStrategy;
import comp303.fivehundred.ai.IRobotPlayer;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * A smart AI who uses advanced techniques.
 */

public class AdvancedRobot implements IRobotPlayer
{
	private IBiddingStrategy aBiddingStrategy;
	private ICardExchangeStrategy aCardExchangeStrategy;
	private IPlayingStrategy aPlayingStrategy;
	private String aName;
	
	/**
	 * Constructs an advanced robot player.
	 * @param pName
	 * 		The name of the player
	 */
	public AdvancedRobot(String pName)
	{
		aName = pName;
		aBiddingStrategy = new AdvancedBiddingStrategy();
		aCardExchangeStrategy = new AdvancedCardExchangeStrategy();
		aPlayingStrategy = new AdvancedPlayingStrategy();
	}
	
	@Override
	public Bid selectBid(Bid[] pPreviousBids, Hand pHand)
	{
		return aBiddingStrategy.selectBid(pPreviousBids, pHand);
	}

	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		return aCardExchangeStrategy.selectCardsToDiscard(pBids, pIndex, pHand);
	}

	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		return aPlayingStrategy.play(pTrick, pHand);
	}

	@Override
	public String getName()
	{
		return aName;
	}

}
