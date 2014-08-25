package comp303.fivehundred.ai.random;

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
 * Plays correctly but randomly, i.e., very badly.
 */
public class RandomRobot implements IRobotPlayer
{
	private IBiddingStrategy aBiddingStrategy;
	private ICardExchangeStrategy aCardExchangeStrategy;
	private IPlayingStrategy aPlayingStrategy;
	private String aName;
	
	/**
	 * Constructs a random robot player.
	 * @param pName 
	 * 		the name of the player
	 */
	public RandomRobot(String pName)
	{
		aBiddingStrategy = new RandomBiddingStrategy();
		aCardExchangeStrategy = new RandomCardExchangeStrategy();
		aPlayingStrategy = new RandomPlayingStrategy();
		aName = pName;
	}
	
	@Override
	public String getName()
	{
		return aName;
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
}
