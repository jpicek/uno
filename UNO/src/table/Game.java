package table;

import java.util.Collections;
import java.util.Stack;

import table.Card.CardColor;
import table.Card.CardValue;

public class Game {
	private final int STARTING_PLAYER = 1;
	private final int NUM_PLAYERS = 4;
	public int currentTurnNumber = 1;
	public int currentPlayer = 1;
	
	public boolean turnDirection = true;	// true is clockwise, going from player 1 (Human at bottom of screen) to 2, 3, 4, 1...
											// false is counter-clockwise, going from player 1 (Human at bottom of screen) to 4, 3, 2, 1...
	
	//   3		The table looks like this
	// 2   4	for reference
	//   1		true is clockwise, false is counter-clockwise
	
	protected CardColor colorOfPlayPile;		// same as Card.CardColor
	protected CardValue numberOfPlayPile; 			// same as Card.CardValue
	Deck<Card> d = new Deck<>();
	Stack<Card> playedPile = new Stack<>();
	
	public static Player Human = new Player(1, "John");
	public static Player CPU_P2 = new Player(2, "CPU P2");
	public static Player CPU_P3 = new Player(3, "CPU P3");
	public static Player CPU_P4 = new Player(4, "CPU P4");
	
	public Game() {
		
		createDeck(d);
		shuffleDeck(d);
		
		dealHands(d, Human.hand, CPU_P2.hand, CPU_P3.hand, CPU_P4.hand);
		// printDeck(d);
		
		playedPile.add(d.get(0));
		d.remove(0);
		this.colorOfPlayPile = playedPile.peek().color;
		this.numberOfPlayPile = playedPile.peek().value;
	}
	
	public void advanceToNextTurn() {
		// 
		this.currentTurnNumber++;
	}
	
	public void dealHands(Deck<Card> deck, Deck<Card> p1, Deck<Card> p2, Deck<Card> p3, Deck<Card> p4) {
		for (int cardsPerPlayer = 1; cardsPerPlayer <= 7; cardsPerPlayer++) {
			p1.add(deck.get(0));
			d.remove(0);
			p2.add(deck.get(0));
			d.remove(0);
			p3.add(deck.get(0));
			d.remove(0);
			p4.add(deck.get(0));
			d.remove(0);
		}
		System.out.println("John's Hand:\n" + p1);
		System.out.println("Player 2's Hand:\n" + p2);
		System.out.println("Player 3's Hand:\n" + p3);
		System.out.println("Player 4's Hand:\n" + p4);
	}
	
	public Deck<Card> createDeck(Deck<Card> deck) {
		
		for (CardColor c : CardColor.values()) {
			if (c != CardColor.WILD) {
				// For each CardColor OTHER THAN Wild,
				// add 1 ZERO
				deck.add(new NumberCard(c, CardValue.ZERO));
				for (CardValue v : CardValue.values()) {
					// add 2 of EVERY Value except ZERO, WILD, and WILDDRAWFOUR
					if ( ! (v == CardValue.ZERO || v == CardValue.WILD || v == CardValue.WILDDRAWFOUR)) {
						if (v == CardValue.SKIP) {
							// SKIP are made from SkipCard class
							Collections.addAll(deck, new SkipCard(c), new SkipCard(c));
						}
						else if (v == CardValue.REVERSE) {
							// REVERSE are made from ReverseCard class
							Collections.addAll(deck, new ReverseCard(c), new ReverseCard(c));
						}
						else if (v == CardValue.DRAWTWO) {
							// DRAWTWO are made from DrawTwoCard class
							Collections.addAll(deck, new DrawTwoCard(c), new DrawTwoCard(c));
						}
						else {
							// the rest of the values, ONE through NINE, are made from NumberCard class
							Collections.addAll(deck, new NumberCard(c, v), new NumberCard(c, v));
						}
					}
				}
			}
			else { // here, CardColor IS Wild, so add 4 of each
				for (int i = 0; i < 4; i++) {
					Collections.addAll(deck, new WildCard(), new WildDrawFourCard());
				}	
			}
		}
		return deck;
	}
	
	public static Player checkWinConditions() {
		if (Human.hand.size() == 0) {
			return Human;
		}
		else if (CPU_P2.hand.size() == 0) {
			return CPU_P2;
		}
		else if (CPU_P3.hand.size() == 0) {
			return CPU_P3;
		}
		else if (CPU_P4.hand.size() == 0) {
			return CPU_P4;
		}
		else {
			return null;
		}
	}
	public void printDeck(Deck<Card> deck) {
		System.out.println("Deck contains: \n" + deck.toString());
		System.out.println("Cards in deck: " + deck.size());
	}
	
	public void setColor(CardColor cc) {
		this.colorOfPlayPile = cc;
	}
	
	public Deck<Card> shuffleDeck(Deck<Card> deck) {
		Collections.shuffle(deck);
		return deck;
	};

}
