package comp303.fivehundred.ai.advanced;

import comp303.fivehundred.ai.IPlayingStrategy;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Stephanie Pataracchia 260407002 If leading, picks the card with the highest Lead rating If following, choose
 *         the lowest card that can follow suit and win. If no card can follow suit and win, picks the lowest card that
 *         can follow suit. If no card can follow suit, picks the lowest trump card that can win. If there are no trump
 *         card or the trump cards can't win (because the trick was already trumped), then picks the lowest card. If a
 *         joker was led, dump the lowest card unless it can be beaten with the high joker according to the rules of the
 *         game.
 */

public class AdvancedPlayingStrategy implements IPlayingStrategy

{

	private static final int UPPER_BOUND = 11;

	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		assert pTrick != null && pHand != null;
		assert pTrick.size() >= 0 && pHand.size() > 0;

		Suit pSuitLed = pTrick.getSuitLed();
		Suit pTrump = pTrick.getTrumpSuit();
		Hand clone = pHand.clone();
		if (pTrick.size() == 0)
		{
			Hand nonJoTrumpHand;
			// contract has trump
			if (pTrick.getTrumpSuit() != null)
			{
				nonJoTrumpHand = toHand(toHand(clone.getNonJokers()).getTrumpCards(pTrick.getTrumpSuit()));

				if (nonJoTrumpHand.size() == 0)
				{

					// if only trump
					if (clone.getTrumpCards(pTrick.getTrumpSuit()).size() == clone.size())
					{
						return clone.selectHighest(pTrick.getTrumpSuit());
					}
					else
					{
						return suitOfLeast(toHand(clone.getNonJokers()), pTrick.getTrumpSuit()).selectHighest(
								pTrick.getTrumpSuit());
					}
				}

				// if you have trump, play your highest trump
				else
				{

					return nonJoTrumpHand.selectHighest(pTrick.getTrumpSuit());
				}

			}
			// contract is no trump
			else
			{

				nonJoTrumpHand = toHand(toHand(clone.getNonJokers()));
			}

			// if you have no trump (other than jokers), play highest of the suit you have the least of
			if (nonJoTrumpHand.size() == 0)
			{
				// for no trump
				if (pTrick.getTrumpSuit() == null)
				{
					// return highest joker
					return clone.selectHighest(pTrick.getTrumpSuit());
				}
				// if only trump
				if (clone.getTrumpCards(pTrick.getTrumpSuit()).size() == clone.size())
				{
					return clone.selectHighest(pTrick.getTrumpSuit());
				}
				else
				{
					return suitOfLeast(clone, pTrick.getTrumpSuit()).selectHighest(pTrick.getTrumpSuit());
				}
			}

			// if you have trump, play your highest trump
			else
			{
				// for no trump
				if (pTrick.getTrumpSuit() == null)
				{
					//************
					// return highest joker
					return suitOfLeast(nonJoTrumpHand, pTrick.getTrumpSuit()).selectHighest(pTrick.getTrumpSuit());
				}

				return nonJoTrumpHand.selectHighest(pTrick.getTrumpSuit());
			}
		}
		else
		{
			Hand pFollowable = toHand(pHand.playableCards(pSuitLed, pTrump));
			Hand winningCards = couldWin(pTrick, pFollowable);
			if (pTrick.size() == 3)
			{
				if (pTrick.winnerIndex() == 1)
				{
					return pFollowable.selectLowest(pTrick.getTrumpSuit());
				}
			}

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

	// returns a hand of cards
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

	// returns a single suited hand of the suit that pHand has the least
	// doesn't return trump unless pHand is all trump
	private Hand suitOfLeast(Hand pHand, Suit pTrump)
	{

		int[] suitCount = new int[4];
		Hand[] suitArray = new Hand[4];
		Hand result = pHand.clone();
		Hand clone = pHand.clone();

		for (int i = 0; i < 4; i++)
		{
			suitCount[i] = 0;
			suitArray[i] = new Hand();
		}
		if (pTrump == null)
		{
			for (Card card : clone)
			{

				Suit ef = card.getEffectiveSuit(pTrump);
				if (ef == null)
				{
					return result;
				}

				// create a hand for each suit
				suitCount[ef.ordinal()]++;
				suitArray[ef.ordinal()].add(card);
			}


			int resultIdx = 0;
			int lowestSoFar = UPPER_BOUND;
			int i;
			for (i = 0; i < 4; i++)
			{
				if ((suitCount[i] < lowestSoFar) && (suitCount[i] > 0))
				{
					lowestSoFar = suitCount[i];
					resultIdx = i;
				}
			}
				result = suitArray[resultIdx];

		}
		else
		{
			for (Card card : clone)
			{
				Suit ef = card.getEffectiveSuit(pTrump);
				if (ef == null)
				{
					return result;
				}

				// create a hand for each suit
				suitCount[ef.ordinal()]++;
				suitArray[ef.ordinal()].add(card);
			}
			int trumpIdx = pTrump.ordinal();
			int resultIdx = 0;
			int lowestSoFar = UPPER_BOUND;
			int i;
			for (i = 0; i < 4; i++)
			{
				if ((suitCount[i] < lowestSoFar) && (suitCount[i] > 0))
				{
					lowestSoFar = suitCount[i];
					resultIdx = i;
				}
			}
			if (resultIdx != trumpIdx)
			{
				result = suitArray[resultIdx];
			}
			else
			{
				result = suitArray[trumpIdx];
			}
		}

		return result;
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