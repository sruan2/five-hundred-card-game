package comp303.fivehundred.ai.basic;

import comp303.fivehundred.ai.ICardExchangeStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

/**
 * @author Gerald Lang 260402748
 * Picks the six lowest non-trump cards.
 */

public class BasicCardExchangeStrategy implements ICardExchangeStrategy
{
	private static final int WIDOW_HAND_SIZE = 16;
	private static final int CARDS_TO_REMOVE = 6;
	private static final int BID_LENGTH = 4;
	private static final int MIN_BID_INDEX = 0;
	private static final int MAX_BID_INDEX = 3;
	
	@Override
	public CardList selectCardsToDiscard( Bid[] pBids, int pIndex, Hand pHand)
	{
		assert pBids.length == BID_LENGTH && pHand.size()==WIDOW_HAND_SIZE && pIndex>=MIN_BID_INDEX && pIndex <= MAX_BID_INDEX;
		assert !Bid.max(pBids).isPass();
		
		Suit trumpSuit = Bid.max(pBids).getSuit();
		
		CardList cardRemoved = new CardList();
        Hand clonedHand = pHand.clone();
        
        while (cardRemoved.size() != CARDS_TO_REMOVE)
        {
        	Card toRemove = clonedHand.selectLowest(trumpSuit);
        	clonedHand.remove(toRemove);
        	cardRemoved.add(toRemove);
        }
        
        return cardRemoved;
	}
}
