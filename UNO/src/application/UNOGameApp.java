package application;
	
import javafx.application.Application;
import table.tablePane;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class UNOGameApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			tablePane root = new tablePane();
			Scene scene = new Scene(root,1200,1200);
			primaryStage.setTitle("UNO");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
