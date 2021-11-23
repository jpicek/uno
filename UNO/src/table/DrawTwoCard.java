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
		
	}
}
