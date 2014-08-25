package comp303.fivehundred.model;

import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.BySuitNoTrumpComparator;
import comp303.fivehundred.util.Card.BySuitComparator;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;


/**
 * @author Ioannis Fytilis 260482744
 * A card list specialized for handling cards discarded
 * as part of the play of a trick.
 */
public class Trick extends CardList
{	
	
	private Suit aSuit;
	private Bid aContract;
	
	/**
	 * Constructs a new empty trick for the specified contract.
	 * @param pContract The contract that this trick is played for.
	 */
	public Trick(Bid pContract)
	{
		aSuit = pContract.getSuit();
		aContract = pContract;
	}

	@Override
	public Trick clone()
	{
		Trick cloned = (Trick) super.clone();
		
		cloned.aSuit = aSuit;
		cloned.aContract = aContract;
		
		return cloned;
	}
	
	/**
	 * @return Can be null for no-trump.
	 */
	public Suit getTrumpSuit()
	{
		return aSuit;
	}
	
	
	/**
	 * @return The effective suit led.
	 */
	public Suit getSuitLed()
	{
		if (size()==0)
		{
			return null;
		}
		Card first = getFirst();
		if (first==AllCards.aHJo || first==AllCards.aLJo)
		{
			return getTrumpSuit();
		}
		else
		{
			return first.getEffectiveSuit(getTrumpSuit());
		}
	}
	
	/**
	 * @return True if a joker led this trick
	 */
	public boolean jokerLed()
	{
		if (size()==0)
		{
			return false;
		}
		return getFirst().isJoker();
	}
	
	/**
	 * @return The card that led this trick
	 * @pre size() > 0
	 */
	public Card cardLed()
	{
		assert size() > 0;
		return getFirst();
	}

	/**
	 * @return Highest card that actually follows suit (or trumps it).
	 * I.e., the card currently winning the trick.
	 * @pre size() > 0
	 */
	public Card highest()
	{
		assert size() > 0;
		
		Suit suitLed;
		
		if (getFirst().isJoker())
		{
			suitLed = null;
		}
		else
		{
			suitLed = getFirst().getSuit();
		}
		if(aSuit==null || suitLed==null)
		{
			CardList a = sort(new BySuitNoTrumpComparator());
			if (a.getLast().isJoker())
			{
				return a.getLast();
			}
			else
			{
				return bestOfSuit(a, suitLed);
			}
		}

		else
		{
			CardList sorted = sort(new BySuitComparator(aSuit));
			Card lastCard = sorted.getLast();
			
			if (lastCard.isJoker() || lastCard.getEffectiveSuit(aSuit)==aSuit)
			{
				return sorted.getLast();
			}
			else
			{
				return bestOfSuit(sorted, suitLed);
			}
		}
	}
	
	/**
	 * @return The index of the card that wins the trick.
	 */
	public int winnerIndex()
	{
		Card pBest = highest();
		int pCount = 0;

		for (Card card : this)
		{
			if (pBest.equals(card))
			{
				return pCount;
			}
			else
			{
				pCount++;
			}
		}
		return -1;
	}
	
	private Card bestOfSuit(CardList pList, Suit pSuitLed)
	{
		Card bestCard = pList.getFirst();
		for (Card card : pList)
		{
			if (card.getSuit()==pSuitLed && bestCard.getSuit() != pSuitLed)
			{
				bestCard = card;
			}
			else if (bestCard.getSuit() == pSuitLed && card.getSuit() == pSuitLed && bestCard.getRank().compareTo(card.getRank()) < 0)
			{
				bestCard = card;
			}
		}
		return bestCard;
	}
}
