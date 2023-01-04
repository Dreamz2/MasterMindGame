package src.MasterMindGame;

import java.util.ArrayList;

public class PlayerNComp {

    private ArrayList<Computer> compList;
    private ArrayList<Player> player;   // ArrayList 
    private int gamesPlayed;
    private int guessesAllowed; // Number of guesses allowed (Difficulty)

    PlayerNComp(int guessesAllowed, int lengthOfDigits) {
        gamesPlayed = 0;
        this.guessesAllowed = guessesAllowed;
        player = new ArrayList<>();
        compList = new ArrayList<>();
        addNewGame(lengthOfDigits);
    }

    /**
     * Adds a new intance of Player class into the ArrayList with guessesAllowed and lengthOfDigits
     */
    public void addNewGame(int lengthOfDigits) {
        player.add(new Player(lengthOfDigits));
        compList.add(new Computer(lengthOfDigits));
    }

    public int[] getCompList() {
        return compList.get(gamesPlayed).getCompList();
    }

    public int getCompDigit(int index) {
        return compList.get(gamesPlayed).get(index);
    }

    public void addPlayerList() {
        player.get(gamesPlayed).addList();
    }

    public void addPlayerInput(int input) {
        player.get(gamesPlayed).addInput(input);
    }

    public int getGuessesAllowed() {
        return guessesAllowed;
    }

    public int getLength() {
        return player.get(gamesPlayed).getLength();
    }

    public int getAttemptsMade() {
        return player.get(gamesPlayed).getAttemptsMade();
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addAttemptsMade() {
        player.get(gamesPlayed).addAttemptsMade();
    }

    public void createNewGame(int lengthOfDigits) {
        gamesPlayed+=1;
        addNewGame(lengthOfDigits);
    }
    
    public boolean checkGuess() {
        int correct = 0;
        
        for(int i=0; i< getLength(); i++){
            // if(player.get(i + lengthOfDigits*(attemptsMade-1))==compList.get(i))
            if(player.get(gamesPlayed).get(i)==compList.get(gamesPlayed).get(i))
                correct++;
            // System.out.println(player.get(i + lengthOfDigits*(attemptsMade-1)) + ", " + compList.get(i));
            System.out.println(player.get(gamesPlayed).get(i) + ", " + compList.get(gamesPlayed).get(i));
        }
        
        addAttemptsMade();
        return(correct==player.get(gamesPlayed).getLength());
    }

    public boolean duplicateInputs() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getLength(); j++) {
                if(player.get(gamesPlayed).get(i)==player.get(gamesPlayed).get(j)&& i!=j)
                    return true;
            }
        }
        return false;
    }

    public void removeList() {
        player.get(gamesPlayed).remove();
    }
    
    //

    public void test() {
        player.get(gamesPlayed).test();
    }

    public void print() {
        System.out.println(player.size() +" games played");
        System.out.println(gamesPlayed + " games played");
        System.out.println(player.get(gamesPlayed).getAttemptsMade() + " Attempts made");
        System.out.println(player.get(gamesPlayed).size1() + " Thing size");
        System.out.println(player.get(gamesPlayed).size2() + " input size");
        System.out.println(player.get(gamesPlayed).getLength() + " Length of digits");
        System.out.println(guessesAllowed + " Guess allowed");
        for (int i = 0; i < player.get(gamesPlayed).size1(); i++) {
            for (int j = 0; j < player.get(gamesPlayed).size2(); j++) {
                System.out.print(player.get(gamesPlayed).get2(i, j));
            }
            System.out.println();
        }
    }

}
