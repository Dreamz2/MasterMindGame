/*
Name: Edison Wei   Student Number: 100374523
Course: CPSC 1150-003
Purpose: Like the game Mastermind but that uses colours.
            This game will be using random different numbers.
*/
import java.util.Scanner;

/**
 * Mastermind game
 * @author Edison Wei
 * @since March 27, 2022
 */
public class Mastermind {
    static Scanner keyboard = new Scanner(System.in);
    public static void main(String[] args) {

        printIdentification();
        gameHandler();
    }
    
    /**
     * Prints My Idenitfication
     * To show it is me who wrote the code
     */
    private static void printIdentification(){
        System.out.println("Name: Edison Wei   Student Number: 100374523");
        System.out.println("Course: CPSC 1150-003");
        System.out.println("ASSIGNMENT #5 – Master Mind");
        System.out.println();
    }
    /**
     * Main menu to the Mastermind game can used to Start
     * enter Cheater mode and Exit. Also, after choosing 
     * Start or Cheater you will be asked to choose a difficulty
     * Easy   = 12 Guesses
     * Medium = 9 Guesses
     * Hard   = 6 Guesses
     * Then starts the gameTime method
     */
    private static void gameHandler() {
        boolean cheater = false;
        char check;

        do{
            check = menu();
            if(wantToPlay(check)){
                if(cheatOnOff(check))
                    cheater=true;
                else
                    cheater=false;
                gameTime(cheater,difficulty());
            }

        }while(wantToPlay(check));

    }
    /**
     * Displays a Menu like output 
     * for the user to choose from
     * @return a char the user enters
     */
    private static char menu() {
            System.out.println("(Y) - Start");
            System.out.println("(C) - Cheater Mode");
            System.out.println("(E) - Exit");
        return keyboard.next().toUpperCase().charAt(0);
    }
    /**
     * Checks to see if the entered user input is 'S','Y' or 'C'
     * @param check a char from user input to be checked 
     * @return True if user wants to start the game 
     */
    private static boolean wantToPlay(char check) {
        return(check=='S'||check=='Y'||check=='C');
    }
    /**
     * Checks to see if the user wants to have cheater mode enabled
     * @param check a char from user input to be checked 
     * @return True if user wants to enable Cheater mode
     */
    private static boolean cheatOnOff(char check) {
        return(check=='C');
    }
    /**
     * Displays a Menu like output for the
     * different difficulties the user can choose from
     * @return a char with the user input of the difficultly they want
     */
    private static int difficulty() {
        System.out.println();
        System.out.println("What difficulty do you want to play");
        System.out.println("12 Guesses  - (E)asy");
        System.out.println("9  Guesses  - (M)edium");
        System.out.println("6  Guesses  - (H)ard");
        return convertdiff(keyboard.next().toUpperCase().charAt(0));
    }
    /**
     * Converts the difficulty they want from char
     * to int and returns a number. If user input does not equal to
     * 'E', 'M' or 'H' then defaults to 12
     * @param diff a char with the difficulty the user wants
     * @return a int with the number that equals the difficulty
     */
    private static int convertdiff(char diff) {
        switch(diff){
            case 'E':
                return(12);
            case 'M':
                return(9);
            case 'H':
                return(6);
            default:
                return(12);
        }
    }

    /**
     * 
     * @param cheater a boolean if true digits for computer gets shown
     * @param numGuesses a int with a number of guess the user wants 12, 9, 6
     */
    private static void gameTime(boolean cheater,final int numGuessesAllowed) {
        boolean quits = false;
        int numberOfGamesPlayed = 0;
        //The amount of guesses made throughout all the games played
        int numberOfGuessesmade = 0;

        while(!quits){
            int numberOfAttempts = 0;
            //Ask the user how many number they want to guess
            System.out.print("How many digits do you want to be guessed? (not more than 10, but at least 2.) ");
            int howMany = keyboard.nextInt();
            //Checks to see if the number put in meets the requirments
            if(!checkDigit(howMany)){
                System.out.println("Enter a digit again (not more than 10, but at least 2.)");
                howMany = keyboard.nextInt();
            }

            //intialized array list and  Gets random numbers and puts in random arrays
            int[] compList = getRandom(howMany);
            int[] playerList = new int[howMany];

            if(cheater==true)
                printList(compList, howMany);
            
            //Starts the Master Mind game
            while(numberOfAttempts<numGuessesAllowed&&quits!=true){
                message(numberOfAttempts,howMany,compList,playerList);
                String guess = keyboard.next();
                while(!checkInput(guess,howMany)){
                    System.out.println("Enter a random string of numbers again with a length of "+howMany);
                    guess = keyboard.next();
                    System.out.println();
                }
                if(checkGuess(guess, howMany, compList, playerList)){
                    congratulation(numberOfAttempts,compList);
                    quits = true;
                }
                else
                    howManyCorrect(howMany,compList,playerList);
                
                numberOfAttempts++;
            }

            if(numberOfAttempts==numGuessesAllowed)
                compWins(compList);
            numberOfGuessesmade+=numberOfAttempts;
            numberOfGamesPlayed++;
            quits = checkQuit();
        }
        averagePerformance(numberOfGuessesmade,numberOfGamesPlayed);
    }

