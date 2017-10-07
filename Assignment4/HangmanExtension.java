/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class HangmanExtension extends ConsoleProgram {

	/* Instance Variables */
	private RandomGenerator rg = RandomGenerator.getInstance();
	private HangmanCanvasExtension canvas;
	private String hiddenWord;
	private boolean correctAnswer;

	public void init() {
		canvas = new HangmanCanvasExtension();
		add(canvas);

	}

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

	private void printFinalResult(int turns, String word) {
		if (turns == 0) {
			println("The word was: " + word);
			println("You Lose!");
			canvas.die();
		} else {
			try {
				canvas.dance();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			println("You guessed the word: " + word);
			println("You Win!");
			canvas.displayWord(hiddenWord);

		}
	}

	private String getHiddenWord() {
		HangmanLexicon lex = new HangmanLexicon();
		hiddenWord = "";
		String word = lex.getWord(rg.nextInt(0, lex.getWordCount()));
		for (int i = 0; i < word.length(); i++) {
			hiddenWord = hiddenWord + "-";
		}
		return word;
	}

	private String readGuesses(int turns) {
		println("Word now looks like this: " + hiddenWord);
		canvas.displayWord(hiddenWord);
		println("U have " + turns + " guesses left");
		String guess = readLine("Your guess is: ");
		return guess;
	}
}
