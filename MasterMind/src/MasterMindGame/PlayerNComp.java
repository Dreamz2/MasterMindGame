package src.MasterMindGame;

import java.util.ArrayList;

public class PlayerNComp {

    private Computer compList;
    private ArrayList<Player> player;   // ArrayList 
    private int gamesPlayed;

    PlayerNComp(int guessesAllowed, int lengthOfDigits) {
        player = new ArrayList<>();
        player.add(new Player(guessesAllowed, lengthOfDigits));
        compList = new Computer(lengthOfDigits);
        gamesPlayed = 0;
    }

    public int[] getCompList() {
        return compList.getCompList();
    }

    public void addPlayerList() {
        player.get(gamesPlayed).addList();
    }

    public void addPlayerInput(int input) {
        player.get(gamesPlayed).addInput(input);
    }

    public int getGuessesAllowed() {
        return player.get(gamesPlayed).getGuessesAllowed();
    }

    public int getLength() {
        return player.get(gamesPlayed).getLength();
    }

    public void addAttemptsMade() {
        player.get(gamesPlayed).addAttemptsMade();
    }
    
    public boolean checkGuess() {
        int correct = 0;
        
        for(int i=0; i< player.get(gamesPlayed).getLength(); i++){
            // if(player.get(i + lengthOfDigits*(attemptsMade-1))==compList.get(i))
            if(player.get(gamesPlayed).get(i)==compList.get(i))
                correct++;
            // System.out.println(player.get(i + lengthOfDigits*(attemptsMade-1)) + ", " + compList.get(i));
            System.out.println(player.get(gamesPlayed).get(i) + ", " + compList.get(i));
        }

        return(correct==player.get(gamesPlayed).getLength());
    }

    public boolean duplicateInputs() {
        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getLength(); j++) {
                if(player.get(gamesPlayed).get(i)== player.get(gamesPlayed).get(j)&& i!=j)
                    return true;
            }
        }
        return false;
    }
    

    public void test() {
        player.get(gamesPlayed).test();
    }

    public void print() {
        System.out.println(player.size() +" games played");
        System.out.println(player.get(gamesPlayed).getAttemptsMade() + " Attempts made");
        System.out.println(player.get(gamesPlayed).size1() + " Thing size");
        System.out.println(player.get(gamesPlayed).size2() + " input size");
        for (int i = 0; i < player.get(gamesPlayed).size1(); i++) {
            for (int j = 0; j < player.get(gamesPlayed).size2(); j++) {
                System.out.print(player.get(gamesPlayed).get2(i, j));
            }
            System.out.println();
        }
    }

}
