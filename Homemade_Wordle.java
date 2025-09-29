import java.util.Random;
import java.io.File;
import java.util.Scanner;
public class Homemade_Wordle {
	public static void main (String args[]){
		Scanner userInput = new Scanner(System.in);
		File words = new File("words.txt");
		Random rng = new Random();

		int lines = getWordAmount(words);
		String[] wordList = new String[lines];

		populateArray(words, wordList);


		boolean cont = true;
		boolean correctWord = false;

		while(cont){
			int randomIndex = rng.nextInt(lines);
			String secretWord = wordList[randomIndex];


			
			int guessAmountLeft = 6;
			int guessesHad = 0;



			printTitleScreen();



			String[][] attempt = new String[guessAmountLeft][secretWord.length()];
			String[][] score = new String[guessAmountLeft][secretWord.length()];



			while(guessAmountLeft >= 1){

				guessAmountLeft--;
				String currentGuess = guess(userInput, wordList);

				//currentGuess.toLowerCase();

				correctWord = writeAttempt(attempt, currentGuess, secretWord, guessesHad);
				writeScore(score, currentGuess, secretWord, guessesHad);
				if(correctWord){
					System.out.println("Congratulations!");
					break; 
				}
				

				tryAgain(guessAmountLeft, secretWord);
				if(guessAmountLeft == 0){
					break;
				}

				System.out.println("---------ATTEMPT---------");
				print2DArrayPartial(attempt, guessesHad);
				System.out.println("-------------------------");
				guessesHad++;
				//guessAmountLeft--;
			}

			//print2DArray(attempt);

			
			//userInput.nextLine();
			if(correctWord && guessAmountLeft > 0){
				System.out.println("--------SCORE--------");
				print2DArrayPartial(score, guessesHad);
				System.out.println("---------------------");
			}
			if(guessAmountLeft < 1){
				System.out.println("--------SCORE--------");
				print2DArray(score);
				System.out.println("---------------------");
			}

			System.out.println("Play Again?");
			String x = userInput.nextLine();
			x = turnLowerCase(x);
			if(x.equals ("yes")){
			}
			else{
				cont = false;
			}
			//guess(userInput);
		}







		
	}

	public static int getWordAmount(File words){
		int lines = 0;
		try{

			Scanner inputText = new Scanner(words);
			

			while(inputText.hasNextLine()){
				String line = inputText.nextLine();
				//System.out.println(line);
				lines++;
			}
		}
		catch(Exception e){
		}

		return lines;

	}

	public static void populateArray(File words, String[] arr){

		try{
			Scanner inputText = new Scanner(words);
			
			for(int i = 0; i < arr.length; i++){
				while(inputText.hasNextLine()){
					String line = inputText.nextLine();
					Scanner inputArr = new Scanner(line);
					arr[i] = inputArr.nextLine();
					break;
				}
			}
		}
		catch(Exception e){
		}

	}

	public static void printTitleScreen(){
		System.out.println("**********************\n" +
						   "* Welcome to Mnrdle! *\n" + 
						   "**********************\n" +
				           "() means the letter is in the word but wrong place\n" +
				           "A capital letter means the letter is in the correct place\n" +
				           "A lowercase letter means the letter is not in the word");
	}

	public static String guess(Scanner userInput, String[] wordList){
		String guess = " ";
		boolean valid = false;

		System.out.print("Please guess: ");
		
		while(!valid){

			guess = userInput.nextLine();
			guess = guess.toLowerCase();

			for(int i = 0; i < wordList.length; i++){
				if(guess.equals(wordList[i])){
					valid = true;
					break;
				}
			}

			if(!valid){
				System.out.print("Invalid input. Please try again: ");

			}
		}

		return guess;
	}


	public static void tryAgain(int guessAmountLeft, String secretWord){
		//guessAmountLeft--;
		if(guessAmountLeft > 1){
			System.out.println("Try again! you have " + guessAmountLeft +
								" guesses left!");
		}
		else if(guessAmountLeft == 1){
			System.out.println("Try again! you have " + guessAmountLeft +
								" guess left!");
		}
		else{
		System.out.println("The word was " + secretWord +
							". Better luck next time!");
		}
	}

	public static String turnLowerCase(String something){
		
		return something.toLowerCase();

	}

	public static void print2DArray(String[][] arr){
		for(int i = 0; i < arr.length; i++){
			if(i > 0){
				System.out.println();
			}
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(arr[i][j]);
			}
		}
		System.out.println();

	}


	public static void print2DArrayPartial(String[][] arr, int guessesHad){
		for(int i = 0; i < guessesHad + 1; i++){
			if(i > 0){
				System.out.println();
			}
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(arr[i][j]);
			}
		}
		System.out.println();
	}

	public static boolean writeAttempt(String[][] arr, String currentGuess, String secretWord, int guessesHad){
		for(int j = 0; j < arr[guessesHad].length; j++){

			if(secretWord.charAt(j) != currentGuess.charAt(j)){
				arr[guessesHad][j] = "| " + currentGuess.charAt(j) + " |";
			}

			for(int k = 0; k < secretWord.length(); k++){
				if(currentGuess.charAt(j) == secretWord.charAt(k)){
					arr[guessesHad][j] = "|(" + currentGuess.charAt(j) + ")|";
				}
				
			}

			if(secretWord.charAt(j) == currentGuess.charAt(j)){
				arr[guessesHad][j] = "| " + Character.toUpperCase(currentGuess.charAt(j)) + " |";
			}

			if(secretWord.equals (currentGuess)){
				return true;
			}
		}
		return false;
	}

	public static void writeScore(String[][] arr, String currentGuess, String secretWord, int guessesHad){
		for(int j = 0; j < arr[guessesHad].length; j++){

			if(secretWord.charAt(j) != currentGuess.charAt(j)){
				arr[guessesHad][j] = "| 0 |";
			}

			for(int k = 0; k < secretWord.length(); k++){
				if(currentGuess.charAt(j) == secretWord.charAt(k)){
					arr[guessesHad][j] = "|(1)|";
				}
				
			}

			if(secretWord.charAt(j) == currentGuess.charAt(j)){
				arr[guessesHad][j] = "| 2 |";
			}
		}
	}


	
}