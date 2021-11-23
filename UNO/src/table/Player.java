package table;

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
	
	public void performAITurn(Game g) {
		boolean foundPlayableCard = false;
		
		for (Card c : hand) {
			if (c.playCard(g)) {
				foundPlayableCard = true;
				g.playedPile.add(c);
				break;
			}
			if (!foundPlayableCard) {
				this.hand.add(g.d.get(0));
				g.d.remove(0);
			}
		}
	}
	
}
