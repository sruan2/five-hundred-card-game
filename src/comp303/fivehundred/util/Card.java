package comp303.fivehundred.util;

import java.util.Comparator;

/**
 * @author Stephanie Pataracchia 260407002
 * An immutable description of a playing card.
 */
public final class Card implements Comparable<Card>
{
	/**
	 * Represents the rank of the card.
	 */
	public enum Rank
	{
		FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	}

	/**
	 * Represents the suit of the card.
	 */
	public enum Suit
	{
		SPADES, CLUBS, DIAMONDS, HEARTS;

		/**
		 * @return the other suit of the same colour.
		 */
		public Suit getConverse()
		{
			Suit lReturn = this;
			switch (this)
			{
			case SPADES:
				lReturn = CLUBS;
				break;
			case CLUBS:
				lReturn = SPADES;
				break;
			case DIAMONDS:
				lReturn = HEARTS;
				break;
			case HEARTS:
				lReturn = DIAMONDS;
				break;
			default:
				lReturn = this;
			}
			return lReturn;
		}
	}

	/**
	 * Represents the value of the card, if the card is a joker.
	 */
	public enum Joker
	{
		LOW, HIGH
	}

	// If this field is null, it means the card is a joker, and vice-versa.
	private final Rank aRank;

	// If this field is null, it means the card is a joker, and vice-versa.
	private final Suit aSuit;

	// If this field is null, it means the card is not a joker, and vice-versa.
	private final Joker aJoker;

	/**
	 * Create a new card object that is not a joker.
	 * 
	 * @param pRank
	 *            The rank of the card.
	 * @param pSuit
	 *            The suit of the card.
	 * @pre pRank != null
	 * @pre pSuit != null
	 */
	public Card(Rank pRank, Suit pSuit)
	{
		assert pRank != null;
		assert pSuit != null;
		aRank = pRank;
		aSuit = pSuit;
		aJoker = null;
	}

	/**
	 * Creates a new joker card.
	 * 
	 * @param pValue
	 *            Whether this is the low or high joker.
	 * @pre pValue != null
	 */
	public Card(Joker pValue)
	{
		assert pValue != null;
		aRank = null;
		aSuit = null;
		aJoker = pValue;
	}

	/**
	 * @return True if this Card is a joker, false otherwise.
	 */
	public boolean isJoker()
	{
		return aJoker != null;
	}

	/**
	 * @return Whether this is the High or Low joker.
	 */
	public Joker getJokerValue()
	{
		assert isJoker();
		return aJoker;
	}

	/**
	 * Obtain the rank of the card.
	 * 
	 * @return An object representing the rank of the card. Can be null if the card is a joker.
	 * @pre !isJoker();
	 */
	public Rank getRank()
	{
		assert !isJoker();
		return aRank;
	}

	/**
	 * Obtain the suit of the card.
	 * 
	 * @return An object representing the suit of the card
	 * @pre !isJoker();
	 */
	public Suit getSuit()
	{
		assert !isJoker();
		return aSuit;
	}

