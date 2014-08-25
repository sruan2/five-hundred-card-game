package comp303.fivehundred.ai.basic;

import comp303.fivehundred.ai.IBiddingStrategy;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * @author Shanshan Ruan 260471837
 * Careful decides what to Bid based on previous Bids and the Player's hand.
 */
public class BasicBiddingStrategy implements IBiddingStrategy
{
	// points per card type
	private static final int HIGH_JOKER_POINTS = 4;
	private static final int LOW_JOKER_POINTS = 3;
	private static final int BOWER_POINTS = 2;

	private static final int BID_POINTS = 1;
	private static final int CONTRACT_SIX_POINTS = 7;
	private static final int CONTRACT_SEVEN_POINTS = 9;
	private static final int CONTRACT_EIGHT_POINTS = 11;
	private static final int CONTRACT_NINE_POINTS = 13;
	private static final int CONTRACT_TEN_POINTS = 15;

	// SPADES, CLUBS, DIAMONDS, HEARTS
	private static final int NB_SUIT = 4;
	private static final int TOTAL_CHOICE = 5;

	// give 1 point per trump after this threshold
	private static final int SUIT_IN_HAND_THRESHOLD = 5;

	// possible numbers of tricks to bid
	private static final int SIX_TRICKS = 6;
	private static final int SEVEN_TRICKS = 7;
	private static final int EIGHT_TRICKS = 8;
	private static final int NINE_TRICKS = 9;
	private static final int TEN_TRICKS = 10;
	
	private static final int BIDS_MAX = 4;
	private static final int TRICK_MIN = 6;
	private static final int TRICK_MAX = 10;
	private static final int HAND_SIZE = 10;
	
	private int aBidsLength;

	@Override
	public Bid selectBid(Bid[] pBids, Hand pHand)
	{
		
		assert pBids.length < BIDS_MAX;
		assert pHand.size() == HAND_SIZE;
		
		aBidsLength = pBids.length;
		int[] aSuitPointList = new int[TOTAL_CHOICE];
		
		for (int i = 0; i < TOTAL_CHOICE; i++)
		{
			Suit suit = indexToSuit(i);
			aSuitPointList[i] = sumUpPoints(pHand, suit);	// Consider each suit's potential
		}
		for (int i = 0; i < TOTAL_CHOICE; i++)
		{
			Suit suit = indexToSuit(i);
			aSuitPointList[i] = addPointsPartnerBid(pBids, suit, aSuitPointList[i]);
			aSuitPointList[i] = removePointsOpponentBid(pBids, suit, aSuitPointList[i]);
		}
		int maxSuit = selectHighestPointBid(aSuitPointList);		// The suit we should bid on
		int maxPoints = aSuitPointList[maxSuit];
		if (maxPoints < CONTRACT_SIX_POINTS)
		{
			return new Bid();
		}
		int maxTrick = decideMaxTrick(maxPoints);
		Bid winningBid = Bid.max(pBids);
		if (maxTrick >= TRICK_MIN && maxTrick <= TRICK_MAX)
		{
			Bid potentialBid = new Bid(maxTrick, indexToSuit(maxSuit));
			if (winningBid.isPass() || potentialBid.toIndex() > winningBid.toIndex())
			{
				return potentialBid;
			}
		}
		return new Bid();
	}

	private int sumUpPoints(Hand pHand, Suit pSuit)
	{
		int aPoint = 0;

		CardList cards;
		if (pSuit != null)
		{
			cards = pHand.getTrumpCards(pSuit);
		}
		else
		{
			cards = pHand;
		}
		for (Card card : cards)
		{
			if (card.isJoker())
			{
				if (card.getJokerValue() == Joker.HIGH)
				{
					aPoint = aPoint + HIGH_JOKER_POINTS;
				}
				if (card.getJokerValue() == Joker.LOW)
				{
					aPoint = aPoint + LOW_JOKER_POINTS;
				}
			}
			else if (pSuit != null && card.getRank() == Rank.JACK && card.getEffectiveSuit(pSuit) == pSuit)
			{
				aPoint += BOWER_POINTS;
			}
			else
			{
				if (card.getRank() == Rank.ACE)
				{
					aPoint++;
					if (pSuit==null)
					{
						aPoint++;
					}
				}
				else if(card.getRank().compareTo(Rank.JACK) >= 0)
				{
					aPoint++;
				}
			}
		}
		if (pSuit != null && cards.size() > SUIT_IN_HAND_THRESHOLD)
		{
			aPoint += cards.size() - SUIT_IN_HAND_THRESHOLD;
		}
		return aPoint;
	}

	private int addPointsPartnerBid(Bid[] pBids, Suit pSuit, int pPoint)
	{
		if (aBidsLength >= 2 && !pBids[aBidsLength - 2].isPass() && pBids[aBidsLength - 2].getSuit() == pSuit)
		{
			return pPoint + BID_POINTS;
		}
		return pPoint;
	}

	private int removePointsOpponentBid(Bid[] pBids, Suit pSuit, int pPoint)
	{
		int aPoint = pPoint;
		if (aBidsLength >= 1 && !pBids[aBidsLength - 1].isPass() && pBids[aBidsLength - 1].getSuit() == pSuit)
		{
			aPoint -= BID_POINTS;
		}
		if (aBidsLength == 3 && !pBids[aBidsLength - 3].isPass() && pBids[aBidsLength - 3].getSuit() == pSuit)
		{
			aPoint -= BID_POINTS;
		}
		return aPoint;
	}

	private int selectHighestPointBid(int[] pSuitPointList)
	{
		int maxIndex = 0;
		int maxPoints = pSuitPointList[0];
		for (int i = 1; i < TOTAL_CHOICE; i++)
		{
			if (pSuitPointList[i] >= maxPoints)
			{
				maxPoints = pSuitPointList[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private int decideMaxTrick(int pPoint)
	{
		if (pPoint >= CONTRACT_TEN_POINTS)
		{
			return TEN_TRICKS;
		}
		else if (pPoint >= CONTRACT_NINE_POINTS)
		{
			return NINE_TRICKS;
		}
		else if (pPoint >= CONTRACT_EIGHT_POINTS)
		{
			return EIGHT_TRICKS;
		}
		else if (pPoint >= CONTRACT_SEVEN_POINTS)
		{
			return SEVEN_TRICKS;
		}
		else
		{
			return SIX_TRICKS;
		}
	}
	
	private Suit indexToSuit(int pIndex)
	{
		if (pIndex >= NB_SUIT)
		{
			return null;
		}
		else
		{
			return Suit.values()[pIndex];
		}
	}
}
