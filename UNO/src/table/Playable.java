package table;

public interface Playable {
	public abstract boolean playCard(Game game);
	// return true if the card can be played, otherwise return false
	
	public abstract void performSpecialBehavior(tablePane t, Game g);
	// handle the "Next Turn" behavior for special cards
	// and set the table properties correctly
}
