package src.MasterMindGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MasterMind extends Application {

    BorderPane rootPane;

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage stage) throws Exception {
        
        rootPane = new BorderPane();
        rootPane.setStyle("-fx-background-color: rgb(104, 104, 115)");

        new MainMenu(stage, rootPane);

        Scene scene = new Scene(rootPane, 600, 600);
		
		stage.setScene(scene);
		stage.setTitle("MasterMind");
		stage.show();
        
    }
}