package comp303.fivehundred.engine;

import comp303.fivehundred.ai.IPlayer;
import comp303.fivehundred.ai.human.HumanPlayer;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Deck;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Stephanie Pataracchia 260407002
 */
public class GameEngine 
{
	private static final int NB_TEAMS = 2;
	private static final int NB_PLAYERS = 4;
	private static final int WIDOW_SIZE = 6;
	private static final int PER_TRICK_PTS = 10;
	private static final int HAND_SIZE = 10;
	private static final int SLAM = 250;
	private static final int END_SCORE = 500;

	private ArrayList<IObserver> aObsList;
	private IPlayer[] aPlayers;
	private int aLatestPlayer;
	private int aDealer;
	private int aFirstBidder;
	private int aContractor;
	private int aTrickLeader;
	private int[] aTeamTricks;
	private int[] aRoundScores;
	private Bid aWinningBid;
	private Bid[] aBids;
	private int[] aScores;
	
	private Hand[] aHands;
	private Hand aWidow;
	private Deck aDeck;
	private Card aLatestCard;
	private Trick aTrick;
	private boolean aCardsDealt;
	private boolean aGameEnded;
	
	private Random aRand;

	/**
	 * Messages that the Subject can send to IObservers.
	 */
	public enum Message 
	{NEWGAME, DEALT, BID, PASS, WIDOW, EXCHANGE, TRICK, SCORE, GAMEOVER, ENDROUND};

	/**
	 * Creates a game engine which can play games.
	 * 
	 * @param pPlayers
	 *            The behaviour of each player (random robot, basic robot, human...)
	 */
	public GameEngine(IPlayer[] pPlayers)
	{
		aDeck = new Deck();
		aPlayers = pPlayers;
		aObsList = new ArrayList<IObserver>();
		aRand = new Random();
		aScores = new int[NB_TEAMS];
		aTeamTricks = new int[NB_TEAMS];
		aBids = new Bid[NB_PLAYERS];
	}

	/**
	 * Adds an observer which will wait for updates.
	 * @param pObs
	 * 		The observer
	 */
	public void addObserver(IObserver pObs)
	{
		aObsList.add(pObs);
	}
	
	/**
	 * Removes an observer from the GameEngine.
	 * @param pObs
	 * 		The observer
	 */
	public void removeObserver(IObserver pObs)
	{
		aObsList.remove(pObs);
	}
	
	/**
	 * Notify all observers.
	 * @param pArg
	 * 		A message for the observers
	 */
	public void notify(Message pArg)
	{
		for (IObserver obs : aObsList)
		{
			obs.update(pArg);
		}
	}
	
	/**
	 * Change the players playing.
	 * @param pPlayers
	 * 		the players to be changed
	 */
	public void changePlayers(IPlayer[] pPlayers)
	{
		aPlayers = pPlayers;
	}

	/**
	 * Start a new game.
	 */
	public void newGame()
	{
		aCardsDealt = false;
		aGameEnded = false;
		aDealer = Math.abs(aRand.nextInt())%NB_PLAYERS;
		aFirstBidder = (aDealer + 1) % NB_PLAYERS;
		aScores = new int[NB_TEAMS];
		aTeamTricks = new int[NB_TEAMS];
		
		notify(Message.NEWGAME);
	}

	/**
	 * Creates 4 new hands, 1 for each player, and the remaining cards go to the widow.
	 * The dealer deals to the player on his left first.
	 * 
	 * @post deck is empty
	 */
	public void deal()
	{
		resetDeal();
		
		for (int i = 0; i < WIDOW_SIZE; i++)
		{
			aWidow.add(aDeck.draw());
		}

		for (int i = 0; i < NB_PLAYERS * HAND_SIZE; i++)
		{
			aLatestPlayer = (aDealer + i) % NB_PLAYERS;
			aHands[aLatestPlayer].add(aDeck.draw());
			if (aHands[aLatestPlayer].size()==HAND_SIZE)
			{
				notify(Message.DEALT);
			}
		}
		
		assert aDeck.size() == 0 && aWidow.size() == WIDOW_SIZE;

	}

	/**
	 * Executes one round of bidding.
	 */
	public void bid()
	{
		bidBeforeHuman();
		bidAfterHuman();
	}
	
	/**
	 * Each player bidding before the human player places a bid.
	 */
	public void bidBeforeHuman()
	{
		aLatestPlayer = aFirstBidder;
		while(getBidNbPlaced()!=NB_PLAYERS)
		{
			if (humanExists() && aLatestPlayer==0)
			{
				break;
			}
			singleBid();
			alternatePlayer();
		}
	}
	
	/**
	 * The human (if any) and players bidding after him place their bids.
	 */
	public void bidAfterHuman()
	{
		while(getBidNbPlaced()!=NB_PLAYERS)
		{
			singleBid();
			alternatePlayer();
		}
		aTrickLeader = aContractor;
	}

