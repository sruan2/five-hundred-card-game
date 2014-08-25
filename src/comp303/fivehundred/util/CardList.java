package comp303.fivehundred.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * @author Ioannis Fytilis 260482744
 * A mutable list of cards. Does not support duplicates.
 * The cards are maintained in the order added.
 */
public class CardList implements Iterable<Card>, Cloneable
{
	
	private ArrayList<Card> aList;
	
	
	/**
	 * Create a new list of cards.
	 *
	 */
	public CardList()
	{
		aList = new ArrayList<Card>();
	}
	
	/**
	 * Adds a card if it is not
	 * already in the list. Has no effect if the card
	 * is already in the list.
	 * @param pCard The card to add.
	 * @pre pCard != null
	 */
	public void add(Card pCard)
	{
		assert pCard != null;
		if (!aList.contains(pCard))
		{
			aList.add(pCard);
		}

	}
	
	/**
	 * @return The number of cards in the list.
	 */
	public int size()
	{
		return aList.size();
	}
	
	/**
	 * @return The first card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException if the list is empty.
	 */
	public Card getFirst()
	{
		if (aList.size()==0)
		{
			throw new GameUtilException("Empty CardList");
		}
		return aList.get(0);
	}
	
	/**
	 * @return The last card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException If the list is empty.
	 */
	public Card getLast()
	{
		if (aList.size()==0)
		{
			throw new GameUtilException("Empty CardList");
		}
		return aList.get(size()-1);
	}
	
	/**
	 * Removes a card from the list. Has no effect if the card is
	 * not in the list.
	 * @param pCard The card to remove. 
	 * @pre pCard != null;
	 */
	public void remove(Card pCard)
	{
		assert pCard != null;
		aList.remove(pCard);
	}
	
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc}
	 */
	@Override
	public CardList clone()
	{
		CardList cloned;
        try
        {
            cloned = (CardList) super.clone();
            cloned.aList = new ArrayList<Card>();
        }
        catch (CloneNotSupportedException e)
        {
            throw new Error();
        }

		for (Card card : this)
		{
			if (card.isJoker())
			{
				cloned.add(new Card(card.getJokerValue()));
			}
			else
			{
				cloned.add(new Card(card.getRank(), card.getSuit()));
			}
		}

        return cloned;	
	}
	
	/**
	 * @see java.lang.Iterable#iterator()
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Card> iterator() 
	{
		
		Iterator<Card> pIterator = new Iterator<Card>() {

			private int aCurrent = 0;

			@Override
			public boolean hasNext() 
			{
				return aCurrent < size() && aList.get(aCurrent) != null;
			}

			@Override
			public Card next() 
			{
				return aList.get(aCurrent++);
			}

			@Override
			public void remove() 
			{
				// Implementation not needed -- just need to override it
			}
		};
		
		return pIterator;
	}

	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */
	public String toString()
	{
		if (size()==0)
		{
			return "<>";
		}
		String pPrint = "<";
		for (Card card : this)
		{
			pPrint += card.toShortString() + ",";
		}
		
		pPrint = pPrint.substring(0, pPrint.length()-1) + ">";
		
		return pPrint;
	}
	
	/**
	 * @pre aCards.size() > 0
	 * @return A randomly-chosen card from the set. 
	 */
	public Card random()
	{
		assert size() > 0;
		CardList pClone = clone();
		
		Collections.shuffle(pClone.aList);
		return pClone.getFirst();
	}
	
	/**
	 * Returns another CardList, sorted as desired. This
	 * method has no side effects.
	 * @param pComparator Defines the sorting order.
	 * @return the sorted CardList
	 * @pre pComparator != null
	 */
	public CardList sort(Comparator<Card> pComparator)
	{
		assert pComparator != null;
		CardList pClone = clone();
		Collections.sort(pClone.aList, pComparator);
		
		return pClone;
	}
	
	/**
	 * Returns whether this CardList contains pCard.
	 * @param pCard the cart whose existence we check
	 * @return whether pCard was contained or not
	 * @pre pCard != null
	 */
	public boolean contains(Card pCard)
	{
		assert pCard != null;
		CardList cloned = this.clone();
		cloned.remove(pCard);
		
		return cloned.size() != size();
	}
	
}
