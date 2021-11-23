package table;

public final class ReverseCard extends Card {
	public ReverseCard(CardColor color) {
		this.color = color;
		this.value = CardValue.REVERSE;
	}

	@Override
	public void performSpecialBehavior(tablePane t, Game g) {
		// handle the "Next Turn" behavior for special cards
		// and set the table properties correctly
		
	}
}
