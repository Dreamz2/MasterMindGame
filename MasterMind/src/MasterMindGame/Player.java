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

    public void addArrayList() {
        userInputs.add(new ArrayList<>());
    }

    public void addInput(int input) {
        userInputs.get(gamesPlayed).add(input);
    }

    public int get(int index) {
        return userInputs.get(gamesPlayed).get(index);
    }

    public void remove() {
        userInputs.get(gamesPlayed).remove(userInputs.get(gamesPlayed).size()-1);
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
    public boolean duplicateInputs() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(userInputs.get(gamesPlayed).get(i) == userInputs.get(gamesPlayed).get(j) && i!=j)
                    return true;
            }
        }
        return false;
    }

    public void test() {
        
    }
    public void print() {
        System.out.println(userInputs.size() +" games played");
        System.out.println(userInputs.get(gamesPlayed).size() + " input things");
        for (int i = 0; i < userInputs.size(); i++) {
            for (int j = 0; j < userInputs.get(i).size(); j++) {
                System.out.print(userInputs.get(i).get(j));
            }
            System.out.println();
        }
    }


}
