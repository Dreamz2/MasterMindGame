package src.MasterMindGame;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MasterMind extends Application {

    BorderPane rootPane;
    VBox centerPane;

	public static void main(String[] args) {
		launch(args);

	}

    @Override
    public void start(Stage stage) throws Exception {
        
        rootPane = new BorderPane();
        rootPane.setStyle("-fx-background-color: rgb(104, 104, 115)");

        FlowPane topPane = new FlowPane(Orientation.HORIZONTAL);
        topPane.setPadding(new Insets(15, 10, 10, 10));
        topPane.setAlignment(Pos.CENTER);

            Label message = new Label("Master Mind");
            message.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
            message.setTextFill(Color.WHITE);
        
        topPane.getChildren().add(message);


        centerPane = new VBox(30);
        centerPane.setAlignment(Pos.CENTER);

        new Menu(stage);

        rootPane.setTop(topPane);
        rootPane.setCenter(centerPane);

        Scene scene = new Scene(rootPane, 500, 500);
		
		stage.setScene(scene);
		stage.setTitle("Master Mind");
		stage.show();
        
    }

    public class Menu {
        
        private String[] mainLabels = {"Start", "Cheater Mode", "Exit"};
        private ArrayList<Label> initialMenu = new ArrayList<>();
        private int guesses = 12, length = 2;
        private boolean cheaterMode = false;

        Menu(Stage stage) {
            for (String name : mainLabels) {
                Label label = new Label(name);
                label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
                label.setTextFill(Color.WHITE);

                label.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event clicked) {
                        removeMenu();
                        switch (label.getText().toString()) {
                            case "Exit":
                                stage.close();
                                break;
                            case "Cheater Mode":
                                cheaterMode = true;
                            default:
                                lenNDiff();
                                break;
                        }
                        
                    }
                });

                initialMenu.add(label);
            }
            centerPane.getChildren().addAll(initialMenu);
        }

        private void removeMenu() {
            centerPane.getChildren().removeAll(initialMenu);
        }

        public boolean isCheaterMode() {
            return cheaterMode;
        }
        
        public void lenNDiff() {
            FlowPane lenNDiffPane = new FlowPane(Orientation.VERTICAL, 0, 30);
            lenNDiffPane.setAlignment(Pos.CENTER);

                FlowPane lenPane = new FlowPane(Orientation.HORIZONTAL, 0, 10);
                lenPane.setAlignment(Pos.CENTER);

                    Label lenLabel = new Label("How many digits to guess? 2 - 9");
                    lenLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 28));
                    lenLabel.setTextFill(Color.WHITE);
                    TextField lenText = new TextField("2");
                    lenText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));

                lenPane.getChildren().addAll(lenLabel, lenText);

                VBox radioPane = new VBox(10);
                radioPane.setAlignment(Pos.CENTER);
                    ToggleGroup difficulty = new ToggleGroup();
                    RadioButton rad1 = new RadioButton("12 Guesses - Easy");
                    rad1.setUserData("12");
                    rad1.setSelected(true);
                    rad1.setTextFill(Color.WHITE);
                    RadioButton rad2 = new RadioButton("9 Guesses - Medium");
                    rad2.setUserData("9");
                    rad2.setTextFill(Color.WHITE);
                    RadioButton rad3 = new RadioButton("6 Guesses - Hard");
                    rad3.setUserData("6");
                    rad3.setTextFill(Color.WHITE);

                    rad1.setToggleGroup(difficulty);
                    rad2.setToggleGroup(difficulty);
                    rad3.setToggleGroup(difficulty);

                    difficulty.selectedToggleProperty().addListener(e -> {
                        guesses = Integer.parseInt(difficulty.getSelectedToggle().getUserData().toString());
                    });

                radioPane.getChildren().addAll(rad1, rad2, rad3);

            
                FlowPane btnPane = new FlowPane(Orientation.HORIZONTAL);
                btnPane.setAlignment(Pos.CENTER);
                    Button startBtn = new Button("Start Game");
                    startBtn.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            length = Integer.parseInt(lenText.getText().toString());
                            if(length<2||length>9) {
                                lenLabel.setText("Enter length between 2 - 9");
                            }
                            else {
                                rootPane.getChildren().removeAll(centerPane, startBtn);
                                new GameHandler(length, guesses);
                            }
                        }
                        
                    });
                btnPane.getChildren().add(startBtn);
            
            lenNDiffPane.getChildren().addAll(lenPane, radioPane, btnPane);
            centerPane.getChildren().addAll(lenNDiffPane);
        }
    }

    public class GameHandler{
        
        private int length;
        private int guesses;

        GameHandler(int length, int guesses) {
            this.length = length;
            this.guesses = guesses;
        }

    }
    
}
