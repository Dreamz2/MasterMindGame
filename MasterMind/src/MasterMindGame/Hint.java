package src.MasterMindGame;

public class Hint {
    private String hint;

    Hint() {
        hint = "";
    }

    public String getHint() {
        return hint;
    }

    public void giveAHint(int[] compList, int lengthOfDigits, int attemptsMade) {
        final int MAX_THRESHOLDGUESSES = 9; // Difficultly
        final int MAX_THRESHOLDLENGTH = 6; // Length of digits needed to guess it over 6

        final int num1 = (int)(Math.random()*lengthOfDigits);
        int num2 = (int)(Math.random()*lengthOfDigits);
        final int choice = (int)(Math.random()*10);
        if(choice<=7){
            //Give two random digits in compList
            if(attemptsMade>MAX_THRESHOLDGUESSES&&lengthOfDigits>=MAX_THRESHOLDLENGTH){
                while(num1==num2)
                    num2 = (int)(Math.random()*lengthOfDigits);
                    hint += compList[num1]+" and "+compList[num2]+ " is in the Computers list\n";
            }
            //Give hint of a digit and the place it is in
            else if(attemptsMade>MAX_THRESHOLDGUESSES)
                    hint += compList[num1]+" is in index "+num1 +"\n";
            //Give a random digit in compList
            else
                    hint += compList[num1]+" is in the computers list\n";
        }
        else{
            int sum = 0;
            for (int i : compList) {
                sum+=i;
            }
                hint += "The sum of the Computers digits is: "+sum +"\n";
        }
    }
}
