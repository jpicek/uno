package table;

public final class NumberCard extends Card {
	public NumberCard(CardColor color, CardValue value) {
		super(color, value);
	}

public void performSpecialBehavior(tablePane t, Game g) {};
// this card has no special behavior so its empty
}