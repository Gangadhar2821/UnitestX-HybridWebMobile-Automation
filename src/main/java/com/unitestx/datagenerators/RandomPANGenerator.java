package com.unitestx.datagenerators;

import java.util.Random;

public class RandomPANGenerator {

	// Method to generate random PAN number
	public static String generatePAN() {
		Random random = new Random();
		StringBuilder pan = new StringBuilder();

		// First 5 letters
		for (int i = 0; i < 5; i++) {
			char letter = (char) ('A' + random.nextInt(26));
			pan.append(letter);
		}

		// Next 4 digits
		for (int i = 0; i < 4; i++) {
			int digit = random.nextInt(10);
			pan.append(digit);
		}

		// Last letter
		char lastLetter = (char) ('A' + random.nextInt(26));
		pan.append(lastLetter);

		return pan.toString();
	}

	public static String generateTestPanNum() {
		String panNum = generatePAN();
		return panNum;
	}

	public static void main(String[] args) {
		// Generate 10 PAN numbers
		for (int i = 0; i < 10; i++) {
			System.out.println(generatePAN());
		}
	}
}
