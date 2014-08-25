package comp303.fivehundred.ai.advanced;

import comp303.fivehundred.ai.ICardExchangeStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Shanshan Ruan 260471837
 * Explain what it does.
 * 
 * 1) If the trumpSuit is not null and the player only has a few cards of a certain non-trump suit (i.e. <= FEW_ENOUGH_CARDS)
 *    then he just discards all cards of this suit except high rank cards (i.e. Ace) 
 *    so that he is more likely to trump others' cards when playing this suit
 *    
 * 2) If the player has the similar number(+/-EPSILON) of cards of two non-trump suits (both are small enough)
 *    he will discard the suit based on the following strategies: 
 *    1. Discard the suit that the opponent bidded before
 *    2. Discard the suit that has the lowest average rank (average rank = sum of ranks of a suit/ number of cards of this suit), 
 *    
 */
public class AdvancedCardExchangeStrategy implements ICardExchangeStrategy
{

	private static final int WIDOW_HAND_SIZE = 16;
	private static final int CARDS_TO_REMOVE = 6;
	private static final int BID_LENGTH = 4;
	private static final int MIN_BID_INDEX = 0;
	private static final int MAX_BID_INDEX = 3;
	private static final int FEW_ENOUGH_CARDS = 4; // the best threshold based on tests
	private static final int NUM_INCOMPARABLE = 20;
	private static final int HIGH_AVERAGE_RANK = 15;
	private static final int HIGH_RANK_CARD = 14; //ace
	private static final int EPSILON = 0; // based on tests, set it to 0 (but in fact we can remove it?)
	
	private CardList aCardListRemoved;
	
	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		assert pBids.length == BID_LENGTH && pHand.size()==WIDOW_HAND_SIZE;
		assert pIndex>=MIN_BID_INDEX && pIndex <= MAX_BID_INDEX;
		assert !Bid.max(pBids).isPass();
		
		aCardListRemoved = new CardList();
		
		Suit trumpSuit = Bid.max(pBids).getSuit();
		Suit opponentSuit = null;
		if (pIndex ==2 && !pBids[1].isPass())
		{
			opponentSuit = pBids[1].getSuit();
		}
		if(pIndex ==3 && !pBids[2].isPass())
		{
			opponentSuit = pBids[2].getSuit();
		}
		if(pIndex == 4 && !pBids[3].isPass())
		{
			opponentSuit = pBids[3].getSuit();
		}
		else if (pIndex == 4 && !pBids[1].isPass())
		{
			opponentSuit = pBids[1].getSuit();
		}
		
