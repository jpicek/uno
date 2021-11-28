package table;

public final class WildCard extends Card {
	public WildCard() {
		this.color = CardColor.WILD;
		this.value = CardValue.WILD;
	}

	@Override
	public void performSpecialBehavior(tablePane t, Game g) {
		// handle the "Next Turn" behavior for special cards
		// and set the table properties correctly
		if (g.currentPlayer == 1) {
			t.wildChooserPane.setVisible(true);
		}
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
