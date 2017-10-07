/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import acm.graphics.*;
import acm.util.MediaTools;

public class HangmanCanvasExtension extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		wrongLetters = "";
		set.clear();
		drawScaffold();
		if (isPlayin)
			surv.stop();

	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		if (hiddenWord == null)
			createDisplayWord(word);
		remove(hiddenWord);
		createDisplayWord(word);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter, int count) {
		// Switch/case statement adds parts of body on canvas;
		// If statement checks incorrect guess in set of wrong letters and adds
		// if there is not entered letter in it;
		if (!set.contains(letter)) {
			set.add(letter);
			wrongLetters += letter + ", ";
			GLabel l = new GLabel("Wrong letters: " + wrongLetters);
			add(l, 40, 7 * getHeight() / 8 + 40);
		}
		switch (count) {
		case 1:
			drawHead();
			break;
		case 2:
			addBody();
			break;
		case 3:
			drawLeftHand();
			break;
		case 4:
			drawRightHand();
			break;
		case 5:
			drawLeftLeg();
			break;
		case 6:
			drawLeftFoot();
			break;
		case 7:
			drawRightLeg();
			break;
		case 8:
			drawRightFoot();
			break;

		default:
			break;
		}

	}

	/*
	 * This method draws left hand;
	 */
	private void drawLeftHand() {
		GCompound leftHand = new GCompound();
		GLine upperArm = new GLine(getWidth() / 2, 2 * getHeight() / 8
				+ ARM_OFFSET_FROM_HEAD, getWidth() / 2 - UPPER_ARM_LENGTH, 2
				* getHeight() / 8 + ARM_OFFSET_FROM_HEAD);
		leftHand.add(upperArm);
		GLine lowerArm = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH, 2
				* getHeight() / 8 + ARM_OFFSET_FROM_HEAD, getWidth() / 2
				- UPPER_ARM_LENGTH, 2 * getHeight() / 8 + ARM_OFFSET_FROM_HEAD
				+ LOWER_ARM_LENGTH);
		leftHand.add(lowerArm);
		add(leftHand);
	}

	/*
	 * This method draws right hand;
	 */
	private void drawRightHand() {
		GCompound rightHand = new GCompound();
		GLine upperArm = new GLine(getWidth() / 2, 2 * getHeight() / 8
				+ ARM_OFFSET_FROM_HEAD, getWidth() / 2 + UPPER_ARM_LENGTH, 2
				* getHeight() / 8 + ARM_OFFSET_FROM_HEAD);
		rightHand.add(upperArm);
		GLine lowerArm = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH, 2
				* getHeight() / 8 + ARM_OFFSET_FROM_HEAD, getWidth() / 2
				+ UPPER_ARM_LENGTH, 2 * getHeight() / 8 + ARM_OFFSET_FROM_HEAD
				+ LOWER_ARM_LENGTH);
		rightHand.add(lowerArm);
		add(rightHand);
	}

	/*
	 * This method draws left leg;
	 */
	private void drawLeftLeg() {
		GCompound leftLeg = new GCompound();
		GLine hip = new GLine(getWidth() / 2,
				2 * getHeight() / 8 + BODY_LENGTH, getWidth() / 2 - HIP_WIDTH,
				2 * getHeight() / 8 + BODY_LENGTH);
		leftLeg.add(hip);
		GLine leg = new GLine(getWidth() / 2 - HIP_WIDTH, 2 * getHeight() / 8
				+ BODY_LENGTH, getWidth() / 2 - HIP_WIDTH, 2 * getHeight() / 8
				+ BODY_LENGTH + LEG_LENGTH);
		leftLeg.add(leg);
		add(leftLeg);
	}

	/*
	 * This method draws right leg;
	 */
	private void drawRightLeg() {
		GCompound rightLeg = new GCompound();
		GLine hip = new GLine(getWidth() / 2,
				2 * getHeight() / 8 + BODY_LENGTH, getWidth() / 2 + HIP_WIDTH,
				2 * getHeight() / 8 + BODY_LENGTH);
		rightLeg.add(hip);
		GLine leg = new GLine(getWidth() / 2 + HIP_WIDTH, 2 * getHeight() / 8
				+ BODY_LENGTH, getWidth() / 2 + HIP_WIDTH, 2 * getHeight() / 8
				+ BODY_LENGTH + LEG_LENGTH);
		rightLeg.add(leg);
		add(rightLeg);
	}

	/*
	 * This method draws left foot;
	 */
	private void drawLeftFoot() {
		GLine leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH, 2 * getHeight()
				/ 8 + BODY_LENGTH + LEG_LENGTH, getWidth() / 2 - HIP_WIDTH
				- FOOT_LENGTH, 2 * getHeight() / 8 + BODY_LENGTH + LEG_LENGTH);
		add(leftFoot);
	}

	/*
	 * This method draws right foot;
	 */
	private void drawRightFoot() {
		GLine rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH, 2 * getHeight()
				/ 8 + BODY_LENGTH + LEG_LENGTH, getWidth() / 2 + HIP_WIDTH
				+ FOOT_LENGTH, 2 * getHeight() / 8 + BODY_LENGTH + LEG_LENGTH);
		add(rightFoot);
	}

	/*
	 * This method draws head;
	 */
	private void drawHead() {
		head = new GOval(getWidth() / 2 - HEAD_RADIUS, 2 * getHeight() / 8 - 2
				* HEAD_RADIUS, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head);
	}

	/*
	 * This method draws body;
	 */
	private void addBody() {
		GLine body = new GLine(getWidth() / 2, 2 * getHeight() / 8,
				getWidth() / 2, 2 * getHeight() / 8 + BODY_LENGTH);
		add(body);
	}

	/*
	 * This method draws scaffold; Groups elements of scaffold with GCompound;
	 */
	private void drawScaffold() {
		GCompound scaffold = new GCompound();
		GLine scaff = new GLine(getWidth() / 2 - BEAM_LENGTH, 2 * getHeight()
				/ 8 - 2 * HEAD_RADIUS - ROPE_LENGTH, getWidth() / 2
				- BEAM_LENGTH, 2 * getHeight() / 8 - 2 * HEAD_RADIUS
				- ROPE_LENGTH + SCAFFOLD_HEIGHT);
		scaffold.add(scaff);
		GLine beam = new GLine(getWidth() / 2, 2 * getHeight() / 8 - 2
				* HEAD_RADIUS - ROPE_LENGTH, getWidth() / 2 - BEAM_LENGTH, 2
				* getHeight() / 8 - 2 * HEAD_RADIUS - ROPE_LENGTH);
		scaffold.add(beam);
		GLine rope = new GLine(getWidth() / 2, 2 * getHeight() / 8 - 2
				* HEAD_RADIUS, getWidth() / 2, 2 * getHeight() / 8 - 2
				* HEAD_RADIUS - ROPE_LENGTH);
		scaffold.add(rope);
		add(scaffold);
	}

	/*
	 * This method add hidden word on canvas;
	 */
	private void createDisplayWord(String word) {
		hiddenWord = new GLabel(word);
		double x = 40;
		double y = getHeight() / 8 * 7 + 10;
		hiddenWord.setFont("-24");
		add(hiddenWord, x, y);
	}

	public void dance() throws InterruptedException {
		GLabel win = new GLabel("VICTORY!");
		removeAll();
		surv = MediaTools.loadAudioClip("survive.wav");
		GImage image = new GImage("dance.gif");
		add(image, getWidth(), 2 * getHeight() / 8 - 2 * HEAD_RADIUS + 1);
		surv.loop();
		isPlayin = true;
		while (image.getX() > 43) {
			image.move(-2, 0);
			Thread.sleep(25);
		}
		win.setColor(Color.BLUE);
		win.setFont("-24");
		add(win, getWidth() / 2 - win.getWidth() / 2, win.getAscent());

	}

	public void die() {
		GLabel lose = new GLabel("DEFEAT!");
		AudioClip die = MediaTools.loadAudioClip("no.wav");
		GImage image = new GImage("dead.png");
		remove(head);
		add(image, 44, 2 * getHeight() / 8 - 2 * HEAD_RADIUS + 1);
		lose.setColor(Color.RED);
		lose.setFont("-24");
		add(lose, getWidth() / 2 - lose.getWidth() / 2, lose.getAscent());
		die.play();
	}

	/* Instance Variables */
	private GLabel hiddenWord;
	private String wrongLetters = "";
	private Set<Character> set = new HashSet<Character>();
	private boolean isPlayin;
	private AudioClip surv;
	private GOval head;

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
}