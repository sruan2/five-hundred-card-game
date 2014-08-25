	package comp303.fivehundred.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import comp303.fivehundred.gui.GameRunner.Mode;

/**
 * @author Ioannis Fytilis 260482744
 * Menu for the GUI.
 * Inspired partially by http://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
 */
@SuppressWarnings("serial")
public class TopMenu extends JMenuBar
{
	
	private static final int ABOUT_X = 500;
	private static final int ABOUT_Y = 400;
	
	/**
	 * Creates the menu.
	 */
	public TopMenu()
	{
		JMenu menu;
		JMenuItem menuItem;
		
		// Game Options
		menu = new JMenu("Options");
		add(menu);

		menuItem = new JMenuItem("New Game");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode()==Mode.WAIT_NEW)
				{
					GameRunner.play();
				}
				else
				{
					raiseError();
				}
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
                 KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Customize Robots");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode()==Mode.WAIT_NEW)
				{
					GameRunner.createPlayers();
				}
				else
				{
					raiseError();
				}
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Reset Statistics");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				if (GameRunner.getMode()==Mode.WAIT_NEW)
				{
					GameRunner.resetStats();
				}
				else
				{
					raiseError();
				}
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		

		// Help
		menu = new JMenu("Help");
		menuItem = new JMenuItem("Rules");		
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				// These rules were obtained from the "Rules of 500" posting on the McGill myCourses section for COMP303 
				//(fall 2012, with Professor Robillard)
				JTextArea textArea = new JTextArea(
		                "---Overview---\n\n" +
						
						"Five Hundred is a trick-taking game played between two partnerships of two players."+
						"The goal of the game is to win rounds according to a pre-established contract. A round"+
						"consists in a number of tricks; each trick consists in a player laying down a card of" +
						"the same suit but of a value higher that the previous player's card. In a round, the" +
						"partnership that wins the highest number of tricks, and at least as many tricks as" +
						"specified by their contract, wins the round. Each round provides the winning partnership" +
						"with a certain amount of points. The first partnership to accumulate 500 points wins the" +
						"game. There are many variations in the rules of Five Hundred. The rules described here" +
						"are the ones we will use in the course.\n\n"+
						
						"---Players and Cards---\n\n"+
						
						"There are four players, with partners sitting opposite. A pack of 46 cards is used," +
						"consisting of all the cards in the four suits except twos and threes, and two" +
						"distinguishable jokers: the high and the low jokers. When there is a trump suit (see" +
						"below), the highest trump is the high joker, followed by the low joker, followed by the" +
						"jack of the trump suit (right bower), the other jack of the same colour (left bower)," +
						"then Ace, King, Queen, 10, 9, etc. down to 4. For purposes of following suit, etc, the" +
						"joker and left bower behave in all respects as members of the trump suit. The other" +
						"three suits rank in the usual order from ace (highest) down to 4, but the suit which is" +
						"the same colour as trumps has no jack.\n\n"+

						"When there are no trumps all the suits rank in the usual order from ace (high) down to" +
						"4 (low), and there are special rules governing how the joker is played.\n\n"+

						"---Deal---\n\n"+

						"Deal, bidding and play proceed clockwise. The first dealer is chosen at random, and " +
						"the turn to deal rotates clockwise after each hand. The cards are shuffled and cut and" +
						"the dealer deals 10 cards to each player and six face down in the middle of the table to" +
						"form the widow.\n\n"+

						"---Bidding---\n\n"+

						"The bidding begins with the player to dealer's left and continues clockwise until all" +
						"players have bid once and only once. The possible bids are:\n\n"+

						"- a number of tricks (minimum six) and a trump suit - for example a bid of \"eight diamonds\"" +
						"undertakes that the bidder, with partner's help, will try to win at least eight tricks with" +
						"diamonds as trumps;\n"+
						"- a number (minimum six) of \"No Trump\", offering to win at least that number of tricks" +
						"without a trump suit;\n"+
						"- \"Pass\", indicating that the player does not wish to obtain the contract.\n\n"+
						"Once someone has bid, each subsequent bid must be higher than the previous one. Higher" +
						"means either more tricks, or the same number of tricks in a higher suit. For this purpose" +
						"No trump are highest, followed by Hearts, Diamonds, Clubs, and Spades (lowest). Thus the" +
						"lowest possible bid is Six Spades and the highest is Ten No Trump.\n\n"+

						"The highest bid becomes the contract which the bidder (contractor) has to make, with the" +
						"named suit (if any) as trumps.\n\n"+

						"If all players pass, the same dealer deals a fresh round and no points are scored.\n\n"+

						"---Play---\n\n"+

						"The contractor begins by picking up the six cards of the widow (without showing them to the" +
						"other players), and discarding any six cards face down in their place. The cards discarded" +
						"can include cards which were picked up from the widow.\n\n"+

						"The contractor leads to the first trick. Players must follow suit if they can. A player with" +
						"no card of the suit led may play any card. A trick is won by the highest trump in it, or if" +
						"no trump is played by the highest card of the suit led. The winner of a trick leads to the" +
						"next.\n\n"+

						"Players cannot lead a joker when playing in no trump, except if they have no other cards left." +
						"When a joker is led, players do not have to follow any suit (but anything except a better" +
						"joker will lose the trick).\n\n"+

						"If there is a trump suit, the jokers counts as the highest trumps, as stated above.\n\n"+

						"In No Trump, the jokers belongs to no suit. They are the highest cards in the pack, and win" +
						"the trick to which they are played, but you can only play the joker if you have no cards of" +
						"the suit led;\n\n"+

						"---Scoring---\n\n"+

						"A cumulative score is kept for each partnership, to which the score for each hand is added or" +
						"subtracted. The scores for the various contracts are as follows:\n\n"+

						"Tricks      Spades    Clubs    Diamonds   Hearts   No Trump\n"+
						"Six                  40         60                80       100             120\n"+
						"Seven            140       160              180       200             220\n"+
						"Eight             240       260              280       300             320\n"+
						"Nine              340       360              380       400             420\n"+
						"Ten               440       460              480       500             520\n\n"+
						
						"In a suit or no trump contract, the contractors win if they take at least as many tricks as they" +
						"bid. The contractors then score the appropriate amount from the above table, and their opponents" +
						"score 10 points for each trick they manage to win. There is no extra score for any additional" +
						"tricks the contractors may make in excess of their bid, except when they win every trick, which" +
						"is called a slam. If the contractors make a slam, and their bid was worth less than 250 points," +
						"they score 250 instead of their bid. If the bid was worth more than 250 (8 clubs or more) there is" +
						"no special score for a slam - if the contractors win every trick they just win the value of their" +
						"bid as normal.\n\n"+
						
						"If the contractors do not take enough tricks for their suit or no trump contract, they score minus" +
						"the value of the contract, and their opponents still score 10 points for each trick they won.\n\n"+

						"---End of the Game---\n\n"+

						"The game ends when a team wins by reaching a score of 500 points or more as a result of winning a" +
						"contract.\n\n"+

						"The game also ends if a team reaches minus 500 points or worse, and thus loses the game.\n\n"+

						"Reaching 500 points or more as a result of odd tricks won while the other side are playing a" +
						"contract is not sufficient to win the game. If this happens, further hands are played until one" +
						"team wins or loses as described above.\n\n"+

						"---Glossary---\n\n"+

						"Bid:\n"+
						"   A declared intention to win a minimum number of tricks in a round if the trump is the suit" +
						"   announced (or without a trump suit, in the case of a \"No Trump\" bid).\n\n"+
						"Contract:\n"+
						"   The highest bid for a round.\n\n"+
						"Contractor:\n"+
						"   The player who made the highest bid.\n\n"+
						"Deal:\n"+
						"   The dealer deals 10 cards to each player, and 6 in the widow. A deal occurs before each round.\n\n"+
						"Dealer:\n"+
						"   A player in charge of dealing cards. In computer versions of the game, it obviously does not" +
						"   matter who physically deals the cards. However, the dealer also determines the order in which" +
						"   players place their bids.\n\n"+
						"Game:\n"+
						"   A variable number of rounds. A game is completed when one partnership reaches a score of at" +
						"   least 500 by winning a round.\n\n"+
						"Hand:\n"+
						"   All the cards currently held by a player during a round.\n\n"+
						"Joker:\n"+
						"   A card with a special rank (typically the highest), and no suit. In our version of Five Hundred" +
						"   there are two jokers.\n\n"+
						"Leading:\n"+
						"   Playing the first card of a trick. In Five Hundred the winner of the last trick leads the next.\n\n"+
						"Partnership:\n"+
						"   Two players playing together. A team. In real games, the players in a partnership sit across from" +
						"   each other.\n\n"+
						"Player:\n"+
						"   One of four entities taking part in a game. A player can be controlled by a human or automatically" +
						"   by software.\n\n"+
						"Rank:\n"+
						"   The relative value of a card withint a suit. For example a five of clubs has higher rank than a" +
						"   four of clubs. Note that in Five Hundred, Jacks can have different ranks depending on the trump" +
						"   suit.\n\n"+
						"Round:\n"+
						"   Ten consecutive tricks following a deal. A round can also be called a hand, but to avoid confusion" +
						"   with the definition of hand in this glossary, we will use the term \"round\" instead.\n\n"+
						"Score:\n"+
						"   The cumulative number of points accumulated by a partnership during a game. The sum of points" +
						"   accumulated during all the rounds in the game. Both players in a partnership have the same score.\n\n" +
						"Suit:\n"+
						"   With the rank, one of the two attributes of a card. One of Spades, Clubs, Diamonds, or Hearts.\n\n"+
						"Trick:\n"+
						"   A basic unit of play. Consists of all players playing (or discarding) one card.\n\n"+
						"Trump:\n"+
						"   A suit with special powers during a round. The trump suit is determined by the contract.\n\n"+
						"Widow:\n"+
						"   Six hidden cards that can be used to improve the contractor's hand.");
				textArea.setLineWrap( true );
				textArea.setWrapStyleWord( true );
				textArea.setEditable(false);
		        JScrollPane scrollPane = new JScrollPane(textArea);
		        scrollPane.setPreferredSize(new Dimension(ABOUT_X, ABOUT_Y));
		        JOptionPane.showMessageDialog(null, scrollPane, "Rules of 500", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent pEvent)
			{
				JOptionPane.showMessageDialog(null,
					    "\u00A9 2012 by \n     Gerald Lang,\n     Ioannis Fytilis,\n     Sherry Ruan,\n     Stephanie Pataracchia.\n     " +
					    "All rights reserved.", "About Play 500 with me!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		add(menu);
		
		
		
	}
	
	private void raiseError()
	{
		JOptionPane.showMessageDialog(null,
			    "You must finish your current game if you wish to do this", "Play more!", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