        Hand clonedHand = pHand.clone();
        
        
        // remove all low rank cards of one suit first if possible
        if (trumpSuit != null)
        {
        	Hand reducedHand = getLowRankCards(clonedHand, trumpSuit);
            int[] numberOfCards = new int[4];
            double[] averageRank = new double[4];
            
            setNumberOfCards(reducedHand, trumpSuit, numberOfCards);
            setAverageRank(reducedHand, trumpSuit, numberOfCards, averageRank);
        	
        	boolean isRemoved;
        	do
        	{
        		isRemoved = removeAllCardsOfOneSuit(clonedHand, reducedHand, numberOfCards, trumpSuit, averageRank, opponentSuit);
        	}while(isRemoved && aCardListRemoved.size() != CARDS_TO_REMOVE);
        }
        
        
    	// finally remove lowest cards until the size of widow reaches 6
        while (aCardListRemoved.size() != CARDS_TO_REMOVE)
        {
        	Card toRemove = clonedHand.selectLowest(trumpSuit);
        	clonedHand.remove(toRemove);
        	aCardListRemoved.add(toRemove);
        }
        return aCardListRemoved;
	}
	
	// get a hand of all non-trump cards whose ranks are lower than HIGH_RANK_CARDS
	// We will only discard cards which are in this list when calling removeAllCardsOfOneSuit
	private Hand getLowRankCards(Hand pClonedHand, Suit pTrump)
	{
		Hand lowRankCards = new Hand();
		for(Card card: pClonedHand)
		{
			if (!card.isJoker() && card.getEffectiveSuit(pTrump)!= pTrump && rankToInt(card.getRank()) < HIGH_RANK_CARD)
			{
				lowRankCards.add(card);
			}
		}
		return lowRankCards;
	}
	
	//Calculate the number of low rank cards (i.e. < HIGH_RANK_CARDS) of each suit
	//This method is only called once
	private void setNumberOfCards(Hand pReducedHand, Suit pTrump, int[] pNumberOfCards)
	{
		// 0 : Spades 1: Clubs 2: Diamonds 3: Hearts	
		pNumberOfCards[0] = pReducedHand.numberOfCards(Suit.SPADES, pTrump);
		pNumberOfCards[1] = pReducedHand.numberOfCards(Suit.CLUBS, pTrump);
		pNumberOfCards[2] = pReducedHand.numberOfCards(Suit.DIAMONDS, pTrump);
		pNumberOfCards[3] = pReducedHand.numberOfCards(Suit.HEARTS, pTrump);
				
		// set the numberofCards of trump suit to be incomparable
		// so that we won't remove any cards of trump suit
		if (pTrump!= null)
		{
			pNumberOfCards[pTrump.ordinal()] = NUM_INCOMPARABLE;
		}
	}
	
	
	//Set the average rank of cards of each suit
	// average rank = sum of ranks of all cards of one suit/ number of cards of this suit
	private void setAverageRank(Hand pReducedHand, Suit pTrump, int[] pNumberOfCards, double[] pAverageRank)
	{
		// 0 : Spades 1: Clubs 2: Diamonds 3: Hearts	
		// calculate the sum of ranks of all cards of one suit
		for (Card card: pReducedHand)
	    {
	    	if (card.getEffectiveSuit(pTrump).equals(Suit.SPADES))
	    	{
	    		pAverageRank[0] = pAverageRank[0] + rankToInt(card.getRank());
	    	}
	    	else if (card.getEffectiveSuit(pTrump).equals(Suit.CLUBS))
	    	{
	    		pAverageRank[1] = pAverageRank[1] + rankToInt(card.getRank());
	    	}
	    	else if (card.getEffectiveSuit(pTrump).equals(Suit.DIAMONDS))
	    	{
	    		pAverageRank[2] = pAverageRank[2] + rankToInt(card.getRank());
	    	}
	    	else if (card.getEffectiveSuit(pTrump).equals(Suit.HEARTS))
	    	{
	    		pAverageRank[3] = pAverageRank[3] + rankToInt(card.getRank());
	    	}
	    }
		for (int i = 0; i<4; i++)
		{
			pAverageRank[i] = pAverageRank[i]/pNumberOfCards[i];
			//System.out.println("AverageRank of " + i + " is: " + pAverageRank[i]);
		}			
	}
	
	
	// Find which suit has the least number of cards first
	// If # of cards of this suit is less than FEW_ENOUGH_CARDS,
    // then remove all cards of this suit
	// the method returns true if we do remove all cards of one suit
	private boolean removeAllCardsOfOneSuit(Hand pClonedHand, Hand pReducedHand, int[] pNumberOfCards, 
			Suit pTrump, double[] pAverageRank, Suit pOpponentSuit)
	{
		// get the suit that has the least number of cards
		int minIndex = 0;
		int minNumCards = NUM_INCOMPARABLE;
		double minAverageRank = HIGH_AVERAGE_RANK; //15
		int opponentSuitIndex = -1;
		if (pOpponentSuit!=null)
		{
			opponentSuitIndex = pOpponentSuit.ordinal();
		}
		
		for (int i = 0; i<4; i++)
		{
			if (pNumberOfCards[i]<minNumCards)
			{
				minIndex = i;
				minNumCards = pNumberOfCards[i];
				minAverageRank = pAverageRank [i];
			}
				// if two suits have the same number of cards,
				// we will discard the suit of lowest average rank
			else if (pNumberOfCards[i] <= minNumCards+EPSILON )
			{
				if (i==opponentSuitIndex || pAverageRank[i] < minAverageRank)
				{
					minIndex = i;
					minNumCards = pNumberOfCards[i];
					minAverageRank = pAverageRank [i];	
				}
			}
		}
		
		//remove all cards of the suit if the number of cards of this suit is small enough
		if (minNumCards <= FEW_ENOUGH_CARDS && minNumCards <= CARDS_TO_REMOVE - aCardListRemoved.size())
		{
			Suit suitToDiscard = Suit.SPADES;
			suitToDiscard = Suit.values()[minIndex];
			
			// store all cards that we are going to remove in a list
			CardList cardToRemoveList = new CardList();
		    for (Card card: pReducedHand)
		    {
		    	if (card.getEffectiveSuit(pTrump).equals(suitToDiscard))
		    	{
		    		cardToRemoveList.add(card);
		    	}
		    }
		    
		    for (Card card: cardToRemoveList)
		    {
		    	pClonedHand.remove(card);
		    	pReducedHand.remove(card);
	        	aCardListRemoved.add(card);
		    }
        	
        	//set the number of cards of the suit discarded to be incomparable
        	pNumberOfCards[minIndex] = NUM_INCOMPARABLE;
        	return true;
		}
    	return false;
    }

	// I can't find any converter method, so I wrote one here.
	private int rankToInt(Card.Rank pRank)
	{
		return pRank.ordinal()+3;
	}
}