	/**
	 * @return true if everyone passes, false if at least 1 bid was made
	 */
	public boolean allPasses()
	{
		if (aCardsDealt)
		{
			boolean isAllPass = Bid.max(aBids).isPass();
			if (isAllPass)
			{
				notify(Message.PASS);
			}
			return Bid.max(aBids).isPass();
		}
		else
		{
			return true;
		}
	}

	/**
	 * The player with the winning contract will take the widow and discard 6 cards.
	 */
	public void exchange()
	{
		addWidowToHand();
		exchangeCards();
	}
	
	/**
	 * Adds the widow to the hand of the contractor.
	 */
	public void addWidowToHand()
	{
		for (Card fromWidow : aWidow)
		{
			aHands[aContractor].add(fromWidow);
		}
		notify(Message.WIDOW);
	}
	
	/**
	 * The contractor exchanges cards, and puts the discarded cards in the widow.
	 */
	public void exchangeCards()
	{
		CardList toBeRemoved = aPlayers[aContractor].selectCardsToDiscard(aBids, aContractor, aHands[aContractor]);
		aWidow = new Hand();

		for (Card card : toBeRemoved)
		{
			aHands[aContractor].remove(card);
			aWidow.add(card);
		}
		
		notify(Message.EXCHANGE);
	}

	/**
	 * Each player plays a card for this trick.
	 */
	public void playTrick()
	{
		playBeforeHuman();
		playAfterHuman();
	}
	
	/**
	 * Players playing before the human select a card to play.
	 */
	public void playBeforeHuman()
	{
		aLatestPlayer = aTrickLeader;
		aTrick = new Trick(Bid.max(aBids));
		while(aTrick.size()!=NB_PLAYERS)
		{
			if (humanExists() && aLatestPlayer==0)
			{
				break;
			}
			singleTrick();
			alternatePlayer();
		}
	}

	/**
	 * The Human (if any) and players playing after him select a card to play.
	 */
	public void playAfterHuman()
	{
		while(aTrick.size()!=NB_PLAYERS)
		{
			singleTrick();
			alternatePlayer();
		}
	}

	/**
	 * After tricks are played, computes the score to add to each team.
	 */
	public void computeScore()
	{
		int leadingTeam = aContractor % NB_TEAMS;
		int nonLeadingTeam = (aContractor + 1) % NB_TEAMS;
		int scoreLeading;
		int scoreNonLeading;
		aRoundScores = new int[NB_TEAMS];
		if (aTeamTricks[leadingTeam] == HAND_SIZE)
		{
			
			scoreLeading = Math.max(aWinningBid.getScore(), SLAM);
			scoreNonLeading = 0;

			if (aTeamTricks[nonLeadingTeam] != 0)
			{
				throw new GameException("Trick count invalid");
			}
		}
		else if (aTeamTricks[leadingTeam] >= aWinningBid.getTricksBid())
		{
			scoreLeading = aWinningBid.getScore();
		}
		else
		{
			scoreLeading = -aWinningBid.getScore();
		}
		scoreNonLeading = PER_TRICK_PTS * aTeamTricks[nonLeadingTeam];
		aScores[leadingTeam] += scoreLeading;
		aScores[nonLeadingTeam] += scoreNonLeading;
		aRoundScores[leadingTeam] = scoreLeading;
		aRoundScores[nonLeadingTeam] = scoreNonLeading;
		
		notify(Message.SCORE);
	}
	
	/**
	 * @return Whether a player has less than -500 points, or more than 500 points after winning a contract.
	 */
	public boolean isGameOver()
	{
		if (aCardsDealt)
		{
			aCardsDealt = false;
			int leadingTeam = aContractor % NB_TEAMS;
			boolean contractWon = aTeamTricks[leadingTeam] >= aWinningBid.getTricksBid();
			aGameEnded = (!contractWon && aScores[leadingTeam] <= -END_SCORE) || (contractWon && aScores[leadingTeam] >= END_SCORE);
			notify(Message.ENDROUND);
			if (aGameEnded)
			{
				notify(Message.GAMEOVER);
			}
			return aGameEnded;
		}
		else
		{
			return false;
		}
	}

	private void resetDeal()
	{
		aCardsDealt = true;
		aDealer = (aDealer+1)%NB_PLAYERS;
		aFirstBidder = (aDealer + 1) % NB_PLAYERS;
		aDeck.shuffle();
		aHands = new Hand[NB_PLAYERS];
		aBids = new Bid[NB_PLAYERS];
		aTeamTricks = new int[NB_PLAYERS / NB_TEAMS];
		aWidow = new Hand();
		for (int i = 0; i < NB_PLAYERS; i++)
		{
			aHands[i] = new Hand();
		}
	}
	
