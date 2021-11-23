package table;

public abstract class Card implements Playable {
	public enum CardColor {
		RED, YELLOW, BLUE, GREEN, WILD
		}
	public enum CardValue {
		ZERO,
		ONE,
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		SKIP,
		REVERSE,
		DRAWTWO,
		WILD,
		WILDDRAWFOUR
		}
	
	protected CardColor color;
	protected CardValue value;
	
	public Card() { };
	
	public Card(CardColor color, CardValue value) { 
		this.color = color;
		this.value = value;
	};
	
	public boolean playCard(Game game) {
		// return true if the card can be played, otherwise return false
		if (game.colorOfPlayPile == this.color || game.numberOfPlayPile == this.value) {
			return true;
		}
		else {
			return false;
		}
	};
	
	public String toString() {
		return new String(this.color + " " + this.value + "\n");
	}
}
