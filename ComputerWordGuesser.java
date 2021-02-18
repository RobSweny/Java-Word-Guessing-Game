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

public class ComputerWordGuesser {
		// DATA MEMBERS
		// Initialise
		private	ArrayList<String> dictionary;
		private	ArrayList<Character> consonants;
		private	char[] randomChars;
		private char[] consonantsArray;
		private	String userOneGuessedWord;
		private	String userTwoGuessedWord;
		private String userGuessedWord;
		private	String dictionary_file;
		private	int userOnePoints;
		private	int userTwoPoints;
		private	int computerPoints;
		private	int runAgainChoice;
		private int points;
		private	Boolean userCharNotFoundInArray;

		// Declare
		public ComputerWordGuesser()
		{
			randomChars = new char[12];
			consonantsArray = "bcdfghjklmnpqrstvwxyz".toCharArray();
			dictionary = new ArrayList<>();
			consonants = new ArrayList<>();
			for (char ch : consonantsArray) {
				consonants.add(ch);
   			}
			userOneGuessedWord = "";
			userTwoGuessedWord = "";
			dictionary_file = "";
			userGuessedWord = "";
			userOnePoints = 0;
			userTwoPoints = 0;
			computerPoints = 0;
			runAgainChoice = 0;
			points = 0;
			userCharNotFoundInArray = false;
		}

		// Setters
		public void setDictionaryFile(String dictionary_file){
			this.dictionary_file = dictionary_file;
		}

		public void setRandomChars(char[] randomChars){
			this.randomChars = randomChars;
		}

		public void setUserOneGuessedWord(String userOneGuessedWord){
			this.userOneGuessedWord = userOneGuessedWord;
		}

		public void setUserOnePoints(int userOnePoints){
			this.userOnePoints = userOnePoints;
		}

		public void setUserTwoGuessedWord(String userTwoGuessedWord){
			this.userTwoGuessedWord = userTwoGuessedWord;
		}

		public void setUserTwoPoints(int userTwoPoints){
			this.userTwoPoints = userTwoPoints;
		}

		public void setComputerPoints(int computerPoints){
			this.computerPoints = computerPoints;
		}


		/* function to generate, print and return 12 random characters (letters from the alphabet) */
		public void generateRandomCharacters()
		{
			System.out.println("Here are your 12 random characters");
			randomChars = returnRandomCharsFromAlphabet(12);
			for (char var : randomChars) {
				System.out.print(var + " ");
			}
			System.out.println("\n\nCreate a word from the characters above: ");
		}

		/* function  to check user word against dictionary and calculate points generated*/
		public void checkDictionaryReturnUserScore(String user, String userGuessedWord)
		{
			int pointsWon = 0;
			// check if userword in dictionary
			if (dictionary.contains(userGuessedWord))
			{
				System.out.println("You entered a valid word from the dictionary");

				// check points
				for (char ch: userGuessedWord.toCharArray())
				{
					if(consonants.contains(ch))
					{
						pointsWon += 3;
					}
				}
				System.out.println("You got " + pointsWon + " points");
			} else {
				System.out.println("You didn't enter a valid word from the dictionary");
				System.out.println("You have " + points + " points");
			}

			if(user == "userone"){
				userOnePoints += pointsWon;
				System.out.println("You now have " + userOnePoints + " points");
			} else {
				userTwoPoints += pointsWon;
				System.out.println("You now have " + userTwoPoints + " points");
			}
			System.out.println("-----------------------------");
		}


		// prints how the game works
		public void printGameIntroduction()
		{
			System.out.println("--------------");
			System.out.println("WELCOME");
			System.out.println("The goal of the game is to guess a word based on 12 random letters");
			System.out.println("Each round there will be 12 new letters chosen, you will try to come up with a word that awards the most points");
			System.out.println("You will receive triple points for each consonant in the word (e.g 'bit' - 6 points, 'shell' - 12 points");
			System.out.println("--------------");
		}


		// function to generate and return 12 random chars from alphabet
		public static char[] returnRandomCharsFromAlphabet(int numOfChars)
		{
			char[] randomChars = new char[numOfChars];
			char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

			// create an array of 12 random chars from the alphabet
			for (int i=0; i<numOfChars; i++)
			{
				int random = (int)(Math.random() * 25 + 1);
				randomChars[i] = alphabet[random];
			}

			return randomChars;
		}

		// function to load dictionary of words from file, as this is easier to view and modify
		public void loadDictionary(String dictionary_file)
		{
			// try catch in case there are any errors finding or reading the file
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(dictionary_file));
				String line;
				try
				{
					if (dictionary_file == "dictionary_of_words_from_brief.txt"){
						// while reading per line in file, split each word by the delimiter ","
						while ((line = reader.readLine()) != null)
						{
							for (String token : line.split(","))
							{
								dictionary.add(token.trim());
							}
						}
						System.out.println("Loaded file from brief specified dictionary");
					} else {
						// while reading per line in file, split each word by the delimiter "\n"
						while ((line = reader.readLine()) != null)
						{
							for (String token : line.trim().split("\n"))
							{
								// Add to arraylist
								dictionary.add(line);
							}
						}
						System.out.println("Loaded file from merriam webster dictionary");
					}
					// close file after reading
					reader.close();
				}
				// throw exception if there is an issue reading the file
				catch(IOException e) {
					System.out.println("Issue reading file");
					e.printStackTrace();
				}
			// throw an exception if there is an issue finding the file
			} catch (FileNotFoundException e) {
				// if there is an issue finding/reading the file, use the default
				System.out.println("No file available, using default dictionary");
				Collections.addAll(dictionary, "algorithm","application","backup","bit","buffer","bandwidth","broadband","bug","binary","browser","bus","cache","command","computer","cookie","compiler","cyberspace","compress","configure","database","digital","data","debug","desktop","disk","domain","decompress","development","download","dynamic","email","encryption","firewall","flowchart","file","folder","graphics","hyperlink","host","hardware","icon","inbox","internet","kernel","keyword","keyboard","laptop","login","logic","malware","motherboard","mouse","mainframe","memory","monitor","multimedia","network","node","offline","online","path","process","protocol","password","phishing","platform","program","portal","privacy","programmer","queue","resolution","root","restore","router","reboot","runtime","screen","security","shell","snapshot","spam","screenshot","server","script","software","spreadsheet","storage","syntax","table","template","thread","terminal","username","virtual","virus","web","website","window","wireless");
			}
		}


		// GETTERS
		// Allow the program to retrieve these results in a different class
		public String getDictionaryFile(){
			return dictionary_file;
		}

		public char[] getRandomChars(){
			return randomChars;
		}

		public String getUserOneGuessedWord(){
			return userOneGuessedWord;
		}

		public int getUserOnePoints(){
			return userOnePoints;
		}

		public String getUserTwoGuessedWord(){
			return userTwoGuessedWord;
		}

		public int getUserTwoPoints(){
			return userTwoPoints;
		}

		public int getComputerPoints(){
			return computerPoints;
		}
}