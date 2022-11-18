package src.MasterMindGame;

import java.util.ArrayList;

public class Player {
    private ArrayList<ArrayList<Integer>> userInputs;
    private int gamesPlayed;
    private int difficulty; // Number of guesses allowed (guessesAllowed)
    private int length; // Number of digits to guess (lengthOfDigits)
    private int attemptsMade; // Number of attempts made to guest the Computers list

    Player(int difficulty, int lengthOfDigits) {
        userInputs = new ArrayList<>();
        gamesPlayed = 0;
        this.difficulty = difficulty;
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

    public int getGuessesAllowed() {
        return difficulty;
    }

    public int getLength() {
        return length;
    }

    public void gamesPalyed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }


    public void test() {
        System.out.println("TEST");
    }

    public void print() {
        System.out.println(userInputs.size() +" games played");
        System.out.println(getAttemptsMade() + " Attempts made");
        System.out.println(userInputs.get(attemptsMade).size() + " input things");
        for (int i = 0; i < userInputs.size(); i++) {
            for (int j = 0; j < userInputs.get(i).size(); j++) {
                System.out.print(userInputs.get(i).get(j));
            }
            System.out.println();
        }
    }
    public int size1() {
        return userInputs.size();
    }
    public int size2() {
        return userInputs.get(attemptsMade).size();
    }
    public int get2(int i, int j) {
        return userInputs.get(i).get(j);
    }


}
