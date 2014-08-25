package comp303.fivehundred.ai.random;

import comp303.fivehundred.ai.IPlayingStrategy;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;

/**
 * @author Ioannis Fytilis 260482744
 * If leading, picks a card at random except a joker if the contract is in no trump.
 * If not leading and the hand contains cards that can follow suit, pick a suit-following 
 * card at random. If not leading and the hand does not contain cards that can follow suit,
 * pick a card at random (including trumps, if available).
 */

public class RandomPlayingStrategy implements IPlayingStrategy
{
	
	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		assert pTrick != null && pHand != null;
		assert pTrick.size() >= 0 && pHand.size() > 0;
		
		if (pTrick.size()==0)
		{
			return pHand.canLead(pTrick.getTrumpSuit()==null).random();
		}
		else
		{
			return pHand.playableCards(pTrick.getSuitLed(), pTrick.getTrumpSuit()).random();
		}
		
	}
	
}
