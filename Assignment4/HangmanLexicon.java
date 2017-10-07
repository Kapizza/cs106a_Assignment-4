/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {
	// This is the HangmanLexicon constructor
	private ArrayList<String> words = new ArrayList<String>();

	public HangmanLexicon() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"HangmanLexicon.txt"));
			while (true) {
				String word = reader.readLine();
				if (word == null)
					break;
				words.add(word);
			}
		} catch (Exception e) {
			throw new ErrorException("Error!");
		}

	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {

		return words.get(index);
	}
}
