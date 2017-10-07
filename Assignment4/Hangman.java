/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
	/* Instance variables */
	private RandomGenerator rg = RandomGenerator.getInstance();
	private HangmanCanvas canvas;
	private String hiddenWord;
	private boolean correctAnswer;

	/*
	 * This method creates canvas object and adds it;
	 */
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);

	}

	/*
	 * This method runs Hangman game for several times;
	 */
	public void run() {
		println("Welcome to Hangman");
		while (true) {
			canvas.reset();
			startGame();
			String answer = readLine("Enter 0 to exit or anything to restart: ");
			if (answer.equals("0")) {
				System.exit(0);
			}
		}
	}

	/*
	 * This method starts Hangman game; Gives user 8 turns to enter his guess,
	 * check is guess is correct or not, prints messages for any incorrect
	 * input; Last it prints final result of game;
	 */
	private void startGame() {
		String word = getHiddenWord();
		int turns = 8;
		int wrongLetterCounter = 0;
		while (turns > 0) {
			String guess = readGuesses(turns);
			if (guess.length() == 1 && Character.isLetter(guess.charAt(0))) {
				guess = guess.toUpperCase();
				checkLetter(word, guess);
				if (!correctAnswer) {
					wrongLetterCounter++;
					canvas.noteIncorrectGuess(guess.charAt(0),
							wrongLetterCounter);
					println("There is no " + guess.charAt(0) + "'s in the word");
					turns--;
				} else {
					println("This guess is correct");
					if (hiddenWord.equals(word))
						break;
				}
			} else {
				println("ERROR!");
			}
		}
		printFinalResult(turns, word);
	}

	/*
	 * This method checks letter, if guess is correct reveals it;
	 */
	private void checkLetter(String word, String guess) {
		String s = "";
		correctAnswer = false;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess.charAt(0)) {
				s += guess.charAt(0);
				correctAnswer = true;
			} else {
				s += hiddenWord.charAt(i);
			}
		}
		hiddenWord = s;
	}

	/*
	 * This method prints win or lose messages;
	 */
	private void printFinalResult(int turns, String word) {
		if (turns == 0) {
			println("The word was: " + word);
			println("You Lose!");
		} else {
			println("You guessed the word: " + word);
			println("You Win!");
			canvas.displayWord(hiddenWord);
		}
	}

	/*
	 * This method gets word from lexicon, then hides and returns it;
	 */
	private String getHiddenWord() {
		HangmanLexicon lex = new HangmanLexicon();
		hiddenWord = "";
		String word = lex.getWord(rg.nextInt(0, lex.getWordCount()));
		for (int i = 0; i < word.length(); i++) {
			hiddenWord = hiddenWord + "-";
		}
		return word;
	}

	/*
	 * This method reads guesses;
	 */
	private String readGuesses(int turns) {
		println("Word now looks like this: " + hiddenWord);
		canvas.displayWord(hiddenWord);
		println("U have " + turns + " guesses left");
		String guess = readLine("Your guess is: ");
		return guess;
	}
}
