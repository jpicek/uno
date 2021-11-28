package table;

public final class WildDrawFourCard extends Card {
	public WildDrawFourCard() {
		this.color = CardColor.WILD;
		this.value = CardValue.WILDDRAWFOUR;
	}
	
	@Override
	public void performSpecialBehavior(tablePane t, Game g) {
		// handle the "Next Turn" behavior for special cards
		// and set the table properties correctly
		if (g.currentPlayer == 1) {
			t.wildChooserPane.setVisible(true);
		}
		// calculate the next player
		int dir = g.turnDirection ? 1 : -1;
		int recip = (g.currentPlayer + dir);
		// correct two outlier results
		if (recip == 0) {
			recip = 4;
		}
		if (recip == 5) {
			recip = 1;
		}
		// next player draws 4 cards
		Player recipient = Game.getPlayer(recip);
		g.draw(4, recipient);
		g.skipTurn();
	}

	@Override
	public boolean playCard(Game game) {
		// return true if the card can be played, otherwise return false
		return true;
	};
	
	@Override
	public String toString() {
		return new String("" + this.value + "\n");
	}
}
