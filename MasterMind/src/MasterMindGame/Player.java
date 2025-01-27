package src.MasterMindGame;

import java.util.ArrayList;

public class Player {
    private ArrayList<ArrayList<Integer>> userInputs;
    private int length; // Number of digits to guess (lengthOfDigits)
    private int attemptsMade; // Number of attempts made to guest the Computers list

    Player(int lengthOfDigits) {
        userInputs = new ArrayList<>();
        length = lengthOfDigits;
    }

    /**
     * Adds a new ArrayList into userInputs
     */
    public void addList() {
        userInputs.add(new ArrayList<>());
    }

    /**
     * Gets the current attempt and adds the players input into it
     */
    public void addInput(int input) {
        userInputs.get(attemptsMade).add(input);
    }

    /**
     * Returns an int at attemptsMade and at a specified index
     */
    public int get(int index) {
        return userInputs.get(attemptsMade).get(index);
    }

    /**
     * Removes the last array in userInputs
     * If a duplicate is found
     */
    public void remove() {
        userInputs.remove(userInputs.size()-1);
    }

    public int getAttemptsMade() {
        return attemptsMade;
    }

    public void addAttemptsMade() {
        attemptsMade+=1;
    }

    public int getLength() {
        return length;
    }
}
