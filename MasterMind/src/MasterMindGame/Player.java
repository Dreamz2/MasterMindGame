package src.MasterMindGame;

import java.util.ArrayList;

public class Player {
    private ArrayList<Integer> userInputs;
    private int length;

    Player(int lengthOfDigits) {
        userInputs = new ArrayList<>();

    }

    public void addInputs(int input) {
        userInputs.add(input);
    }

    public int get(int index) {
        return userInputs.get(index);        
    }

    public void test() {
        
    }

}