    /**
     * Prints the computers list of digits
     * if Cheater mode is activated
     * @param list a int array that stores the computers numbers
     * @param length a int with the max legth of the array
     */
    private static void printList(int[] list,int length) {
        for(int count=0; count<length;count++){
            System.out.println(count+" = "+list[count]+" ");
            if(count%15==0&&count!=0)
                System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Checks to see if the user input number is 
     * between the range 2 (inclusive) and 10 (exclusive)
     * @param num a int with the user input
     * @return True if the number is between 2 (inclusive) and 10 (exclusive)
     */
    private static boolean checkDigit(int num) {
        return (num>=2&&num<10);
    }

    /**
     * Returns a list of random digits in a desired length
     * All the digits generated will be checked with the list
     * so all the digits in the list are different from one other
     * @param length a int with the max legth of the array
     * @return int array with random different digits
     */
    private static int[] getRandom(final int length) {
        int[] list = new int[length];
        int num;

        for(int i=0; i<length; i++){
            int x = 0;
            num = (int)(Math.random()*10);
            while(x<i){
                if(list[x]==num){
                    num = (int)(Math.random()*10);
                    x=0;
                }
                else
                    x++;
            }
            list[i] = num;
        }
        
        return list;
    }

    /**
     * Prints out a message to the user welcoming them to the game
     * Also, asks the user if they want a hint after 3, 6, and >9 tries

     * @param tries a int with number of times guessed
     * @param length a int with the max length of the digits 
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     */
    private static void message(final int tries,final int length,int[] CL, int[] PL) {
        if(tries==0){
            System.out.println("\nWelcome to Master Mind");
            System.out.println("Each digit in the hidden set of digits are unique from the rest and are in random positions");
        }
        else if(tries==3||tries==6||tries>9){
            System.out.print("Do you want a hint? (Y)es or (N)o ");
            giveHint(keyboard.next().toUpperCase().charAt(0),tries,length,CL,PL);

        }
        System.out.println("Enter a random string of numbers of length "+length);

    }
    /**
     * Offers the user a hint if they choose too
     * If yes then give user a hint of one of the following
     * If number of tries is greater than 9 give user one random number in Computers list
     * with the index
     * if number of tries is greater than 9 and the length of the guess is greater than 5
     * then give user two random digits in the Computers list
     * if none of the  conditions are met then give user 1 random digit in Computers list
     * if no then return the user to guessing
     * @param hint a char Either 'Y' if they want a hint or anything else no hint
     * @param tries a int with number of times guessed
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     */
    private static void giveHint(char hint,final int tries,final int length, int[] CL, int[] PL) {
        final int MAX_THRESHOLDGUESSES = 9;
        final int MAX_THRESHOLDLENGTH = 6;
        System.out.println();
        if(hint=='Y'){
            int num1 = (int)(Math.random()*length);
            int num2 = (int)(Math.random()*length);
            //Give two random digits in CL
            if(tries>MAX_THRESHOLDGUESSES&&length>=MAX_THRESHOLDLENGTH){
                while(num1==num2)
                    num2 = (int)(Math.random()*length);
                System.out.println(CL[num1]+" and "+CL[num2]+ " is in the Computers list");
            }
            //Give hint of a digit and the place it is in
            else if(tries>MAX_THRESHOLDGUESSES)
                System.out.println(CL[num1]+" is in index "+num1);
            //Give a random digit in CL
            else
                System.out.println(CL[num1]+" is in the computers list");
        }
        
    }
    /**
     * Checks if the Users guess is the correct length
     * and if the Digits in the guess are all different
     * @param guess a String with the users input guess
     * @param length a int with the max legth of the array
     * @return True if Users guess is correct length and all digits are different
     */
    private static boolean checkInput(final String guess, final int length) {
        if(guess.length()==length){
            for(int i=0; i<length; i++){
                int num1 = guess.charAt(i)-'0';
                for(int x=0; x<length; x++){
                    int num2 = guess.charAt(x)-'0';
                    if(num1==num2)
                        if(i!=x)
                            return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    /**
     * Checks to see if the users Guess matches the Computers
     * list of digits
     * @param guess a String with the users input guess
     * @param length a int with the max legth of the array
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     * @return True if the guess matches the Computers list of digits
     */
    private static boolean checkGuess(final String guess,final int length,int[] CL, int[] PL) {
        int correct = 0;
        for(int i=0; i<length; i++)
            PL[i] = guess.charAt(i)-'0';
        
        for(int i=0; i<length; i++){
            if(PL[i]==CL[i])
                correct++;
        }

        return(correct==length);
        
    }

    /**
     * Congratulate the user when the users guess matches 
     * the computers list of digits. Print out the number 
     * of tries it took the user to guess the digits and 
     * print out the computers list
     * @param tries
     * @param CL
     * @param PL
     */
    private static void congratulation(int tries, int[] CL) {
        tries+=1;
        System.out.println("\nCongratulation you win");
        System.out.println("It took you "+tries+" Guesses to Match the computer");
        System.out.print("The Computers digits were ");
        for(int i=0; i<CL.length; i++){
            System.out.print(CL[i]);
            if(i!=CL.length-1)
                System.out.print(", ");
        }
        System.out.println();
    }
    /**
     * Sends out a message telling the user from their guess
     * how many of their numbers are correct and in there right place
     * and how many are correct but in the wrong place.
     * @param length a int with the max legth of the array
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     */
    private static void howManyCorrect(final int length,int[] CL, int[] PL) {
        int counter = 0;
        int correctPlace = 0;
        int wrongPlacee = 0;

        //Comapres and counts how many are right in correct place and wrong place
        while(counter<length){
            int num = PL[counter];
            for(int index=0; index<length; index++){
                if(CL[index]==num){
                    if(index==counter)
                        correctPlace++;
                    else
                        wrongPlacee++;
                }
            }
            counter++;
        }

        System.out.println();
        System.out.println(correctPlace+" are correct and in there right place");
        System.out.println(wrongPlacee+" are correct but in there wrong place");
    }
    
    /**
     * Prints out "You lose" message with the list of
     * the Computers Digits when the user guess limit is reached
     * @param CL a int array that stores the computers numbers
     */
    private static void compWins(int[] CL) {
        System.out.println("\nYou lose");
        System.out.print("The Computers Digits were ");
        for (int i : CL) {
            System.out.print(i);
            if(i!=CL.length-1)
            System.out.print(", ");
        }
        System.out.println();
    }

    /**
     * Checks to see if the user wants to
     * play the game again
     * @param ifYes a char to check if the user wants to stop playing
     * @return true if user wants to stop
     */
    private static boolean checkQuit() {
        System.out.println("\nDo you want to play again? (Y)es or (N)o ");
        System.out.println("(Y)es to continue playing");
        System.out.println("(N)o to stop playing and be sent to the main menu");
        return(keyboard.next().toUpperCase().charAt(0)=='N');
    }

    /**
     * Prints out the average performance of the user. Also,
     * Prints out the number of games played and number of guesses
     * made from all games played
     * @param numGuesses a int that has the number of guesses made from all games played
     * @param gamesPlaed a int that has the number of games played
     */
    private static void averagePerformance(int numGuesses, int gamesPlayed) {
        double average = ((double)numGuesses/(double)gamesPlayed);

        System.out.println("\nYour Average Performance overall is: "+average);
        System.out.println("Number of games played: "+gamesPlayed);
        System.out.println("Number of guesses made throughout all games: "+numGuesses+"\n\n");
    }
}