	/**
	 * Returns the actual suit of the card if pTrump is the trump suit. Takes care of the suit swapping of jacks.
	 * 
	 * @param pTrump
	 *            The current trump. Null if no trump.
	 * @return The suit of the card, except if the card is a Jack and its converse suit is trump. Then, returns the
	 *         trump.
	 */
	public Suit getEffectiveSuit(Suit pTrump)
	{
		if (pTrump == null)
		{
			return aSuit;
		}
		else if (aRank == Rank.JACK && aSuit == pTrump.getConverse())
		{
			return pTrump;
		}
		else
		{
			return aSuit;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return See above.
	 */
	public String toString()
	{
		if (!isJoker())
		{
			return aRank + " of " + aSuit;
		}
		else
		{
			return aJoker + " " + "Joker";
		}
	}

	/**
	 * @return A short textual representation of the card
	 */
	public String toShortString()
	{
		String lReturn = "";
		if (isJoker())
		{
			lReturn = aJoker.toString().charAt(0) + "J";
		}
		else
		{
			if (aRank.ordinal() <= Rank.NINE.ordinal())
			{
				lReturn += new Integer(aRank.ordinal() + 4).toString();
			}
			else
			{
				lReturn += aRank.toString().charAt(0);
			}
			lReturn += aSuit.toString().charAt(0);
		}
		return lReturn;
	}

	/**
	 * Compares two cards according to their rank.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @param pCard
	 *            The card to compare to
	 * @return Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater
	 *         than pCard
	 */
	public int compareTo(Card pCard)
	{
		assert ((pCard.aRank != null) && (pCard.aSuit != null)) || (pCard.aJoker != null);

		if ((aJoker != null) || (pCard.aJoker != null))
		{
			return jokerSort(aJoker, pCard.aJoker);
		}
		else
		{
			return aRank.compareTo(pCard.aRank);
		}
	}

	/**
	 * Two cards are equal if they have the same suit and rank or if they are two jokers of the same value.
	 * 
	 * @param pCard
	 *            The card to test.
	 * @return true if the two cards are equal
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pCard)
	{
		if (pCard==null || pCard.getClass() != Card.class)
		{
			return false;
		}

		if (aJoker != null)
		{
			if (((Card) pCard).aJoker != null)		
			{
				return compareTo((Card) pCard) == 0;
			}
			else
			{
				return false;
			}
		}
		return (compareTo((Card) pCard) == 0) && (aSuit.equals(((Card) pCard).aSuit));
	}

	/**
	 * The hashcode for a card is the suit*number of ranks + that of the rank (perfect hash).
	 * Hashcodes for all non-Joker cards range from 0-44 and low Joker is 45 and high Joker is 46
	 * 
	 * @return the hashcode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		if (aJoker == Joker.LOW)
		{
			return Suit.HEARTS.ordinal() * Rank.values().length + Rank.values().length + 1; // 45
		}
		else if (aJoker == Joker.HIGH)
		{
			return Suit.HEARTS.ordinal() * Rank.values().length + Rank.values().length + 2; // 46
		}
		else
		{
			return aSuit.ordinal() * Rank.values().length + aRank.ordinal();
		}
	}

	/**
	 * Compares cards using their rank as primary key, then suit. Jacks rank between 10 and queens of their suit.
	 */
	public static class ByRankComparator implements Comparator<Card>
	{
		@Override
		public int compare(Card pCard1, Card pCard2)
		{
			if ((pCard1.aJoker != null) || (pCard2.aJoker != null))
			{
				return jokerSort(pCard1.aJoker, pCard2.aJoker);
			}
			
			return rankSort(pCard1, pCard2);

		}

	}

	/**
	 * Compares cards using their suit as primary key, then rank. Jacks rank between 10 and queens of their suit.
	 */
	public static class BySuitNoTrumpComparator implements Comparator<Card>
	{

		@Override
		public int compare(Card pCard1, Card pCard2)
		{
			if ((pCard1.aJoker != null) || (pCard2.aJoker != null))
			{
				return jokerSort(pCard1.aJoker, pCard2.aJoker);
			}
			else
			{
				return suitSort(pCard1, pCard2);
			}

		}
	}

	/**
	 * Compares cards using their suit as primary key, then rank. Jacks rank above aces if they are in the trump suit.
	 * The trump suit becomes the highest suit.
	 */
	public static class BySuitComparator implements Comparator<Card>
	{
		private Suit aTrump;

		/**
		 *@param pTrump
		 * 			The suit of the trump
		 */
		public BySuitComparator(Suit pTrump)
		{
			aTrump = pTrump;
		}

		@Override
		public int compare(Card pCard1, Card pCard2)
		{
			if ((pCard1.aJoker != null) || (pCard2.aJoker != null))
			{
				return jokerSort(pCard1.aJoker, pCard2.aJoker);
			}
			else if (isTrumpJack(pCard1, aTrump) || isTrumpJack(pCard2, aTrump))
			{
				return jackSort(pCard1, pCard2, aTrump);
			}
			else if (pCard1.aSuit == aTrump && pCard2.aSuit == aTrump)
			{
				return rankSort(pCard1, pCard2);
			}
			else if (pCard1.aSuit == aTrump || pCard2.aSuit == aTrump)
			{
				return singleTrumpSort(pCard1, pCard2, aTrump);
			}
			return suitSort(pCard1, pCard2);
		}
	}

	/*
	 * HELPER FUNCTIONS
	 */
	
	/**
	 * Sorts 2 cards, where only 1 has a a "trump" advantage over the other one.
	 * 
	 * @pre pCard1 and pCard2 are not null
	 */
	private static int singleTrumpSort(Card pCard1, Card pCard2, Suit pTrump)
	{
		if (pCard1.aSuit == pTrump)
		{
			return 1;
		}
		else 
		{
			return -1;
		}
	}

	/**
	 * Sorts 2 cards, where at least 1 is a joker.
	 * 
	 * @pre at least one of pJ1 or pJ2 are not null
	 */
	private static int jokerSort(Joker pJ1, Joker pJ2)
	{
		assert (pJ1 != null) || (pJ2 != null);
		
		if (pJ1 == null)
		{
			return -1;
		}
		else if(pJ2 == null)
		{
			return 1;
		}
		else
		{
			return pJ1.compareTo(pJ2);
		}

	}

	/**
	 * A simple case of sorting by rank, and by suit if the ranks are equal.
	 * 
	 * @pre pCard1 and pCard2 are not null
	 */
	private static int rankSort(Card pCard1, Card pCard2)
	{
		assert (pCard1 != null) && (pCard2 != null);

		int rankCompare = pCard1.compareTo(pCard2);
		
		if (rankCompare != 0 )
		{
			return rankCompare;
		}
		else
		{
			return pCard1.aSuit.compareTo(pCard2.aSuit);
		}
	}

	/**
	 * A simple case of sorting by suit, and by rank if the suits are equal.
	 * 
	 * @pre pCard1 and pCard2 are not null
	 */
	private static int suitSort(Card pCard1, Card pCard2)
	{
		assert (pCard1 != null) && (pCard2 != null);

		int suitCompare = pCard1.aSuit.compareTo(pCard2.aSuit);
		
		if (suitCompare != 0 )
		{
			return suitCompare;
		}
		else
		{
			return pCard1.aRank.compareTo(pCard2.aRank);
		}
	}
	
	/**
	 * Returns whether a card is a jack which counts as trump.
	 * 
	 * @pre pCard != null
	 */
	private static boolean isTrumpJack(Card pCard, Suit pTrump)
	{
		assert pCard != null;
		if (pTrump==null)
		{
			return false;
		}
		return (pCard.aRank==Rank.JACK) && (pCard.getEffectiveSuit(pTrump) == pTrump);
	}

	/**
	 * Sorts considering jacks may count as trumps.
	 * 
	 * @pre pCard1, pCard2 and pTrump are not null. At least one of pCard1 or pCard2 is a Jack. None are jokers.
	 */
	private static int jackSort(Card pCard1, Card pCard2, Suit pTrump)
	{
		assert pCard1 != null && pCard2 != null && pTrump != null;
		assert (pCard1.aRank == Rank.JACK) || (pCard2.aRank == Rank.JACK);
		assert !pCard1.isJoker() && !pCard2.isJoker();
		
		if (isTrumpJack(pCard1, pTrump) && isTrumpJack(pCard2, pTrump))
		{
			if (pCard1 == pCard2)
			{
				return 0;
			}
			else if (pCard1.aSuit==pTrump)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		else if(isTrumpJack(pCard1, pTrump))
		{
			return 1;
		}
		else 
		{
			return -1;
		}

	}

}