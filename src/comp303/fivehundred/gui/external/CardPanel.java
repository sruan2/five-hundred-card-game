package comp303.fivehundred.gui.external;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import comp303.fivehundred.engine.GameEngine.Message;
import comp303.fivehundred.engine.IObserver;
import comp303.fivehundred.gui.BoardPanel;
import comp303.fivehundred.gui.ExchangeScreen;
import comp303.fivehundred.gui.GameRunner;
import comp303.fivehundred.gui.GameRunner.Mode;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardImages;
import comp303.fivehundred.util.CardList;

/**
 * @author Martin Robillard 7 November 2012, with personal modifications from Ioannis Fytilis Manages a CardList and
 *         supports selection of an individual card.
 */
@SuppressWarnings("serial")
public class CardPanel extends JPanel implements IObserver
{
	// Read-only: not synchronized with the GameEngine
	private HashMap<JLabel, Card> aCards = new HashMap<JLabel, Card>();

	private static Card aLatestClicked;
	private static CardList discardedCards;

	public CardPanel()
	{
		super(new OverlapLayout(new Point(45, 0)));

		GameRunner.addObserver(this);
		Insets ins = new Insets(10, 0, 0, 0);
		((OverlapLayout) getLayout()).setPopupInsets(ins);
	}

	private void initialize(CardList pCards)
	{
		setBorder(new TitledBorder(GameRunner.getEngine().getPlayer(0).getName()));
		aCards.clear();
		removeAll();
		for (Card card : pCards)
		{
			JLabel lLabel = new JLabel(CardImages.getCard(card));
			aCards.put(lLabel, card);
			lLabel.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent e)
				{
					Component c = e.getComponent();
					Boolean constraint = ((OverlapLayout) getLayout()).getConstraints(c);

					if (constraint == null || constraint == OverlapLayout.POP_DOWN)
					{
						popAllDown();
						((OverlapLayout) getLayout()).addLayoutComponent(c, OverlapLayout.POP_UP);
					}
					else
					{
						((OverlapLayout) getLayout()).addLayoutComponent(c, OverlapLayout.POP_DOWN);
					}

					c.getParent().invalidate();
					c.getParent().validate();
				}

				public void mousePressed(MouseEvent e)
				{
					if (GameRunner.getMode() == Mode.WAIT_PLAY)
					{
						aLatestClicked = isUp();

						if (aLatestClicked == null)
						{
							BoardPanel.displayErrorInfo("You can select a card only when it's up.");
						}
						else if (!cardPlayable())
						{
							BoardPanel
									.displayErrorInfo("You cannot play this card. You should perhaps read the rules!");
						}
						else
						{
							GameRunner.runAfterHumanTrick();
						}
					}
					else if (GameRunner.getMode() == Mode.WAIT_DISCARD)
					{
						aLatestClicked = isUp();

						if (aLatestClicked == null)
						{
							BoardPanel.displayErrorInfo("You can select a card only when it's up.");
						}
						else if (discardedCards.size() < 6)
						{
							ExchangeScreen.enableAutoDiscard(false);
							discardedCards.add(aLatestClicked);
							discard(aLatestClicked);
							if (discardedCards.size() == 6)
							{
								GameRunner.exchangeWidow();
							}

						}
						else if (discardedCards.size() == 6)
						{
							BoardPanel.displayErrorInfo("You have already discarded 6 cards. You cannot discard more!");
						}
					}
					else
					{
						BoardPanel.displayErrorInfo("This is not the time to be selecting a card!");
					}
				}
			});
			add(lLabel);
		}
		validate();
		repaint();
	}

	private void popAllDown()
	{
		Component[] lChildren = getComponents();
		for (Component component : lChildren)
		{
			((OverlapLayout) getLayout()).addLayoutComponent(component, OverlapLayout.POP_DOWN);
		}
	}

	/**
	 * @return The card that is up. Null if none.
	 */
	public Card isUp()
	{
		for (Component component : getComponents())
		{
			Boolean constraint = ((OverlapLayout) getLayout()).getConstraints(component);
			if (constraint != null && constraint == OverlapLayout.POP_UP)
			{
				return aCards.get(component);
			}
		}
		return null;
	}

	public static Card analyzeCards()
	{
		return aLatestClicked;
	}

	public static CardList analyzeDiscardedCards()
	{
		return discardedCards;
	}

	private void discard(Card pCard)
	{
		JLabel lToRemove = null;
		for (JLabel label : aCards.keySet())
		{
			if (aCards.get(label).equals(pCard))
			{
				lToRemove = label;
				break;
			}
		}
		aCards.remove(lToRemove);
		removeAll();
		CardList lList = new CardList();
		for (Card card : aCards.values())
		{
			lList.add(card);
		}
		lList = lList.sort(new Card.BySuitComparator(GameRunner.getEngine().getBestBid().getSuit()));
		initialize(lList);
		validate();
		repaint();
	}

	@Override
	public void update(Message pMessage)
	{
		Hand hand = null;
		switch (pMessage)
		{
		case DEALT:
			hand = GameRunner.getEngine().getHand(0);
			if (hand.size() == 10)
			{
				initialize(hand.sort(new Card.BySuitNoTrumpComparator()));
			}
			discardedCards = new CardList();
			break;
		case WIDOW:
			hand = GameRunner.getEngine().getHand(0);
			initialize(hand.sort(new Card.BySuitComparator(GameRunner.getEngine().getBestBid().getSuit())));
			break;
		case EXCHANGE:
			hand = GameRunner.getEngine().getHand(0);
			initialize(hand.sort(new Card.BySuitComparator(GameRunner.getEngine().getBestBid().getSuit())));
			break;
		case TRICK:
			hand = GameRunner.getEngine().getHand(0);

			if (GameRunner.getEngine().getLatestPlayerIndex() == 0)
			{
				discard(GameRunner.getEngine().getLatestCard());
			}
			break;
		default:
			break;
		}

	}

	private boolean cardPlayable()
	{
		Trick trick = GameRunner.getEngine().getTrick();
		Hand hand = GameRunner.getEngine().getHand(0);

		if (trick.size() == 0)
		{
			return hand.canLead(trick.getTrumpSuit() == null).contains(aLatestClicked);
		}
		else
		{
			return hand.playableCards(trick.getSuitLed(), trick.getTrumpSuit()).contains(aLatestClicked);
		}
	}

}