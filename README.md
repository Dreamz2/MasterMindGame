# MasterMind
The java program is an attempt on creating **Master Mind** a code-breaking game for two players. Instead, it will be 
**Player** vs **Computer**. Also, because guessing a list of colours will take a long time, the 
player will be guessing digits 0-9 in the computer's lists. The player will have to enter the 
length of the list they are wanting to guess (2 inclusive to 10 exclusives). Once the length is entered, 
the computer will generate a random list of digits within the given length. The digits in the list will  
be different from one another. The program will let the player continue playing until they want to stop.
Finally, print out the average performance of all games played.

Some improvements I would like to make to the program is to list out all past guesses made for 
each game the player has played. Another would be to print out the best and worst 
game played during that session. Display the number of guesses the player has left for each guess
Finally, maybe implement OOP to make the game run more smoothly.

## List of Criteria
- Have a Main menu
- Have the player input the length they want to guess
  - 2 inclusive to 10 exclusive
- All digits in the computers list are different from one another
  - All digits are between 0-9 inclusive
- Make sure all the digits in the user guess is all different
- Check if the user's guess is the correct length
- Calculate and print out the average performance of all games played
- Let the player continue to play until they have had enough
- Include a Cheater Mode
  - To be used for debugging
- Include some hints after some amount of tires
- Add a limit to how many guesses the user has
- Add Javadoc documentation  

<!--![Old Content](pic.jpg)-->

## Improvements
- List out past user digit inputs
  - Used ArrayList to keep users input
- Print out the best and worst game played
- Display the number of attempts left when guessing
<!-- - Implement OOP to the program -->


<!--![Improved Content]() -->