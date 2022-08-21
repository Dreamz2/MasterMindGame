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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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

        FlowPane topPane = new FlowPane(Orientation.HORIZONTAL);
        topPane.setPadding(new Insets(15, 10, 10, 10));
        topPane.setAlignment(Pos.CENTER);

            Label message = new Label("Master Mind");
            message.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
            message.setTextFill(Color.WHITE);
        
        topPane.getChildren().add(message);

        new Menu(stage);

        rootPane.setTop(topPane);

        Scene scene = new Scene(rootPane, 500, 500);
		
		stage.setScene(scene);
		stage.setTitle("Master Mind");
		stage.show();
        
    }

    public class Menu implements Difficulty{
        
        private String[] mainLabels = {"Start", "Cheater Mode", "Exit"};
        private ArrayList<Label> initialMenu = new ArrayList<>();
        private int guessesAllowed = 12, lengthOfDigits = 2;
        private boolean cheaterMode = false;
        private VBox centerPane;

        Menu(Stage stage) {
            centerPane = new VBox(30);
            centerPane.setAlignment(Pos.CENTER);
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
                    rad1.setUserData(EASY);
                    rad1.setSelected(true);
                    rad1.setTextFill(Color.WHITE);
                    RadioButton rad2 = new RadioButton("9 Guesses - Medium");
                    rad2.setUserData(MEDIUM);
                    rad2.setTextFill(Color.WHITE);
                    RadioButton rad3 = new RadioButton("6 Guesses - Hard");
                    rad3.setUserData(HARD);
                    rad3.setTextFill(Color.WHITE);

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
                            lengthOfDigits = Integer.parseInt(lenText.getText().toString());
                            if(lengthOfDigits<2||lengthOfDigits>9) {
                                lenLabel.setText("Enter Length between 2 - 9");
                            }
                            else {
                                rootPane.getChildren().removeAll(centerPane, startBtn);
                                new GameHandler(lengthOfDigits, guessesAllowed);
                            }
                        }
                        
                    });
                btnPane.getChildren().add(startBtn);
            
            lenNDiffPane.getChildren().addAll(lenPane, radioPane, btnPane);
            centerPane.getChildren().addAll(lenNDiffPane);
        }
    }

    public class GameHandler{
        
        private int lengthOfDigits; // columns
        private int guessesAllowed; // rows
        private int attemptsMade;
        private VBox centerPane;
        private HBox inputsPane;
        private VBox hintPane;
        private ArrayList<TextField> tfList = new ArrayList<>();
        private int[] compList;
        private ArrayList<ArrayList<Integer>> userInputs = new ArrayList<>();

        GameHandler(int lengthOfDigits, int guessesAllowed) {
            this.lengthOfDigits = lengthOfDigits;
            this.guessesAllowed = guessesAllowed;
            attemptsMade = 0;
            compList = getRandom(lengthOfDigits);
            centerPane = new VBox(20);
            centerPane.setAlignment(Pos.CENTER);
            inputsPane = new HBox(5);
            inputsPane.setAlignment(Pos.CENTER);
            hintPane = new VBox(5);

            displayEnvironment();
            setHintsBox();

            rootPane.setCenter(centerPane);
        }

        public void displayEnvironment() {
            Label guessesLeftLabel = new Label("Guesses Left: " + (guessesAllowed-attemptsMade));

            Label message = new Label("Enter a random string of numbers of length "+lengthOfDigits);

            for(int i = 0; i < lengthOfDigits; i++) {
                TextField tf = new TextField();
                tf.setBorder(Border.stroke(Color.BLACK));
                tf.setMaxSize(30, 25);

                inputsPane.getChildren().add(tf);
                tfList.add(tf);
            }

            Button submitBtn = new Button("Submit");
            submitBtn.setOnAction(e -> {

                userInputs.add(new ArrayList<>());
                for(int i = 0; i < lengthOfDigits; i++) {
                    userInputs.get(attemptsMade).add(Integer.parseInt(tfList.get(i).getText().toString()));
                    tfList.get(i).setText("");
                }

                

                if(checkGuess()){
                    finishGame(true);
                }
                else if(attemptsMade==guessesAllowed) {
                    finishGame(false);
                }
                messages(message);
                attemptsMade++;
            });

            centerPane.getChildren().addAll(guessesLeftLabel, message, inputsPane, submitBtn);
        }

        private boolean checkGuess() {
            int correct = 0;
            
            for(int i=0; i<lengthOfDigits; i++){
                if(userInputs.get(attemptsMade).get(i)==compList[i])
                    correct++;
            }
    
            return(correct==lengthOfDigits);
        }

        private int[] getRandom(final int length) {
            int[] list = new int[length];
            int num;
    
            for(int i=0; i<length; i++){
                int x = 0;
                num = (int)(Math.random()*10);
                while(x<i){
                    if(list[x]==num){
                        num = (int)(Math.random()*10);
                        x=0;
                    }
                    else
                        x++;
                }
                list[i] = num;
            }
            
            return list;
        }

        private void messages(Label message) {
            if(attemptsMade==0){
                message.setText("Welcome to Master Mind\n"
                                + "Each digit in the hidden set of digits are unique from the rest and are in random positions");
            }
            else if(attemptsMade==3||attemptsMade==6||attemptsMade>9){
                centerPane.getChildren().removeAll();

                Label hint = new Label("Do you want a hint?");

                HBox ynPane = new HBox(20);
                Button yes = new Button();
                yes.setOnAction(e -> {
                    giveAHint();
                    centerPane.getChildren().removeAll();
                    displayEnvironment();
                });
                Button no = new Button();
                no.setOnAction(e -> {
                    centerPane.getChildren().removeAll();
                    displayEnvironment();
                });
                ynPane.getChildren().addAll(yes, no);
                centerPane.getChildren().addAll(hint, ynPane);
            }
        }

        private void giveAHint() {
            final int MAX_THRESHOLDGUESSES = 9;
            final int MAX_THRESHOLDLENGTH = 6;
            System.out.println();

            final int num1 = (int)(Math.random()*lengthOfDigits);
            int num2 = (int)(Math.random()*lengthOfDigits);
            final int choice = (int)(Math.random()*10);
            if(choice<=7){
                //Give two random digits in compList
                if(attemptsMade>MAX_THRESHOLDGUESSES&&lengthOfDigits>=MAX_THRESHOLDLENGTH){
                    while(num1==num2)
                        num2 = (int)(Math.random()*lengthOfDigits);
                    System.out.println(compList[num1]+" and "+compList[num2]+ " is in the Computers list");
                }
                //Give hint of a digit and the place it is in
                else if(attemptsMade>MAX_THRESHOLDGUESSES)
                    System.out.println(compList[num1]+" is in index "+num1);
                //Give a random digit in compList
                else
                    System.out.println(compList[num1]+" is in the computers list");
            }
            else{
                int sum = 0;
                for (int i : compList) {
                    sum+=i;
                }
                System.out.println("The check sum of the Computers digits is: "+sum);
            }
        }

        private void setHintsBox() {


            rootPane.setLeft(hintPane);
        }

        private void finishGame(boolean wOrL) {
            
        }


    }
    
}