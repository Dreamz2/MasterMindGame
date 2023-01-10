package src.MasterMindGame;

/**
 * Produces a random preset message for the player to read and to be
 * used for completing the game
 */
public class Hint {
    private String hint;

    Hint() {
        hint = "";
    }

    /**
     * Gives the messa
     * @return a String that contains a set message for the player
     */
    public String getHint() {
        return hint;
    }

    /**
     * Clears the hint variable when the player request to play another 
     * game
     */
    public void clearHint() {
        hint = "";
    }

    /**
     * When the player requests for a hint. This method
     * randomly chooses a message from one of the 3 preset hints.
     * @param compList Array of the computers digits
     * @param lengthOfDigits an int with the number of digits to be guessed
     * @param attemptsMade an int that contains number of attempts made
     */
    public void giveAHint(int[] compList, int lengthOfDigits, int attemptsMade) {
        final int MAX_THRESHOLDGUESSES = 9; // Difficultly
        final int MAX_THRESHOLDLENGTH = 6; // Length of digits needed to guess if over 6

        final int num1 = (int)(Math.random()*lengthOfDigits);
        int num2 = (int)(Math.random()*lengthOfDigits);
        final int choice = (int)(Math.random()*10);
        if(choice<=7){
            //Give two random digits in compList
            if(attemptsMade>MAX_THRESHOLDGUESSES&&lengthOfDigits>=MAX_THRESHOLDLENGTH){
                while(num1==num2)
                    num2 = (int)(Math.random()*lengthOfDigits);
                hint += compList[num1]+" and "+ compList[num2] + " is in the Computers list\n";
            }
            //Give hint of a digit and the place it is in
            else if(attemptsMade>MAX_THRESHOLDGUESSES)
                    hint += compList[num1]+" is in position "+ (num1+1) +"\n";
            //Give a random digit in compList
            else
                    hint += compList[num1]+" is in the computers list\n";
        }
        else{
            int sum = 0;
            for (int i : compList) {
                sum+=i;
            }
                hint += "The sum of the Computers digits is: "+ sum +"\n";
        }
    }
}
