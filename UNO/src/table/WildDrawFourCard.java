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
		t.wildChooserPane.setVisible(true);
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