	/**
	 * @return the Trick being played
	 */
	public Trick getTrick()
	{
		return aTrick.clone();
	}
	
	/**
	 * @param pIndex
	 * 		The index of the score you want to access
	 * @return a score
	 */
	public int getScore(int pIndex)
	{
		return aScores[pIndex];
	}
	
	/**
	 * @param pIndex
	 * 		The team index
	 * @return the score obtained this round by a team
	 */
	public int getRoundScore(int pIndex)
	{
		return aRoundScores[pIndex];
	}
	
	/**
	 * @param pIndex
	 * 		The index of the bid you want to access
	 * @return a Bid
	 */
	public Bid getBid(int pIndex)
	{
		return aBids[pIndex];
	}
	
	/**
	 * @return the winning bid for this deal
	 */
	public Bid getBestBid()
	{
		if (inOrderBids(aBids).length==0)
		{
			return null;
		}
		return Bid.max(inOrderBids(aBids));
	}
	
	/**
	 * @return the number of bids already entered.
	 */
	public int getBidNbPlaced()
	{
		return inOrderBids(aBids).length;
	}
	
	/**
	 * @return the number of tricks already played.
	 */
	public int getTrickNbPlayed()
	{
		return aTeamTricks[0] + aTeamTricks[1];
	}

	/**
	 * @param pIndex
	 * 		The index of the hand you want to access
	 * @return a Hand
	 */
	public Hand getHand(int pIndex)
	{
		return aHands[pIndex].clone();
	}

	/**
	 * @return the hand of the widow
	 */
	public Hand getWidow()
	{
		return aWidow.clone();
	}
	
	/**
	 * @return the latest player who's data was changed
	 */
	public int getLatestPlayerIndex()
	{
		return aLatestPlayer;
	}
	
	/**
	 * @return the latest Card that was played
	 */
	public Card getLatestCard()
	{
		return aLatestCard;
	}

	/**
	 * @param pIndex
	 * 		The index of the player you want to access
	 * @return a IPlayer
	 */
	public IPlayer getPlayer(int pIndex)
	{
		return aPlayers[pIndex];
	}
	
	/**
	 * @return the index of the Dealer player
	 */
	public int getDealerIndex()
	{
		return aDealer;
	}

	/**
	 * @return the index of the Contractor player
	 */
	public int getContractorIndex()
	{
		return aContractor;
	}

	/**
	 * @return the index of the Trick Leader
	 */
	public int getTrickLeaderIndex()
	{
		return aTrickLeader;
	}
	
	/**
	 * @return whether the Game ended, or just the round.
	 */
	public boolean hasGameEnded()
	{
		return aGameEnded;
	}

	/**
	 * @param pTeamIndex
	 * 		The index of a team: 0 or 1
	 * @return the number of tricks obtained by team 0 or 1
	 */
	public int getTeamTricks(int pTeamIndex)
	{
		return aTeamTricks[pTeamIndex];
	}
	
	private Bid[] inOrderBids(Bid[] pBids)
	{
		int nonNullEntries = 0;
		for (Bid each : pBids)
		{
			if (each != null)
			{
				nonNullEntries++;
			}
		}

		Bid[] inOrderBids = new Bid[nonNullEntries];

		for (int i = 0; i < inOrderBids.length; i++)
		{
			inOrderBids[i] = pBids[(aFirstBidder + i)%4];
		}
		return inOrderBids;
	}
	
	private boolean humanExists()
	{
		for (int i = 0; i<NB_PLAYERS; i++)
		{
			if (aPlayers[i].getClass() == HumanPlayer.class)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private void alternatePlayer()
	{
		aLatestPlayer = (aLatestPlayer + 1) % NB_PLAYERS;
	}
	
	private void singleBid()
	{
		aBids[aLatestPlayer] = aPlayers[aLatestPlayer].selectBid(inOrderBids(aBids), aHands[aLatestPlayer]);
		if (getBidNbPlaced()==NB_PLAYERS)
		{
			aWinningBid = Bid.max(aBids);
			aContractor = Bid.indexMax(aBids);
		}
		notify(Message.BID);
	}
	
	private void singleTrick()
	{
		Card played = aPlayers[aLatestPlayer].play(aTrick, aHands[aLatestPlayer]);
		aTrick.add(played);
		aLatestCard = played;
		if (aTrick.size()==NB_PLAYERS)
		{
			aTrickLeader = (aLatestPlayer + aTrick.winnerIndex() + 1) % NB_PLAYERS;
			aTeamTricks[aTrickLeader % NB_TEAMS]++;
		}
		notify(Message.TRICK);
		aHands[aLatestPlayer].remove(played);
	}
}
