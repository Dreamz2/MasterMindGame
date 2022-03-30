/*
Name: Edison Wei   Student Number: 100374523
Course: CPSC 1150-003
Purpose: Like the game Mastermind but that uses colours.
            This game will be using random different numbers.
*/
import java.util.Scanner;

/**
 * Mastermind
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
        int guesses;

        do{
            check = menu();
            if(wantToPlay(check)){
                if(cheatOnOff(check))
                    cheater=true;
                else
                    cheater=false;
                guesses = difficulty();
                gameTime(cheater,guesses);
            }

        }while(wantToPlay(check));

    }
    /**
     * Displays a Menu like output 
     * for the user to choose from
     * @return a charcter the user enters
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
     * @return a char the user input in the
     */
    private static int difficulty() {
        System.out.println();
        System.out.println("What difficulty do you want to play on");
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
    private static void gameTime(boolean cheater,final int numGuesses) {
        int numberOfAttempts = 0;
        boolean quits = false;

        System.out.print("How many digits should be guessed this time (not more than 10, but at least 2.) ");
        int howMany = keyboard.nextInt();
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
        while(numberOfAttempts<numGuesses&&quits!=true){
            message(numberOfAttempts,howMany,compList,playerList);
            String guess = keyboard.next();
            while(!checkInput(guess,howMany)){
                System.out.println("Enter a random string of numbers again with a length of "+howMany);
                guess = keyboard.next();
                System.out.println();
            }
            if(checkGuess(guess, howMany, compList, playerList)){
                congratulation(numberOfAttempts,compList);
                numberOfAttempts = numGuesses;
            }
            else
                howManyCorrect(howMany,compList,playerList);
            

            numberOfAttempts++;
        }
        
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
    private static int[] getRandom(int length) {
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
     * Sends out a message to the user welcoming them to the game
     * Asks the user if they want a hint after 3,6,and >10 tries
     * @param tries a int with number of times guessed
     * @param length a int with the max legth of the array
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     */
    private static void message(int tries,int length,int[] CL, int[] PL) {
        if(tries==0){
            System.out.println("Welcome to Master Mind");
            System.out.println("Each digit in the hidden set of digits are unique from the rest and are in random positions");
            System.out.println("Enter a random string of numbers of length "+length);
        }
        else if(tries==3||tries==6||tries>10){
            System.out.print("Do you want a hint? (Y)es or (N)o ");
            giveHint(keyboard.next().toUpperCase().charAt(0),tries,CL,PL);
        }

    }
    /**
     * Offers the user a hint if they want
     * If yes then give user a hint of 1 or 2 digits
     * or give user a index of a digit if they are over 9 tries
     * if no then return the user to guessing
     * @param hint
     * @param tries a int with number of times guessed
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     */
    private static void giveHint(char hint,int tries,int[] CL, int[] PL) {
        if(hint=='Y'){
            
        }
        
    }
    /**
     * Checks if the Users guess is the correct length
     * and if the Digits in the guess are all different
     * @param guess a String with the users input guess
     * @param length a int with the max legth of the array
     * @return True if Users guess is correct length and all digits are different
     */
    private static boolean checkInput(String guess, int length) {
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
    private static boolean checkGuess(String guess,int length,int[] CL, int[] PL) {
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
        System.out.println("Congratulation you win");
        System.out.println("It took "+tries+" Guess to Match the computer");
        System.out.print("The Computers digits were ");
        for(int i: CL)
            System.out.print(i+", ");
        System.out.println();
    }
    /**
     * Sends out a message telling the user from their guess
     * how many of the number are correct and in there right place
     * and how many are correct but in the wrong place.
     * @param length a int with the max legth of the array
     * @param CL a int array that stores the computers numbers
     * @param PL a int array that stores the players guesses
     */
    private static void howManyCorrect(int length,int[] CL, int[] PL) {
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

        System.out.println(correctPlace+" are correct and in there right place");
        System.out.println(wrongPlacee+" are correct but in there wrong place");
    }
}