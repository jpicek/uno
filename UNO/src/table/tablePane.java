package table;

import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import table.Card.*;

public class tablePane extends BorderPane {
	
	//   3		The table looks like this
	// 2   4	for reference
	//   1		Game.turnDirection true is clockwise, false is counter-clockwise
	
	HBox seat1 = new HBox();
	VBox seat2 = new VBox();
	HBox seat3 = new HBox();
	VBox seat4 = new VBox();
	GridPane pilesAndGameInfo = new GridPane();
	HBox wildDirections = new HBox();
	protected HBox wildChooserPane = new HBox();
	HBox gameInfo = new HBox();
	Pane discardArea = new Pane();
	Pane drawArea = new Pane();
	Image unoCardBack = new Image("file:images/cardback.png");
	Text infoText = new Text();
	boolean refreshed = false;
	int lastRefreshedTurn;
	boolean hasDrawnYet = false;
	ImageView chooseRed, chooseBlue, chooseYellow, chooseGreen;
	Game g;
	Timeline guiUpdating;
	
	public tablePane() {
		g = new Game(this);
		new Thread(g).start();
		guiUpdating = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (refreshed == false) {
					lastRefreshedTurn = g.currentTurnNumber;
					refreshPane(g);
				}
				if (! (infoText.getText().equals(generateGameInfo(g)))) {
					updatePane(g);
					if (g.currentTurnNumber != lastRefreshedTurn) {
						refreshed = false;
					}
				}
			}
			}));
		
		guiUpdating.setCycleCount(Timeline.INDEFINITE);
		guiUpdating.play();
		
		// seat 1 JavaFX properties
		this.setBottom(seat1);
		seat1.setMinHeight(200);
		seat1.setMinWidth(500);
		seat1.setSpacing(-25);
		seat1.setAlignment(Pos.CENTER);
		
		// seat 2 JavaFX properties
		this.setLeft(seat2);
		seat2.setMinHeight(500);
		seat2.setMinWidth(150);
		seat2.setSpacing(-65);
		seat2.setAlignment(Pos.CENTER);
		
		// seat 3 JavaFX properties
		this.setTop(seat3);
		seat3.setMinHeight(200);
		seat3.setMinWidth(500);
		seat3.setSpacing(-25);
		seat3.setAlignment(Pos.CENTER);
		
		// seat 4 JavaFX properties
		this.setRight(seat4);
		seat4.setMinHeight(500);
		seat4.setMinWidth(150);
		seat4.setSpacing(-65);
		seat4.setAlignment(Pos.CENTER);
		
		// draw and discard pile properties
		// also displays game information like: who's turn, game direction, and current playable CardColor CardValue 
		this.setCenter(pilesAndGameInfo);
		pilesAndGameInfo.minWidth(300);
		pilesAndGameInfo.setAlignment(Pos.CENTER);
		pilesAndGameInfo.setHgap(50);
		pilesAndGameInfo.add(gameInfo, 0, 0, 4, 1);
		pilesAndGameInfo.add(discardArea, 0, 1, 1, 1);
		pilesAndGameInfo.add(drawArea, 3, 1, 1, 1);
		pilesAndGameInfo.add(wildDirections, 0, 2, 4, 1);
		pilesAndGameInfo.add(wildChooserPane, 0, 3, 4, 1);
		wildChooserPane.minHeight(200);
		wildChooserPane.setSpacing(5);
		wildChooserPane.setPadding(new Insets(0, 0, 0, 5));
		gameInfo.setAlignment(Pos.CENTER);
		gameInfo.setStyle("-fx-background-color: #ffd966");
		infoText.setFont(Font.font("Consolas"));
		discardArea.minWidth(150);
		drawArea.minWidth(150);
		// replace these labels with wildChooser buttons showing Red, Blue, Yellow, and Green
		// add another row here to hold "UNO!" button too
		
		wildDirections.getChildren().add(new Label("You've thrown a wild! Change to which color?"));
		wildDirections.setAlignment(Pos.CENTER);
		wildDirections.setVisible(false);
		
		chooseRed = new ImageView(new Image("file:images/chooseRed.png"));
		chooseBlue = new ImageView(new Image("file:images/chooseBlue.png"));
		chooseYellow = new ImageView(new Image("file:images/chooseYellow.png"));
		chooseGreen = new ImageView(new Image("file:images/chooseGreen.png"));
		
		wildChooserPane.getChildren().addAll(chooseRed, chooseBlue, chooseYellow, chooseGreen);
		wildChooserPane.setVisible(false);
		
		System.out.println(g.playedPile.peek().toString());
		// gameInfo.getChildren().add(new Label(generateGameInfo(g)));
		// discardArea.getChildren().add(new ImageView(new Image(new String("file:images/" + g.playedPile.peek().color + g.playedPile.peek().value + ".png"))));
		drawArea.getChildren().add(new ImageView(new Image("file:images/cardback.png")));
		
		Player gameWinner = Game.checkWinConditions();
		boolean resultsDisplayed = false;
		
		refreshPane(g);
		}
	
	public String generateGameInfo(Game g) {
		String t = "";
		if (g.turnDirection == false) {
			t = "Counter-";
		}
		t += "Clockwise";
		return new String("Turn Number: " + g.currentTurnNumber + 
						"\n      Player " + g.currentPlayer + "'s Turn" +
						"\n  Direction: " + t +
						"\n Play Color: " + g.colorOfPlayPile +
						"\n Play Value: " + g.numberOfPlayPile);
	}
	
	public void refreshPane(Game g) {
		// a player's turn begins, allowing interaction via mouse hover + clicks
		hasDrawnYet = false;
		lastRefreshedTurn = g.currentTurnNumber;
		refreshed = true;
				
		gameInfo.getChildren().clear();
	
		infoText.setText(generateGameInfo(g));
		gameInfo.getChildren().add(infoText);
		discardArea.getChildren().add(new ImageView(new Image(new String("file:images/" + g.playedPile.peek().color + g.playedPile.peek().value + ".png"))));

		
		////  Event handlers for buttons, player interactions
		
		// clicking on the drawPile
		drawArea.setOnMouseReleased( e-> {
			// upon clicking on the draw Area,
			if (!hasDrawnYet) {
				// draw a card if we haven't yet
				Game.Human.hand.add(g.d.get(0));
				hasDrawnYet = true;
				refreshPane(g);
				// if the drawn card is playable, allow a chance to play it
				if (g.d.get(0).playCard(g)) {
					g.d.remove(0);
					refreshPane(g);
					return;
				}
				// otherwise, the drawn card is not playable, so advance to cpuPlay
				else {
					g.d.remove(0);
					updatePane(g);
					hasDrawnYet = false;
					try {
							g.advanceTurn(this);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
						System.out.println("thread interrupted");
					}
				}
			} });

		// clicking on color choices from the Wild Color Chooser
		chooseRed.setOnMouseReleased( e -> {
			g.setColor(CardColor.RED); wildChooserPane.setVisible(false);
			try {
				g.advanceTurn(this);
			} catch(InterruptedException e1) {
				e1.printStackTrace();
		}});
		chooseBlue.setOnMouseReleased( e -> {
			g.setColor(CardColor.BLUE); wildChooserPane.setVisible(false);
			try {
				g.advanceTurn(this);
			} catch(InterruptedException e1) {
				e1.printStackTrace();
		}});
		chooseYellow.setOnMouseReleased( e -> {
			g.setColor(CardColor.YELLOW); wildChooserPane.setVisible(false);
			try {
				g.advanceTurn(this);
			} catch(InterruptedException e1) {
				e1.printStackTrace();
		}});
		chooseGreen.setOnMouseReleased( e -> {
			g.setColor(CardColor.GREEN); wildChooserPane.setVisible(false);
			try {
				g.advanceTurn(this);
			} catch(InterruptedException e1) {
				e1.printStackTrace();
		}});
		
		seat1.getChildren().clear();
		// refresh Seat1, the Human player's seat, with event Handlers
		for (Card c : Game.Human.hand) {
			Image imageI = new Image(new String("file:images/" + c.color + c.value + ".png"));
			ImageView i = new ImageView(imageI);
			// Label l = new Label(c.toString());
			
			seat1.getChildren().add(i);
			
		// lambda event Handlers for each Card image object in the player's hand
			// grow card on Hover
			i.setOnMouseEntered(e -> { i.setScaleX(1.2); i.setScaleY(1.2); });
			// reduce card to normal size when Hover ends
			i.setOnMouseExited(e -> { i.setScaleX(1.0); i.setScaleY(1.0); });
			// when released mouse on card, attempt to play card
			i.setOnMouseReleased(e -> {
				// 
				if (c.playCard(g) && g.currentPlayer == 1) {
					// if the card can be played
					if (Game.Human.hand.remove(c)) {
						// and is played
						System.out.println("Human played card " + c.toString());
						// add it to the discard pile, save new PlayPile data in Game g 
						g.playedPile.add(c);
						g.colorOfPlayPile = c.color;
						g.numberOfPlayPile = c.value;
						// perform special card Behavior
						c.performSpecialBehavior(this, g);
						// updatePane(g);
						if (g.colorOfPlayPile != CardColor.WILD ) {
							// then a non-wild was played, and advance turn
							try {
								g.advanceTurn(this);
							} catch (InterruptedException e1) {
							e1.printStackTrace();
							System.out.println("thread interrupted");
							}
						}						
						else {}
						// otherwise a wild was played, so do not advance turn. WildChooser will cause the turn advance
							
					System.out.print("Discard pile Top Card is: " + g.playedPile.peek().toString());
					}
				}
			});
		}
		
		// update Seat 2
		seat2.getChildren().clear();
		for (Card c : Game.CPU_P2.hand) {
			ImageView i = new ImageView(unoCardBack);
			seat2.getChildren().add(i);
			i.setRotate(270);
			//Label l = new Label(c.toString());
			//seat3.getChildren().add(l);
		}
		
		// update Seat 3
		seat3.getChildren().clear();
		for (Card c : Game.CPU_P3.hand) {
			ImageView i = new ImageView(unoCardBack);
			seat3.getChildren().add(i);
			//Label l = new Label(c.toString());
			//seat3.getChildren().add(l);
		}

		// update Seat 4
		seat4.getChildren().clear();
		for (Card c : Game.CPU_P4.hand) {
			ImageView i = new ImageView(unoCardBack);
			seat4.getChildren().add(i);
			i.setRotate(90);
			//Label l = new Label(c.toString());
			//seat3.getChildren().add(l);
		}
		System.out.println("refresh completed");
	}
	
	public Player updatePane(Game g) {
		// just update visuals
		// with no lambda event handlers, so no actions can be performed by the human
		// only refreshPane() allows for human interaction
		Player winner = Game.checkWinConditions();
				
		gameInfo.getChildren().clear();
		
		if (winner != null) {
			infoText.setText(winner.name + " WINS!");
			gameInfo.getChildren().add(infoText);
			System.out.println(winner.name + " WINS!");
			g.isGameFinished = true;
		}
		else {
			
			infoText.setText(generateGameInfo(g));
			gameInfo.getChildren().add(infoText);
			discardArea.getChildren().add(new ImageView(new Image(new String("file:images/" + g.playedPile.peek().color + g.playedPile.peek().value + ".png"))));
	
			// update Seat1, the Human player's seat
			seat1.getChildren().clear();
			for (Card c : Game.Human.hand) {
				Image imageI = new Image(new String("file:images/" + c.color + c.value + ".png"));
				ImageView i = new ImageView(imageI);
				seat1.getChildren().add(i);
			}
			
			// update Seat 2
			seat2.getChildren().clear();
			for (Card c : Game.CPU_P2.hand) {
				ImageView i = new ImageView(unoCardBack);
				seat2.getChildren().add(i);
				i.setRotate(270);
			}
			
			// update Seat 3
			seat3.getChildren().clear();
			for (Card c : Game.CPU_P3.hand) {
				ImageView i = new ImageView(unoCardBack);
				seat3.getChildren().add(i);
			}
			
			// update Seat 4
			seat4.getChildren().clear();
			for (Card c : Game.CPU_P4.hand) {
				ImageView i = new ImageView(unoCardBack);
				seat4.getChildren().add(i);
				i.setRotate(90);
			}
			System.out.println("pane updated");
		}
		if (g.isGameFinished == true) {
			guiUpdating.pause();
		}
		return winner;
	}
}