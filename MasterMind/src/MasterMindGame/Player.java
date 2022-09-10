package src.MasterMindGame;

import java.util.ArrayList;

public class Player {
    private ArrayList<Integer> userInputs;
    private int length;
    private int attemptsMade;

    Player(int lengthOfDigits) {
        userInputs = new ArrayList<>();
        attemptsMade = 0;
    }

    public void addInputs(int input) {
        userInputs.add(input);
    }

    public int get(int index) {
        return userInputs.get(index);        
    }

    public int getAttempt() {
        return attemptsMade;
    }

    public void addAttempt() {
        attemptsMade+= 1;
    }

    public void test() {
        
    }

}
