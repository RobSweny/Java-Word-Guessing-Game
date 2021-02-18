/*
Rob Sweny (x20135939@student.ncirl.ie)
Application.java
20201228



Find Computer Words Game
At the start of each round, the application (i.e. computer) randomly selects 12 letters from the
English alphabet and displays the 12 letters to the players (note that there are 26 letters in the
English alphabet). The 12 letters do not have to be unique (i.e. the same letter can be randomly
selected multiple times at the beginning of a certain round).

Next, each player takes turn to try to make a word using as many of the 12 letters as the player can
but using each letter only once (note that in the case that a randomly selected letter has multiple
occurrences then the players can use that letter as many times as occurrences the letter has). Input
(i.e. word) validation is required according to the rules of the game, including the following rules


+------------------------------------------------------------+
| Ask the players at the beginning of the game how many      |
| rounds they would like to play, and allow the players to   |
| play that amount of rounds.	                             |
+------------------------------------------------------------+

+-----------------------------------------------------------------------------------------------------+-----------------+
| 9 | RP9 | The player receives triple the amount of points as the number of consonants in the word.  | bit 6 points    |
| 	|	  |										                                                      | shell 12 points |
+-----------------------------------------------------------------------------------------------------+-----------------+
*/

// import scanner to take input
import java.util.*;

// import bufferReader to read file
import java.io.*;

// import collections to add multiple items to arraylist
import java.util.Collection;

// import math for random
import java.lang.Math;

public class ComputerWordGuesserApp {


    public static void main(String[] args)
    {
		// create scanner object called scanner
		Scanner input = new Scanner(System.in);

		// Create an object of the ComputerWordGuesser class
		ComputerWordGuesser computerwordguesser = new ComputerWordGuesser();

		// initialize variables
		char[] randomChars = new char[12];
		String userOneGuessedWord;
		String userTwoGuessedWord;
		String dictionary_file;
		int roundsToPlay;
		int currentRound;
		int userOnePoints;
		int userTwoPoints;
		int computerPoints;
		int tempComputerPoints;
		int computerOrHuman;
		int runAgainChoice;
		int computerDifficulty;
		int selectedFile;
		Boolean userCharNotFoundInArray;
		Boolean playAgain;

		// declare variables
		userOneGuessedWord = "";
		userTwoGuessedWord = "";
		dictionary_file = "";
		roundsToPlay = 0;
		userOnePoints = 0;
		userTwoPoints = 0;
		computerPoints = 0;
		tempComputerPoints = 0;
		currentRound = 0;
		runAgainChoice = 0;
		computerOrHuman = 0;
		selectedFile = 0;
		computerDifficulty = 0;
		playAgain = true;
		userCharNotFoundInArray = false;

		computerwordguesser.printGameIntroduction();

		while(playAgain){
			// prompt the user to enter the number of rounds they will play
			System.out.print("Choose a dictionary of words\n1) 100 Words (Hard)\n2) Full dictionary (Easy)\nChoice: ");
			do
			{
				// If the user inputs a non integer, let them know this wasn't valid
				try
				{
					selectedFile = input.nextInt();
					System.out.print("\n");
				} catch (InputMismatchException ex) {
					System.out.println("Invalid Option, enter a positive integer");
					input.next();
				}
				if (selectedFile != 1 & selectedFile != 2){
					System.out.println("Invalid Option, please choose one of the prompted options: ");
				}
			} while (selectedFile < 1 || selectedFile > 2);
			if (selectedFile == 1){
				computerwordguesser.loadDictionary("dictionary_of_words_from_brief.txt");
			} else {
				computerwordguesser.loadDictionary("dictionary_of_words.txt");
			}

			// prompt the user to enter the number of rounds they will play
			System.out.print("How many rounds would you like to play? ");
			do
			{
				// If the user inputs a non integer, let them know this wasn't valid
				try
				{
					roundsToPlay = input.nextInt();
					System.out.print("\n");
				} catch (InputMismatchException ex) {
					System.out.println("Invalid Option, enter a positive integer");
					input.next();
				}
				if (roundsToPlay <= 0){
					System.out.println("Invalid Option, enter a positive integer");
				}
			} while (roundsToPlay <= 0);

			// prompt the user to enter if they would like to play 2 player or against the computer
			System.out.println("Would you like to play with 2 players or against another computer? ");
			System.out.println("1) 2 Players");
			System.out.println("2) Computer");
			System.out.print("Choose one of the above options: ");
			do
			{
				// If the user inputs a non integer, let them know this wasn't valid
				try
				{
					computerOrHuman = input.nextInt();
					System.out.print("\n");
				} catch (InputMismatchException ex) {
					System.out.println("Invalid Option, enter a positive integer");
					input.next();
				}
				if (computerOrHuman != 1 & computerOrHuman != 2){
					System.out.println("Invalid Option, please choose one of the prompted options: ");
				}
			} while (computerOrHuman < 1 || computerOrHuman > 2);

			if (computerOrHuman == 2){
				System.out.print("Please choose a computer difficulty\n1) Easy\n2) Medium\n3) Hard\nChoice: ");
				do
				{
					// If the user inputs a non integer, let them know this wasn't valid
					try
					{
						computerDifficulty = input.nextInt();
						System.out.print("\n");
					} catch (InputMismatchException ex) {
						System.out.println("Invalid Option, enter a positive integer");
						input.next();
					}
					if (computerDifficulty < 1 || computerDifficulty > 3){
						System.out.println("Invalid Option, please choose one of the prompted options: ");
					}
				} while (computerDifficulty < 1 || computerDifficulty > 3);
			}

			// game loop
			while (currentRound < roundsToPlay)
			{

				System.out.println("Player One - Round: " + (currentRound + 1));

				// generate 12 random chars
				computerwordguesser.generateRandomCharacters();
				randomChars = computerwordguesser.getRandomChars();

				// function to take input and validate
				takeInputandValidate("userone", userCharNotFoundInArray, userOneGuessedWord, input, randomChars, computerwordguesser);
				userOneGuessedWord = computerwordguesser.getUserOneGuessedWord();

				// function to check entered word, check dictionary and return points
				computerwordguesser.checkDictionaryReturnUserScore("userone", userOneGuessedWord);
				userOnePoints = computerwordguesser.getUserOnePoints();

				// check if player or computer is playing
				// human is playing
				if (computerOrHuman == 1) {
					System.out.println("Player Two - Round: " + (currentRound + 1));
					// generate 12 random chars
					computerwordguesser.generateRandomCharacters();
					randomChars = computerwordguesser.getRandomChars();

					// function to take input and validate
					takeInputandValidate("usertwo", userCharNotFoundInArray, userTwoGuessedWord, input, randomChars, computerwordguesser);
					userTwoGuessedWord = computerwordguesser.getUserTwoGuessedWord();

					// function to check entered word, check dictionary and return points
					computerwordguesser.checkDictionaryReturnUserScore("usertwo", userTwoGuessedWord);
					userTwoPoints = computerwordguesser.getUserTwoPoints();
				// computer playing
				} else {
					tempComputerPoints += (int)(Math.random() * (computerDifficulty * 3) + 1);
					System.out.println("The computer scored " + tempComputerPoints + " points");
					computerPoints += tempComputerPoints;
					System.out.println("The computer now has " + computerPoints + " points");
					System.out.println("-----------------------------");
				}

				// round end
				currentRound++;
			} // end game round loop


			System.out.println("Game Completed");
			if(computerOrHuman == 1){
				System.out.println("Player One: " + userOnePoints);
				System.out.println("Player Two: " + userTwoPoints);

				if (userOnePoints  == userTwoPoints){
					System.out.println("This game was a draw!");
				} else if (userOnePoints > userTwoPoints){
					System.out.println("Congratulations! You are the winner.");
				} else {
					System.out.println("Unlucky! You lose this game.");
				}
			} else {
				System.out.println("User Score: " + userOnePoints);
				System.out.println("Computer Score: " + computerPoints);

				if (userOnePoints  == computerPoints){
					System.out.println("This game was a draw!");
				} else if (userOnePoints > computerPoints){
					System.out.println("Congratulations! You are the winner.");
				} else {
					System.out.println("Unlucky! You lose this game.");
				}
			}
			System.out.println("-----------------------------");

			// check if player wants to play the game again
			do {
				System.out.print("\nWould you like to play again? \n1) Confirm\n2) Cancel\nChoice: ");
				// If the user inputs a non integer, let them know this wasn't valid
				try {
					runAgainChoice = input.nextInt();
					System.out.print("\n");
				} catch (InputMismatchException ex) {
					System.out.println("Invalid Option");
					input.next();
				}
			} while (runAgainChoice != 1 && runAgainChoice != 2);
			if (runAgainChoice == 2){
				playAgain = false;
			} else {
				computerwordguesser.setUserOnePoints(0);
				computerwordguesser.setUserTwoPoints(0);
				computerwordguesser.setComputerPoints(0);
				currentRound = 0;
			}
		} // end game loop
    } // end main method

