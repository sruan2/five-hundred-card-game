package comp303.fivehundred.ai.basic;

import comp303.fivehundred.ai.IPlayingStrategy;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

/**
 * @author Ioannis Fytilis 260482744
 * If leading, picks a card at random except jokers if playing in no trump. 
 * If following, choose the lowest card that can follow suit and win. 
 * If no card can follow suit and win, picks the lowest card that can follow suit. 
 * If no card can follow suit, picks the lowest trump card that can win. 
 * If there are no trump card or the trump cards can't win 
 * (because the trick was already trumped), then picks the lowest card. 
 * If a joker was led, dump the lowest card unless it can be beaten with 
 * the high joker according to the rules of the game.
 */

public class BasicPlayingStrategy implements IPlayingStrategy
{
	
	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		assert pTrick != null && pHand != null;
		assert pTrick.size() >= 0 && pHand.size() > 0;
		
		Suit pSuitLed = pTrick.getSuitLed();
		Suit pTrump = pTrick.getTrumpSuit();
		
		if (pTrick.size() == 0)
		{
			return pHand.canLead(pTrick.getTrumpSuit()==null).random();
		}
		else
		{
			Hand pFollowable = toHand(pHand.playableCards(pSuitLed, pTrump));
			Hand winningCards = couldWin(pTrick, pFollowable);
			if (winningCards.size() != 0)
			{
				return winningCards.selectLowest(pTrump);
			}
			else
			{
				return pFollowable.selectLowest(pTrump);
			}
		}
	}
	
	private Hand couldWin(Trick pTrick, Hand pHand)
	{
		Trick cloned = pTrick.clone();
		Hand winningCards = new Hand();
		
		for (Card card : pHand)
		{
			cloned.add(card);
			if (cloned.highest().equals(card))
			{
				winningCards.add(card);
			}
			cloned.remove(card);
		}
		return winningCards;
	}
	
	private Hand toHand(CardList pList)
	{
		Hand pHand = new Hand();
		for (Card card : pList)
		{
			pHand.add(card);
		}
		return pHand;
	}
	
}
