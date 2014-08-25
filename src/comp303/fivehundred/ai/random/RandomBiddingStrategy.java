package comp303.fivehundred.ai.random;

import comp303.fivehundred.ai.IBiddingStrategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;

import java.util.Random;

/**
 * @author (Sherry) Shanshan Ruan 260471837
 * Enters a valid but random bid. Passes a configurable number of times.
 * If the robot does not pass, it uses a universal probability
 * distribution across all bids permitted.
 */
public class RandomBiddingStrategy implements IBiddingStrategy
{
	private static final int FULL_POSSIBILITY = 100;
	private static final int HALF_POSSIBILITY = 50;
	private static final int BID_TYPE_LENGTH = 25;
	private static final int HAND_SIZE = 10;
	private static final int BIDS_LENGTH = 4;
	private static final Random RANDOM_GENERATOR = new Random();
	
	private int aPassFrequency;
	
	/**
	 * Builds a robot that passes 50% of the time and bids randomly otherwise.
	 */
	public RandomBiddingStrategy()
	{ 
		aPassFrequency = HALF_POSSIBILITY;
	}
	
	/** 
	 * Builds a robot that passes the specified percentage number of the time.
	 * @param pPassFrequency A percentage point (e.g., 50 for 50%) of the time the robot 
	 * will pass. Must be between 0 and 100 inclusive. 
	 * Whether the robot passes is determined at random.
	 */
	public RandomBiddingStrategy(int pPassFrequency)
	{
		aPassFrequency = pPassFrequency;
	}
	
	@Override
	public Bid selectBid(Bid[] pPreviousBids, Hand pHand)
	{
		assert pPreviousBids.length < BIDS_LENGTH;
		assert pHand.size() == HAND_SIZE;
		
		if (RANDOM_GENERATOR.nextInt(FULL_POSSIBILITY) < aPassFrequency)
		{
			return new Bid();
		}
		if (pPreviousBids==null || pPreviousBids.length == 0) // if the previous bid does not exist, then I can bid any suit and number
		{
			return new Bid(RANDOM_GENERATOR.nextInt(BID_TYPE_LENGTH));
		}
		Bid highestBid = Bid.max(pPreviousBids);
		if (highestBid.isPass()) // if the highest previous bid is a pass, then I can bid any suit and number
		{
			return new Bid(RANDOM_GENERATOR.nextInt(BID_TYPE_LENGTH));
		}
		int n = highestBid.toIndex();
		if (n == BID_TYPE_LENGTH - 1 )// if the previous bid is ten no trump, then I cannot bid anything
		{
			return new Bid();
		}
		else
		{
			int pIndex = n + RANDOM_GENERATOR.nextInt(BID_TYPE_LENGTH - 1 - n) + 1;
			return new Bid(pIndex);
		}
	}
}
