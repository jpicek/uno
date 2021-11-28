package table;

public final class DrawTwoCard extends Card {
	public DrawTwoCard(CardColor color) {
		this.color = color;
		this.value = CardValue.DRAWTWO;
	}

	@Override
	public void performSpecialBehavior(tablePane t, Game g) {
		// handle the "Next Turn" behavior for special cards
		// and set the table properties correctly
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
		// next player draws 2 cards
		Player recipient = Game.getPlayer(recip);
		g.draw(2, recipient);
		g.skipTurn();
	}
}
