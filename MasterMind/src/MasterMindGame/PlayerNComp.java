package src.MasterMindGame;

import java.util.ArrayList;

public class PlayerNComp {

    private Computer compList;
    private ArrayList<Player> player;   // ArrayList 
    private int gamesPlayed;

    PlayerNComp(int guessesAllowed, int lengthOfDigits) {
        player.add(new Player(guessesAllowed, lengthOfDigits));
        compList = new Computer(lengthOfDigits);
        gamesPlayed = 0;
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

    public void test() {
        player.get(gamesPlayed).test();
    }

}
