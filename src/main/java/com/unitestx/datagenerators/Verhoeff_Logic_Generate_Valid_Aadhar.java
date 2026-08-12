package com.unitestx.datagenerators;

import java.util.Random;

public class Verhoeff_Logic_Generate_Valid_Aadhar {
	private static final int[][] d = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 0, 6, 7, 8, 9, 5 },
			{ 2, 3, 4, 0, 1, 7, 8, 9, 5, 6 }, { 3, 4, 0, 1, 2, 8, 9, 5, 6, 7 }, { 4, 0, 1, 2, 3, 9, 5, 6, 7, 8 },
			{ 5, 9, 8, 7, 6, 0, 4, 3, 2, 1 }, { 6, 5, 9, 8, 7, 1, 0, 4, 3, 2 }, { 7, 6, 5, 9, 8, 2, 1, 0, 4, 3 },
			{ 8, 7, 6, 5, 9, 3, 2, 1, 0, 4 }, { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 } };

	private static final int[][] p = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, { 1, 5, 7, 6, 2, 8, 3, 0, 9, 4 },
			{ 5, 8, 0, 3, 7, 9, 6, 1, 4, 2 }, { 8, 9, 1, 6, 0, 4, 3, 5, 2, 7 }, { 9, 4, 5, 3, 1, 2, 6, 8, 7, 0 },
			{ 4, 2, 8, 6, 5, 7, 3, 9, 0, 1 }, { 2, 7, 9, 3, 8, 0, 6, 4, 1, 5 }, { 7, 0, 4, 6, 9, 1, 3, 2, 5, 8 } };

	private static final int[] inv = { 0, 4, 3, 2, 1, 5, 6, 7, 8, 9 };

	// Validate a number string using Verhoeff (expects numeric string)
	public static boolean isValidVerhoeff(String num) {
		int c = 0;
		int[] numArr = new int[num.length()];
		// reverse digits into array of ints
		for (int i = 0; i < num.length(); i++) {
			numArr[i] = Character.getNumericValue(num.charAt(num.length() - 1 - i));
		}
		for (int i = 0; i < numArr.length; i++) {
			c = d[c][p[i % 8][numArr[i]]];
		}
		return c == 0;
	}

	// Compute Verhoeff check digit for the provided numeric string (no check digit
	// appended)
	public static int computeCheckDigit(String num) {
		int c = 0;
		int[] numArr = new int[num.length()];
		// reverse digits into array of ints
		for (int i = 0; i < num.length(); i++) {
			numArr[i] = Character.getNumericValue(num.charAt(num.length() - 1 - i));
		}
		for (int i = 0; i < numArr.length; i++) {
			// note: for generation we use p[(i+1) % 8]
			c = d[c][p[(i + 1) % 8][numArr[i]]];
		}
		return inv[c];
	}

	// Generate a single valid 12-digit Aadhaar-like number
	public static String generateAadhaarRandom(Random rnd) {
		StringBuilder sb = new StringBuilder(11);
		for (int i = 0; i < 11; i++) {
			sb.append(rnd.nextInt(10));
		}
		int check = computeCheckDigit(sb.toString());
		sb.append(check);
		return sb.toString();
	}

	// Generate N valid Aadhaar-like numbers
	public static String[] generateBatch(int count) {
		Random rnd = new Random();
		String[] arr = new String[count];
		for (int i = 0; i < count; i++) {
			arr[i] = generateAadhaarRandom(rnd);
		}
		return arr;
	}

	public static String generateTestAadharNum() {
		String[] samples = generateBatch(1);
		String aadharNo = samples[0];
		return aadharNo;
	}

	// quick demo / smoke test
	public static void main(String[] args) {
		// generate and validate 5 samples
		String[] samples = generateBatch(5);

		System.out.println("Generated Aadhaar-like numbers (12 digits):");
		for (String s : samples) {

			System.out.println(s + "   valid? " + isValidVerhoeff(s));
		}

		// example: validate a specific number

		String test = "123456789016"; // example; will be true only if check digit matches
		System.out.println("\nTest number " + test + " valid? " + isValidVerhoeff(test));

	}
}
