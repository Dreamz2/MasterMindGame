package src.MasterMindGame;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenu implements Difficulty{

    private BorderPane rootPane;
    private String[] mainLabels = {"Start", "Cheater Mode", "Exit"};
    private ArrayList<Label> initialMenu = new ArrayList<>();
    private int guessesAllowed = 12, lengthOfDigits = 2;
    private boolean cheaterMode = false;
    private VBox centerPane;
    private Stage stage;

    MainMenu(Stage stage, BorderPane rootPane) {
        this.rootPane = rootPane;
        centerPane = new VBox(30);
        centerPane.setAlignment(Pos.CENTER);
        this.stage = stage;
        title();
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
        rootPane.setCenter(centerPane);
    }

    private void title() {
        FlowPane topPane = new FlowPane(Orientation.HORIZONTAL);
        topPane.setPadding(new Insets(15, 10, 10, 10));
        topPane.setAlignment(Pos.CENTER);

            Label message = new Label("MasterMind");
            message.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
            message.setTextFill(Color.WHITE);
        
        topPane.getChildren().add(message);
        rootPane.setTop(topPane);
    }

    private void removeMenu() {
        centerPane.getChildren().removeAll(initialMenu);
    }
    
    public void lenNDiff() {
        FlowPane lenNDiffPane = new FlowPane(Orientation.VERTICAL, 0, 30);
        lenNDiffPane.setAlignment(Pos.CENTER);

            // FlowPane lenPane = new FlowPane(Orientation.HORIZONTAL, 0, 10);
            VBox lenPane = new VBox(10);
            lenPane.setAlignment(Pos.CENTER);

                Label lenLabel = new Label("How many digits do you want to guess?");
                lenLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 28));
                lenLabel.setAlignment(Pos.CENTER);
                lenLabel.setTextFill(Color.WHITE);
                Label lenToEnter = new Label("2 - 9");
                lenToEnter.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 28));
                lenToEnter.setAlignment(Pos.CENTER);
                lenToEnter.setTextFill(Color.WHITE);
                Pane lenInputPane = new Pane();
                    TextField lenInputText = new TextField("2");
                    lenInputText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
                    lenInputText.setAlignment(Pos.CENTER);
                    lenInputText.setLayoutX(200);
                    lenInputText.setPrefSize(100, 45);
                lenInputPane.getChildren().add(lenInputText);


            lenPane.getChildren().addAll(lenLabel, lenToEnter, lenInputPane);

            VBox radioPane = new VBox(10);
            radioPane.setAlignment(Pos.CENTER);
                ToggleGroup difficulty = new ToggleGroup();
                RadioButton rad1 = new RadioButton("12 Guesses - Easy");
                rad1.setUserData(EASY);
                rad1.setSelected(true);
                rad1.setTextFill(Color.WHITE);
                rad1.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                RadioButton rad2 = new RadioButton("9 Guesses - Medium");
                rad2.setUserData(MEDIUM);
                rad2.setTextFill(Color.WHITE);
                rad2.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
                RadioButton rad3 = new RadioButton("6 Guesses - Hard");
                rad3.setUserData(HARD);
                rad3.setTextFill(Color.WHITE);
                rad3.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));

                rad1.setToggleGroup(difficulty);
                rad2.setToggleGroup(difficulty);
                rad3.setToggleGroup(difficulty);

                difficulty.selectedToggleProperty().addListener(e -> {
                    guessesAllowed = Integer.parseInt(difficulty.getSelectedToggle().getUserData().toString());
                });

            radioPane.getChildren().addAll(rad1, rad2, rad3);

        
            FlowPane btnPane = new FlowPane(Orientation.HORIZONTAL);
            btnPane.setAlignment(Pos.CENTER);
                Button startBtn = new Button("Start Game");
                startBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        lengthOfDigits = Integer.parseInt(lenInputText.getText().toString());
                        if(lengthOfDigits<2||lengthOfDigits>9) {
                            lenLabel.setText("Enter a Length between");
                        }
                        else {
                            rootPane.getChildren().removeAll(centerPane, startBtn);
                            new GameHandler(stage, rootPane, cheaterMode, lengthOfDigits, guessesAllowed);
                        }
                    }
                    
                });
            btnPane.getChildren().add(startBtn);
        
        lenNDiffPane.getChildren().addAll(lenPane, radioPane, btnPane);
        centerPane.getChildren().addAll(lenNDiffPane);
    }
}