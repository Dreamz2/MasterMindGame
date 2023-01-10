package src.MasterMindGame;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class GameHandler{
        
    private Stage stage;
    private BorderPane rootPane;
    private boolean cheaterMode;
    private VBox centerPane;
    private Pane bottomPane;
    private HBox inputsPane;
    private Text hints;
    private Hint Hint;
    private Pane hintPane;
    private ArrayList<TextField> tfList = new ArrayList<>();
    private PlayerNComp playerVsComp;

    GameHandler(Stage stage, BorderPane rootPane, boolean cheaterMode, int lengthOfDigits, int guessesAllowed) {
        this.stage = stage;
        this.rootPane = rootPane;
        this.cheaterMode = cheaterMode;
        playerVsComp = new PlayerNComp(guessesAllowed, lengthOfDigits);
        Hint = new Hint();
        centerPane = new VBox(20);
        centerPane.setAlignment(Pos.CENTER);
        inputsPane = new HBox(10);
        inputsPane.setAlignment(Pos.CENTER);
        hints = new Text();

        displayEnvironment();
        setHintsBox();
        if(cheaterMode)
            cheaterMode();

        rootPane.setCenter(centerPane);
    }

    private void displayEnvironment() {
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

            if(!isemptyNisLetter()){
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
                        System.out.println("Congratulations");
                    }
                    else if(playerVsComp.getAttemptsMade()==playerVsComp.getGuessesAllowed()) {
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
                    System.out.println("Duplicate");
                    playerVsComp.removeList();
                    duplicate(message);
                }
            }
        });
    }

    private boolean isemptyNisLetter() {
        for (TextField textField : tfList) {
            try {
                char num = textField.getText().toString().charAt(0);
                Character.isDigit(num);
                Character.isWhitespace(num);
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }


    private void messages(Label message) {
        if(playerVsComp.getAttemptsMade()==0&&playerVsComp.getGamesPlayed()==0){
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

    private void cheaterMode() {
        bottomPane = new Pane();
        bottomPane.setLayoutX(100);
        bottomPane.setLayoutY(520);
        
        Label compListLabel = new Label("Computers Digits");
        compListLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        compListLabel.setTextFill(Color.WHITE);
        compListLabel.setTextAlignment(TextAlignment.CENTER);
        compListLabel.setLayoutX(150);

        HBox displayDigits = new HBox(10);
        displayDigits.setLayoutX(10+155/(playerVsComp.getLength()-1));
        displayDigits.setLayoutY(25);
        for (int i = 0; i < playerVsComp.getLength(); i++) {
            TextField compNumbers = new TextField("" + playerVsComp.getCompDigit(i));
            compNumbers.setEditable(false);
            compNumbers.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14));
            compNumbers.setStyle("-fx-text-fill: white; -fx-background-color: rgb(104, 104, 115); -fx-border-color: black");
            compNumbers.setMaxSize(30, 25);
            displayDigits.getChildren().add(compNumbers);
        }

        bottomPane.getChildren().addAll(compListLabel, displayDigits);
        rootPane.getChildren().add(bottomPane);
    }

    private void askHint() {
        centerPane.getChildren().clear();
        inputsPane.getChildren().clear();
        tfList.clear();

        Label hint = new Label("Do you want a hint?");
        // hint.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 15));
        // hint.setTextFill(Color.WHITE);
        // hint.setTextAlignment(TextAlignment.CENTER);

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
        hintPane = new Pane();
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
        if(bottomPane!=null)
            bottomPane.getChildren().clear();
        Hint.clearHint();
        
        Label EndOfGameMessage = new Label();
        EndOfGameMessage.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 28));
        EndOfGameMessage.setTextFill(Color.WHITE);

        if(finalResult) 
            EndOfGameMessage.setText("Congratulations You Win");
        else
            EndOfGameMessage.setText("You Lose");
        
        Label PlayAgain = new Label("Play again?");
        PlayAgain.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        PlayAgain.setTextFill(Color.WHITE);

        Pane scorePane = endScreenScore();

        HBox ynPane = new HBox(20);
        ynPane.setAlignment(Pos.CENTER);

            Button yes = new Button("Yes");
            yes.setOnAction(new TryAginYes());

            Button no = new Button("No");
            no.setOnAction(new TryAginNo());

        ynPane.getChildren().addAll(yes, no);
        centerPane.getChildren().addAll(EndOfGameMessage, scorePane, PlayAgain, ynPane);
    }

    private Pane endScreenScore() {
        Pane scorePane = new Pane();

        Label score = new Label();
        score.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
        score.setStyle("-fx-text-fill: white;");
        score.setTextAlignment(TextAlignment.CENTER);
        score.maxWidth(300);
        score.setLayoutX(240);
        score.setText(playerVsComp.finalScore());

        scorePane.getChildren().add(score);
        return scorePane;
    }

    private void LengthPane() {
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
            
                FlowPane btnPane = new FlowPane(Orientation.HORIZONTAL);
                btnPane.setAlignment(Pos.CENTER);
                    Button startBtn = new Button("Start Game");
                    startBtn.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent arg0) {
                            int lengthOfDigits = Integer.parseInt(lenInputText.getText().toString());
                            if(lengthOfDigits<2||lengthOfDigits>9) {
                                lenLabel.setText("Enter a Length between");
                            }
                            else {
                                centerPane.getChildren().clear();
                                playerVsComp.createNewGame(lengthOfDigits);
                                displayEnvironment();
                                setHintsBox();
                                if(cheaterMode)
                                    cheaterMode();
                            }
                        }
                        
                    });
                btnPane.getChildren().add(startBtn);


            lenPane.getChildren().addAll(lenLabel, lenToEnter, lenInputPane);
        
        lenNDiffPane.getChildren().addAll(lenPane, btnPane);
        centerPane.getChildren().addAll(lenNDiffPane);
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
            LengthPane();
        }
    }
    class TryAginNo implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent e) {
            centerPane.getChildren().clear();
            rootPane.getChildren().clear();
            new MainMenu(stage, rootPane);
        }
    }

}