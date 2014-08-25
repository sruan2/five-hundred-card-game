package comp303.fivehundred.ai.random;

import comp303.fivehundred.ai.ICardExchangeStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * @author Gerald Lang 260402748
 * Picks six cards at random. 
 */
public class RandomCardExchangeStrategy implements ICardExchangeStrategy
{
	
	private static final int WIDOW_HAND_SIZE = 16;
	private static final int CARDS_TO_REMOVE = 6;
	
	@Override
    public CardList selectCardsToDiscard( Bid[] pBids, int pIndex, Hand pHand )
	{
		
		assert pBids.length == 4 && pHand.size()==WIDOW_HAND_SIZE && pIndex>=0 && pIndex <=3;	
		assert !Bid.max(pBids).isPass();
    
        CardList cardRemoved = new CardList();
        
        while (cardRemoved.size() != CARDS_TO_REMOVE)
        {
        	Card toRemove = pHand.random();
        	cardRemoved.add(toRemove);						// Will only add the Card if it is not already in the CardList
        }
        
        return cardRemoved;
    }

}
