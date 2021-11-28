package table;

import table.Card.CardColor;
import table.Card.CardValue;

public class Player {
	public Deck<Card> hand = new Deck<Card>();
	public int seat;
	public String name;
	
	public Player(int seat, String name) {
		this.name = name;
	}
	
	protected boolean playCard() {
		return true;
	}

	
	public void performAITurn(Game g, tablePane t) throws InterruptedException {
		boolean foundPlayableCard = false;
		
		// check for playable non-wild cards
		for (Card c : hand) {
			// if so, play the first one we come across
			if (c.playCard(g) && c.color != CardColor.WILD) {
					foundPlayableCard = true;
					System.out.println(this.name + " played card " + c.toString());
					g.playedPile.add(c);
					g.colorOfPlayPile = c.color;
					g.numberOfPlayPile = c.value;
					hand.remove(c);
					c.performSpecialBehavior(t, g);
					break;
			}
		}
		// if a card has not yet been played this turn, check for Wild
		if (!foundPlayableCard) {
			for (Card c: hand) {
				if (c.playCard(g)) {
					foundPlayableCard = true;
					System.out.println(this.name + " played card " + c.toString());
					g.playedPile.add(c);
					g.colorOfPlayPile = c.color;
					g.numberOfPlayPile = c.value;
					// choose the wild color, for now just default red
					g.setColor(CardColor.RED);
					
					hand.remove(c);
					c.performSpecialBehavior(t, g);
					break;
				}
			}
		}
		
		// a card still has not yet been played this turn, draw and try to play it 
		if (!foundPlayableCard) {
			System.out.println(this.name + " has no playable card and drew 1");
			this.hand.add(g.d.get(0));
			g.d.remove(0);
			if (g.d.get(0).playCard(g)) {
				// and if possible, play the card
				Card c = g.d.get(0);
				foundPlayableCard = true;
				System.out.println(this.name + " played card " + c.toString());
				g.playedPile.add(c);
				g.colorOfPlayPile = c.color;
				g.numberOfPlayPile = c.value;
				
				if (g.colorOfPlayPile == CardColor.WILD)
				{
					// choose the wild color, for now just default red
					g.setColor(CardColor.RED);
					hand.remove(c);
					c.performSpecialBehavior(t, g);
				}
				else {
					hand.remove(c);
					c.performSpecialBehavior(t, g);
				}
			}
			else {
				System.out.println(this.name + " couldn't play any card this turn");
			}
		}
		g.advanceTurn(t);
	}
}