    /* function take user input and validate, this checks against the random chars generated in 'generateRandomCharacters()' */
	public static void takeInputandValidate(String user, Boolean userCharNotFoundInArray, String guessedWord, Scanner input, char[] randomChars, ComputerWordGuesser computerwordguesser)
	{
		do
		{
			userCharNotFoundInArray = false;
			// If the user inputs a non integer, let them know this wasn't valid
			try
			{
				guessedWord = input.next();
				System.out.print("\n");
			} catch (InputMismatchException ex) {
				System.out.println("Invalid Option, enter a valid String");
				input.next();
			}

			// allow the user to exit the round if they are truly stuck
			if (guessedWord.equals("iquit")){
				break;
			}

			// create an array list from the randomChars array, this gives us the ability to use 'contains' and 'remove'
			// we utilise remove to ensure the user doesn't enter the same character twice
			ArrayList<Character> randomCharsArrayList = new ArrayList<Character>();
			for(char randomChar : randomChars) {
				 randomCharsArrayList.add(randomChar);
			}

			// for each character in guessedword
			for (char ch: guessedWord.toCharArray())
			{
				if(randomCharsArrayList.contains(ch)){
					// after the user uses a character, remove it to ensure they don't enter the same character twice
					randomCharsArrayList.remove(randomCharsArrayList.indexOf(ch));
				} else {
					userCharNotFoundInArray = true;
					System.out.println("You a character that's not available or used the same character multiple times");
					for (char var : randomChars) {
								System.out.print(var + " ");
					}
					System.out.println("\nIf you are stuck, type 'iquit' to skip the round");
					System.out.print("\nCreate a word from the characters above: ");
					break;
				}
			}
			// if the user entered a word that
			if (userCharNotFoundInArray){
				continue;
			}
		} while (!guessedWord.matches("[a-zA-Z]+") || guessedWord == "" || userCharNotFoundInArray);

		if(user == "userone"){
			computerwordguesser.setUserOneGuessedWord(guessedWord);
		} else {
			computerwordguesser.setUserTwoGuessedWord(guessedWord);
		}
	}

}