package comp303.fivehundred.ai.advanced;

import comp303.fivehundred.ai.IBiddingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Gerald Lang 260402748
 * In addition to BasicBiddingStrategy's functionality, 
 * this class:
 * 
 * 1.) Accounts for the value of high rank cards (Ace to Jack)
 * that are not in the suit being considered for bidding.
 * For example the sumUpPoints method, when considering
 * the viability of bidding hearts for a given hand,
 * will add points to aPoint if the hand contains
 * an Ace of Spades.
 * 
 * 2.) Increases the value of trump bids if void (or almost
 * void - only 1 card of suit in hand) suits exist.
 * 
 * Also note the constants were altered to fit
 * this new implementation.
 */
public class AdvancedBiddingStrategy implements IBiddingStrategy
{
	
	// points per card type
	private static final int HIGH_JOKER_POINTS = 8;
	private static final int LOW_JOKER_POINTS = 6;
	private static final int BOWER_POINTS = 4;
	
	private static final int BID_POINTS = 1;
	private static final int VOID_SUIT_POINTS = 5;
	private static final int ALMOST_VOID_SUIT_POINTS = 2;
	
	private static final int CONTRACT_SIX_POINTS = 19;
	private static final int CONTRACT_SEVEN_POINTS = 27;
	private static final int CONTRACT_EIGHT_POINTS = 35;
	private static final int CONTRACT_NINE_POINTS = 42;
	private static final int CONTRACT_TEN_POINTS = 47;
	
	// SPADES, CLUBS, DIAMONDS, HEARTS
	private static final int NB_SUIT = 4;
	private static final int TOTAL_CHOICE = 5;

	// give 2 points per trump after this threshold
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

	/**
	 * Selects an optimal bid.
	 * @param pBids
	 * 		Other player's bids
	 * @param pHand
	 * 		Your Hand
	 * @return an appropriate Bid
	 */
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
			aSuitPointList[i] = addPointsVoidSuits(pHand, suit, aSuitPointList[i]);
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

		CardList cards = pHand;
		
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
					aPoint += 2;
					if (pSuit==null)
					{
						aPoint += 3;
					}
				}
				else if(card.getRank().compareTo(Rank.JACK) >= 0)
				{
					if(pSuit != null && card.getSuit() == pSuit)
					{
						aPoint += 2;
					}
					else if(pSuit != null)
					{
						aPoint += 1;
					}
					else
					{
						aPoint += 3;
					}
				}
			}
		}
		if (pSuit != null && cards.size() > SUIT_IN_HAND_THRESHOLD)
		{
			aPoint += (cards.size() - SUIT_IN_HAND_THRESHOLD)*2;
		}
		
		return aPoint;
	}
	
	private int addPointsVoidSuits(Hand pHand, Suit pSuit, int pPoint)
	{
		int aPoint = pPoint;
		int[] suitCount = new int[4];
		
		if(pSuit != null)
		{
			for(Card card : pHand)
			{
				if(!card.isJoker())
				{
					Suit cardSuit = card.getSuit();
					switch(cardSuit)
					{
					case SPADES:
						suitCount[0]++;
						break;
					case CLUBS:
						suitCount[1]++;
						break;
					case DIAMONDS:
						suitCount[2]++;
						break;
					case HEARTS:
						suitCount[3]++;
						break;
					default:
						break;
					}
				}
			}
			
			for(int i = 0 ; i<suitCount.length; i++)
			{
				if(indexToSuit(i) != pSuit)
				{
					if( suitCount[i] == 0 && indexToSuit(i) != pSuit )
					{
						aPoint += VOID_SUIT_POINTS;
					}
					else if( suitCount[i] == 1)
					{
						aPoint += ALMOST_VOID_SUIT_POINTS;
					}
				}
			}
		}
		
		return aPoint;
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
	/*
	private Bid preventOverBid( Bid pBid, Bid[] pBids )
	{
		int playerTeam = pBids.length % 2;
		int teamScore = this.getScore(playerTeam);
		if()
	}
	*/
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
