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
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

        new Menu(stage);

        Scene scene = new Scene(rootPane, 600, 600);
		
		stage.setScene(scene);
		stage.setTitle("MasterMind");
		stage.show();
        
    }

    public class Menu implements Difficulty{
        
        private String[] mainLabels = {"Start", "Cheater Mode", "Exit"};
        private ArrayList<Label> initialMenu = new ArrayList<>();
        private int guessesAllowed = 12, lengthOfDigits = 2;
        private boolean cheaterMode = false;
        private VBox centerPane;
        private Stage stage;

        Menu(Stage stage) {
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

    public class GameHandler{
        
        private Stage stage;
        private BorderPane rootpane;
        private boolean cheaterMode;
        private VBox centerPane;
        private HBox inputsPane;
        private Text hints;
        private Hint Hint;
        private Pane hintPane;
        private ArrayList<TextField> tfList = new ArrayList<>();
        private PlayerNComp playerVsComp;

        GameHandler(Stage stage, BorderPane rootpane, boolean cheaterMode, int lengthOfDigits, int guessesAllowed) {
            this.stage = stage;
            this.cheaterMode = cheaterMode;
            playerVsComp = new PlayerNComp(guessesAllowed, lengthOfDigits);
            Hint = new Hint();
            hintPane = new Pane();
            centerPane = new VBox(20);
            centerPane.setAlignment(Pos.CENTER);
            inputsPane = new HBox(5);
            inputsPane.setAlignment(Pos.CENTER);
            hints = new Text();

            displayEnvironment();
            setHintsBox();

            rootPane.setCenter(centerPane);
        }

        public void displayEnvironment() {
            Label guessesLeftLabel = new Label("Guesses Left: " + (playerVsComp.getGuessesAllowed()-playerVsComp.getAttemptsMade()));
            guessesLeftLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
            guessesLeftLabel.setTextFill(Color.WHITE);

            Label message = new Label();
            message.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
            message.setTextFill(Color.WHITE);
            message.setTextAlignment(TextAlignment.CENTER);
            messages(message);

            for(int i = 0; i < playerVsComp.getLength(); i++) {
                TextField tf = new TextField();
                tf.setBorder(Border.stroke(Color.BLACK));
                tf.setMaxSize(30, 25);

                inputsPane.getChildren().add(tf);
                tfList.add(tf);
            }

            Button submitBtn = new Button("Submit");
            centerPane.getChildren().addAll(guessesLeftLabel, message, inputsPane, submitBtn);
            submitBtn.setOnAction(e -> {

                playerVsComp.addPlayerList();
                for(int i = 0; i < playerVsComp.getLength(); i++) {
                    playerVsComp.addPlayerInput(tfList.get(i).getText().toString().charAt(0) - '0');
                    System.out.println(tfList.get(i).getText().toString() + " test ");
                    tfList.get(i).setText("");
                    // System.out.println(userInputs.get(attemptsMade).get(i));
                }
                playerVsComp.print();
                if(!playerVsComp.duplicateInputs()) {
                    if(playerVsComp.checkGuess()){
                        finishGame(true);
                        System.out.println("Nice");
                    }
                    playerVsComp.addAttemptsMade();
                    if(playerVsComp.getAttemptsMade()==playerVsComp.getGuessesAllowed()) {
                        finishGame(false);
                        System.out.println(playerVsComp.getAttemptsMade());
                    }
                    else {
                        if(playerVsComp.getAttemptsMade()==3||playerVsComp.getAttemptsMade()==6||playerVsComp.getAttemptsMade()>=9){
                            askHint();
                        }
                        messages(message);
                        guessesLeftLabel.setText("Guesses Left: " + (playerVsComp.getGuessesAllowed()-playerVsComp.getAttemptsMade()));
                    }
                }
                else {
                    System.out.println("You suck");
                    playerVsComp.removeList();
                    duplicate(message);
                }

            });
        }

        private void messages(Label message) {
            if(playerVsComp.getAttemptsMade()==0){
                message.setText("Welcome to Master Mind"
                                + "\nEach digit in the hidden set of digits are unique \nfrom the rest and are in random positions."
                                + "\nStart by entering a digit into each box all different \nfrom one another.");
            }
            else {
                message.setText("Enter a digit into each box all \n different from one another."); //digits
            }
        }
        private void duplicate(Label message) {
            message.setText("Enter digits without any duplicates");
        }

        private void askHint() {
            centerPane.getChildren().clear();
            inputsPane.getChildren().clear();
            tfList.clear();

            Label hint = new Label("Do you want a hint?");

            HBox ynPane = new HBox(20);
            ynPane.setAlignment(Pos.CENTER);

            Button yes = new Button("Yes");
            yes.setOnAction(new HintYes());

            Button no = new Button("No");
            no.setOnAction(new HintNo());

            ynPane.getChildren().addAll(yes, no);
            centerPane.getChildren().addAll(hint, ynPane);
        }

        private void setHintsBox() {
            hintPane.setLayoutX(15);
            hintPane.setLayoutY(160);
            
            Label hintTitle = new Label("Hints");
            hintTitle.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.REGULAR, 10));
            hintTitle.setTextFill(Color.WHITE);
            hintTitle.setLayoutX(15);
            hintTitle.setAlignment(Pos.CENTER);

            Pane hintMessagePane = new Pane();
            hintMessagePane.setStyle("-fx-border-color: white");
            hintMessagePane.setBackground(Background.fill(Color.BLANCHEDALMOND));
            hintMessagePane.setLayoutX(-10);
            hintMessagePane.setLayoutY(20);
            hintMessagePane.setMinSize(80, 150);
            hintMessagePane.setMaxSize(90, 200);

            hints.setText(Hint.getHint());
            hints.setLayoutY(10);
            hints.setWrappingWidth(90);

            hintMessagePane.getChildren().add(hints);
            hintPane.getChildren().addAll(hintTitle, hintMessagePane);

            rootPane.getChildren().add(hintPane);
        }

        private void finishGame(boolean finalResult) {
            centerPane.getChildren().clear();
            inputsPane.getChildren().clear();
            tfList.clear();
            hintPane.getChildren().clear();
            
            Label EndOfGameMessage = new Label();
            EndOfGameMessage.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 28));
            EndOfGameMessage.setTextFill(Color.WHITE);

            if(finalResult) {
                EndOfGameMessage.setText("Congratulations You Win");
            }
            else {
                EndOfGameMessage.setText("You Lose");
            }
            Label PlayAgain = new Label("Do you want to play again?");
            PlayAgain.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
            PlayAgain.setTextFill(Color.WHITE);

            HBox ynPane = new HBox(20);
            ynPane.setAlignment(Pos.CENTER);

                Button yes = new Button("Yes");
                yes.setOnAction(new TryAginYes());

                Button no = new Button("No");
                no.setOnAction(new TryAginNo());

            ynPane.getChildren().addAll(yes, no);
            centerPane.getChildren().addAll(EndOfGameMessage, PlayAgain, ynPane);
        }


        class HintYes implements EventHandler<ActionEvent>{

            @Override
            public void handle(ActionEvent e) {
                centerPane.getChildren().clear();
                Hint.giveAHint(playerVsComp.getCompList(), playerVsComp.getLength(), playerVsComp.getAttemptsMade());
                hints.setText(Hint.getHint());
                displayEnvironment();
            }
        }

        class HintNo implements EventHandler<ActionEvent>{

            @Override
            public void handle(ActionEvent e) {
                centerPane.getChildren().clear();
                displayEnvironment();
            }
        }

        class TryAginYes implements EventHandler<ActionEvent>{

            @Override
            public void handle(ActionEvent e) {
                centerPane.getChildren().clear();
                displayEnvironment();
            }
        }
        class TryAginNo implements EventHandler<ActionEvent>{

            @Override
            public void handle(ActionEvent e) {
                centerPane.getChildren().clear();
                rootPane.getChildren().clear();
                new Menu(stage);
            }
        }

    }


    
}