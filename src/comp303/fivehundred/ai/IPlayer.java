package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 *  @author (Sherry) Shanshan Ruan 260471837
 */
public interface IPlayer
{
	/**
	 * The player makes a bid.
	 * @param pPreviousBids
	 * 		A list of the previous players' bids
	 * @param pHand
	 * 		The hand given to this player
	 * @return
	 * 		 A chosen bid
	 */
	Bid selectBid(Bid[] pPreviousBids, Hand pHand);

	/**
	 * The player discards cards.
	 * @param pBids
	 * 		Everyone's bids
	 * @param pIndex
	 * 		The player's seat number
	 * @param pHand
	 * 		The hand given to this player
	 * @return 
	 * 		Cards that the player will discard
	 */
	CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand);

	/**
	 * The player plays a trick.
	 * @param pTrick
	 * 		The cards played in this trick
	 * @param pHand
	 * 		The player's hand
	 * @return 
	 * 		A card that the player will play
	 */
	Card play(Trick pTrick, Hand pHand);
	
	/**
	 * Get the name of the player.
	 * @return
	 * 		The name of the player
	 */
	String getName();
}
