package comp303.fivehundred.model;

import comp303.fivehundred.util.Card.Suit;

/**
 * @author (Sherry) Shanshan Ruan 260471837
 * Represents a bid or a contract for a player. Immutable.
 */
public class Bid implements Comparable<Bid>
{
	private static final int MIN_TRICK_BET = 6;
	private static final int MAX_TRICK_BET = 10;
	private static final int BID_TYPE_INTERVAL = 5;
	private static final int BID_MIN = 0;
	private static final int BID_MAX = 24;
	private static final Suit[] BID_TYPE = { Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS };
	private static final int[] BID_TYPE_VALUE = {40 , 60 , 80 , 100 , 120};
	private static final int NO_TRUMP_VALUE = 120;
	private static final int TRICKS_VALUE = 100;
	private static final int HASH_MULTIPLIER = 31;
	private static final int HASH_TRUE = 1231;
	private static final int HASH_FALSE = 1237;
	
	private int aTricks;
	private Suit aSuit;
	private boolean aIsPass;
	private boolean aIsNoTrump;
	/**
	 * Constructs a new standard bid (not a pass) in a trump.
	 * @param pTricks Number of tricks bid. Must be between 6 and 10 inclusive.
	 * @param pSuit Suit bid. 
	 * @pre pTricks >= 6 && pTricks <= 10
	 */
	public Bid(int pTricks, Suit pSuit)
	{
		assert pTricks >= MIN_TRICK_BET && pTricks <=MAX_TRICK_BET;
		
		aTricks = pTricks;
		aSuit = pSuit;
		aIsPass = false;
		aIsNoTrump = pSuit==null;
	}
	
	/**
	 * Constructs a new passing bid.
	 */
	public Bid()
	{
		aIsPass = true;
	}
	
	/**
	 * Creates a bid from an index value between 0 and 24 representing all possible
	 * bids in order of strength.
	 * @param pIndex 0 is the weakest bid (6 spades), 24 is the highest (10 no trump),
	 * and everything in between.
	 * @pre pIndex >= 0 && pIndex <= 24
	 */
	public Bid(int pIndex)
	{
		assert pIndex >= BID_MIN && pIndex <=BID_MAX;
		
		aIsPass = false;
		aTricks = pIndex / BID_TYPE_INTERVAL + MIN_TRICK_BET;
		int index = pIndex % BID_TYPE_INTERVAL;
		if (index < BID_TYPE.length) 
		{
			aSuit = BID_TYPE[index];
			aIsNoTrump = false;
		}
		else
		{
			aIsNoTrump = true;
		}
	}
	
	/**
	 * @return The suit the bid is in, or null if it is in no-trump.
	 * @throws ModelException if the bid is a pass.
	 */
	public Suit getSuit()
	{
		if (aIsPass) 
		{
			throw new ModelException("The bid is a pass");
		}
		if (aIsNoTrump) 
		{
			return null;
		}
		return aSuit;
	}
	
	/**
	 * @return The number of tricks bid.
	 * @throws ModelException if the bid is a pass.
	 */
	public int getTricksBid()
	{
		if (aIsPass) 
		{
			throw new ModelException("The bid is a pass");
		}
		return aTricks;
	}
	
	/**
	 * @return True if this is a passing bid.
	 */
	public boolean isPass()
	{
		return aIsPass;
	}
	
	/**
	 * @return True if the bid is in no trump.
	 */
	public boolean isNoTrump()
	{
		return aIsNoTrump;
	}

	@Override
	public int compareTo(Bid pBid)
	{
		if (isPass() && pBid.isPass())
		{
			return 0;
		}
		else if (isPass() && !pBid.isPass())
		{
			return -1;
		}
		else if (!isPass() && pBid.isPass())
		{
			return 1;
		}
		return toIndex() - pBid.toIndex();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		if (aIsPass)
		{
			return "Pass";
		}
		else if (aIsNoTrump)
		{
			return aTricks + " No Trump";
		}
		else
		{
			String suit = aSuit.toString().charAt(0) + aSuit.toString().substring(1).toLowerCase();
			return aTricks +" "+ suit;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pBid)
	{
		if (pBid == null || pBid.getClass() != this.getClass()	)
		{
			return false;
		}
		Bid b2 = (Bid) pBid;
		if (this.compareTo(b2)==0)
		{
			return true;
		}
		return false;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		int result = 1;
		if(aIsNoTrump)
		{
			result = HASH_MULTIPLIER * result + HASH_TRUE;
		}
		else
		{
			result = HASH_MULTIPLIER * result + HASH_FALSE;
		}
		if(aIsPass)
		{
			result = HASH_MULTIPLIER * result + HASH_TRUE;
		}
		else
		{
			result = HASH_MULTIPLIER * result + HASH_FALSE;
		}
		if(aSuit == null)
		{
			result = HASH_MULTIPLIER * result + 0;
		}
		else
		{
			result = HASH_MULTIPLIER * result + aSuit.hashCode();
		}
		result = HASH_MULTIPLIER * result + aTricks;
		return result;
	}

	/**
	 * Converts this bid to an index in the 0-24 range.
	 * @return 0 for a bid of 6 spades, 24 for a bid of 10 no-trump,
	 * and everything in between.
	 * @throws ModelException if this is a passing bid.
	 */
	public int toIndex()
	{
		if (aIsPass) 
		{
			throw new ModelException("This is a passing bid");
		}
		if (aIsNoTrump)
		{
			return (aTricks -MIN_TRICK_BET) * BID_TYPE_INTERVAL + BID_TYPE.length; 
		}
		for (int i = 0; i < BID_TYPE.length; i++)
		{
			if (BID_TYPE[i].equals(aSuit))
			{
				return (aTricks -MIN_TRICK_BET)* BID_TYPE_INTERVAL + i;
			}
		}
		throw new ModelException("Bid index error");
	}
	
	/**
	 * Returns the highest bid in pBids. If they are all passing
	 * bids, returns pass.
	 * @param pBids The bids to compare.
	 * @return The highest bid.
	 */
	public static Bid max(Bid[] pBids)
	{
		Bid highestBid = new Bid();
		for (int i = 0; i < pBids.length; i++)
		{
			if (pBids[i].compareTo(highestBid) > 0)
			{
				highestBid = pBids[i];
			}
		}
		return highestBid;
	}
	
	/**
	 * @param pBids The bids to compare
	 * @return The index of the highest bid
	 */
	public static int indexMax(Bid[] pBids)
	{
		Bid highestBid = Bid.max(pBids);
		for (int i = 0; i < pBids.length; i++)
		{
			if (pBids[i].equals(highestBid))
			{
				return i;
			}
		}
		throw new ModelException("Bid indexMax error");
	}
	
	/**
	 * @return The score associated with this bid.
	 * @throws ModelException if the bid is a pass.
	 */
	public int getScore()
	{
		if (aIsPass) 
		{
			throw new ModelException("This is a passing bid");
		}
		if (aIsNoTrump)
		{
			return (aTricks -MIN_TRICK_BET) * TRICKS_VALUE + NO_TRUMP_VALUE; 
		}
		for (int i = 0; i < BID_TYPE.length; i++)
		{
			if (BID_TYPE[i].equals(aSuit))
			{
				return (aTricks -MIN_TRICK_BET) * TRICKS_VALUE + BID_TYPE_VALUE[i];
			}
		}
		throw new ModelException("Bid score error");
	}
}