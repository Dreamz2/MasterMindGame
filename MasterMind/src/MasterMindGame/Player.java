package src.MasterMindGame;

import java.util.ArrayList;

public class Player {
    private ArrayList<ArrayList<Integer>> userInputs;
    private int gamesPlayed;
    private int difficulty; // Number of guesses allowed
    private int length; // Number of digits to guess
    private int attemptsMade; // At the end get the number of attempts needed to solve the computers list or not

    Player(int difficulty, int lengthOfDigits) {
        userInputs = new ArrayList<>();
        gamesPlayed = 0;
        this.difficulty = difficulty;
        length = lengthOfDigits;
    }

    public void addInputs(int input) {
        userInputs.get(gamesPlayed).add(input);
    }
    public void addArrayList() {
        userInputs.add(new ArrayList<>());
    }

    public int get(int index) {
        return userInputs.get(gamesPlayed).get(index);
    }

    public int getAttempt() {
        return attemptsMade;
    }

    public void setAttempt(int attemptsMade) {
        this.attemptsMade = attemptsMade;
    }

    public void newGame() {
        gamesPlayed += 1;
    }

    public void test() {
        
    }

}
