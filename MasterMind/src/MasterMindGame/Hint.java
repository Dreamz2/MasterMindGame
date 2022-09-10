package src.MasterMindGame;

public class Hint {
    private String hint;

    Hint() {
        hint = "";
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        if(!this.hint.isEmpty()){
            this.hint += "\n";
        }
        this.hint += hint;
    }

    // private void giveAHint(Computer compList) {
    //     final int MAX_THRESHOLDGUESSES = 9;
    //     final int MAX_THRESHOLDLENGTH = 6;
    //     System.out.println();

    //     final int num1 = (int)(Math.random()*lengthOfDigits);
    //     int num2 = (int)(Math.random()*lengthOfDigits);
    //     final int choice = (int)(Math.random()*10);
    //     if(choice<=7){
    //         //Give two random digits in compList
    //         if(attemptsMade>MAX_THRESHOLDGUESSES&&lengthOfDigits>=MAX_THRESHOLDLENGTH){
    //             while(num1==num2)
    //                 num2 = (int)(Math.random()*lengthOfDigits);
    //             System.out.println(compList[num1]+" and "+compList[num2]+ " is in the Computers list");
    //         }
    //         //Give hint of a digit and the place it is in
    //         else if(attemptsMade>MAX_THRESHOLDGUESSES)
    //             System.out.println(compList[num1]+" is in index "+num1);
    //         //Give a random digit in compList
    //         else
    //             System.out.println(compList[num1]+" is in the computers list");
    //     }
    //     else{
    //         int sum = 0;
    //         for (int i : compList) {
    //             sum+=i;
    //         }
    //         System.out.println("The check sum of the Computers digits is: "+sum);
    //     }
    // }
}
