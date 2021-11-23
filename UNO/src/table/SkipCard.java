package table;

public final class SkipCard extends Card {
	protected SkipCard(CardColor color) {
		this.color = color;
		this.value = CardValue.SKIP;
	}

	@Override
	public void performSpecialBehavior(tablePane t, Game g) {
		// handle the "Next Turn" behavior for special cards
		// and set the table properties correctly
		g.advanceToNextTurn();
	}
}
