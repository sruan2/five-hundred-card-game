package comp303.fivehundred.model;

import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.ByRankComparator;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

/**
 * @author Gerald Lang 260402748
 * Additional services to manage a card list that corresponds to
 * the cards in a player's hand.
 */
public class Hand extends CardList
{
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc}
	 */
	@Override
	public Hand clone()
	{
        return (Hand) super.clone();
	}
	
	/**
	 * @param pNoTrump If the contract is in no-trump
	 * @return A list of cards that can be used to lead a trick.
	 */
	public CardList canLead(boolean pNoTrump)
	{
        if(pNoTrump)
        {
        	CardList nonJokers = getNonJokers();
            if (nonJokers.size()==0)
            {
            	return clone();
            }
            else
            {
            	return nonJokers;
            }
        }
        else
        {
        	return clone();
        }
	}
	
	/**
	 * @return The cards that are jokers.
	 */
	public CardList getJokers()
	{
        CardList jokers = new CardList();
        for (Card card : this)
		{
			if (card.isJoker())
			{
				jokers.add(new Card(card.getJokerValue()));
			}
		}
        return jokers;
	}
	
	/**
	 * @return The cards that are not jokers.
	 */
	public CardList getNonJokers()
	{
		CardList nonJokers = new CardList();
        for (Card card : this)
		{
			if (!card.isJoker())
			{
				nonJokers.add(new Card(card.getRank(), card.getSuit()));
			}
		}
        return nonJokers;
	}
	
	/**
	 * Returns all the trump cards in the hand, including jokers.
	 * Takes jack swaps into account.
	 * @param pTrump The trump to check for. Cannot be null.
	 * @return All the trump cards and jokers.
	 * @pre pTrump != null
	 */
	public CardList getTrumpCards(Suit pTrump)
	{
		assert pTrump != null;
		CardList trumps = new CardList();
		for(Card card: this)
		{
			if (card.isJoker())
			{
				trumps.add(new Card(card.getJokerValue()));
			}
			else if (card.getEffectiveSuit(pTrump).equals(pTrump))
			{
				trumps.add(new Card(card.getRank(), card.getSuit()));
			}
		}
		return trumps;
	}
	
	/**
	 * Returns all the cards in the hand that are not trumps or jokers.
	 * Takes jack swaps into account.
	 * @param pTrump The trump to check for. Cannot be null.
	 * @return All the cards in the hand that are not trump cards.
	 * @pre pTrump != null
	 */
	public CardList getNonTrumpCards(Suit pTrump)
	{
		assert pTrump != null;
		CardList noTrumps = new CardList();
		for(Card card: this)
		{
			if (!card.isJoker() && card.getEffectiveSuit(pTrump)!=pTrump)
			{
				noTrumps.add(new Card(card.getRank(), card.getSuit()));
			}
		}
		return noTrumps;
	}
	
	
	/**
	 * Selects the least valuable card in the hand, if pTrump is the trump.
	 * @param pTrump The trump suit. Can be null to indicate no-trump.
	 * @return The least valuable card in the hand.
	 */
	public Card selectLowest(Suit pTrump)
	{
		if (pTrump == null)
		{
			return sort(new ByRankComparator()).getFirst();
		}
		else
		{
			if (getNonTrumpCards(pTrump).size() != 0)
			{
				return getNonTrumpCards(pTrump).sort(new ByRankComparator()).getFirst();
			}
			else
			{
				return sort(new ByRankComparator()).getFirst();
			}
		}
	}
	/**
	 * Selects the least valuable card in the hand, if pTrump is the trump.
	 * @param pTrump The trump suit. Can be null to indicate no-trump.
	 * @return The least valuable card in the hand.
	 */
	public Card selectHighest(Suit pTrump)
	{
		if (pTrump == null)
		{
			return sort(new ByRankComparator()).getLast();
		}
		else
		{
			if (getNonTrumpCards(pTrump).size() != 0)
			{
				return getNonTrumpCards(pTrump).sort(new ByRankComparator()).getLast();
			}
			else
			{
				return sort(new ByRankComparator()).getLast();
			}
		}
	}
	/**
	 * @param pLed The suit led.
	 * @param pTrump Can be null for no-trump
	 * @return All cards that can legally be played given a lead and a trump.
	 */
	public CardList playableCards( Suit pLed, Suit pTrump )
	{
		if (pTrump!=null)
		{
			CardList sameSuitCards = new CardList();
			for (Card card: this)
			{
				if (card.isJoker() && pTrump.equals(pLed))
				{
					sameSuitCards.add(new Card(card.getJokerValue()));
				}
				if (!card.isJoker() && card.getEffectiveSuit(pTrump).equals(pLed))
				{
					sameSuitCards.add(new Card(card.getRank(), card.getSuit()));
				}
			}
			if (sameSuitCards.size()!=0)
			{
				return sameSuitCards;
			}
			else
			{
				return this.clone();
			}
		}
		else
		{
			CardList sameSuitCards = new CardList();
			for (Card card: this)
			{
				if (!card.isJoker() && card.getSuit().equals(pLed))
				{
					sameSuitCards.add(new Card(card.getRank(), card.getSuit()));
				}
			}
			if (sameSuitCards.size()!=0)
			{
				return sameSuitCards;
			}
			else
			{
				return this.clone();
			}
		}
	}
	
	/**
	 * Returns the number of cards of a certain suit 
	 * in the hand, taking jack swaps into account.
	 * Excludes jokers.
	 * @param pSuit Cannot be null.
	 * @param pTrump Can be null for no-trump mode
	 * @return Number of cards of the suit pSuit, with trump pTrump
	 */
	public int numberOfCards(Suit pSuit, Suit pTrump)
	{
		assert pSuit!=null;
		
		int num = 0;
		for (Card card: this)
		{
			if (!card.isJoker() && card.getEffectiveSuit(pTrump).equals(pSuit))
			{
				num++;
			}
		}
		return num;
	}
	
}
