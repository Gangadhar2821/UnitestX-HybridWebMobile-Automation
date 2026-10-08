package com.unitestx.datagenerators;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class TestDatagenerator {

	public static String generateString() {
		String[] PREFIXES = { "sun", "tech", "smart", "green", "bright", "cloud", "swift", "prime" };
		String[] SUFFIXES = { "link", "care", "hub", "point", "flow", "zone", "track", "works" };
		Random RANDOM = new Random();
		String prefix = PREFIXES[RANDOM.nextInt(PREFIXES.length)];
		String suffix = SUFFIXES[RANDOM.nextInt(SUFFIXES.length)];

		return Character.toUpperCase(prefix.charAt(0)) + prefix.substring(1) + Character.toUpperCase(suffix.charAt(0))
				+ suffix.substring(1);
	}

	public static String generateRandomNumber(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Number of digits must be greater than 0");
		}
		StringBuilder sb = new StringBuilder();
		java.util.Random random = new java.util.Random();

		// First digit should not be zero (to ensure n digits)
		sb.append(random.nextInt(9) + 1);

		// Remaining digits can be 0-9
		for (int i = 1; i < n; i++) {
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}

	/**
	 * @author gangadhar.b
	 * @param min range
	 * @param max range
	 * @return random number within the range
	 * 
	 */
	public static String generateRandomNumberInRange(int min, int max) {
		if (min < 0 || max <= min) {
			throw new IllegalArgumentException(
					"Invalid range: max must be greater than min, and min must be non-negative");
		}

		java.util.Random random = new java.util.Random();
		int randomNumber = random.nextInt((max - min) + 1) + min;

		return String.valueOf(randomNumber);
	}

	public static String generateMobileNumber() {
		Random random = new Random();

		// First digit should be 7, 8, or 9
		int firstDigit = 7 + random.nextInt(3); // Generates 7, 8, or 9
		StringBuilder mobileNumber = new StringBuilder();
		mobileNumber.append(firstDigit);

		// Append remaining 9 digits (0–9)
		for (int i = 0; i < 9; i++) {
			mobileNumber.append(random.nextInt(10));
		}

		return mobileNumber.toString();
	}

	public static String generateUniqueStrings(char ch) {
		String[] FIRST_NAMES = { "Aarav", "Vivaan", "Aditya", "Sai", "Ishaan", "Krishna", "Ananya", "Diya", "Aisha",
				"Saanvi", "Pranav", "Rohit", "Karthik", "Vikram", "Neha", "Pooja", "Sneha", "Riya", "Harsha", "Tejas",
				"James", "Oliver", "Ethan", "Liam", "Noah", "Emma", "Olivia", "Ava", "Sophia", "Mia", "Lucas",
				"Benjamin", "Charlotte", "Amelia", "Isabella", "Henry", "Jack", "Emily", "Grace", "Chloe" };

		String[] LAST_NAMES = { "Sharma", "Verma", "Gupta", "Mehta", "Reddy", "Iyer", "Menon", "Patel", "Khan", "Singh",
				"Nair", "Bhat", "Kulkarni", "Deshpande", "Jain", "Kapoor", "Mishra", "Chatterjee", "Mukherjee",
				"Saxena", "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez",
				"Martinez", "Wilson", "Anderson", "Taylor", "Thomas", "Moore", "Jackson", "White", "Harris", "Thompson",
				"Martin" };

		String[] MIDDLE_NAMES = { "Kumar", "Prasad", "Raj", "Singh", "Anand", "Rao", "Chandra", "Mohan", "Lal", "Dev",
				"James", "Lee", "Marie", "Grace", "Rose", "Ann", "John", "Paul", "Ray", "Jane", };

		String[] ADDRESSES = { "Indiranagar", "Koramangala", "Whitefield", "Hebbal", "Jayanagar", "HSR", "BTM",
				"Marathahalli", "Yelahanka", "Malleshwaram", "Richmond", "Ulsoor", "Vasanthnagar", "Rajajinagar",
				"Banashankari", "Domlur", "Sarjapur", "ElectronicCity", "Bellandur", "Kengeri" };

		String[] REMARKS = { "Approved", "OK", "Accepted", "Incomplete", "Pass" };
		String[] STATUS = { "Active", "Inactive" };
		String[] PINCODES = { "562149", "560029" };

		SecureRandom random = new SecureRandom();
		String firstname = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String middlename = MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
		String lastname = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		String address = ADDRESSES[random.nextInt(ADDRESSES.length)];
		String remarks = REMARKS[random.nextInt(REMARKS.length)];
		String status = STATUS[random.nextInt(STATUS.length)];
		String pincodes = PINCODES[random.nextInt(PINCODES.length)];

		switch (ch) {
		case 'F':
			return firstname;
		case 'M':
			return middlename;
		case 'L':
			return lastname;
		case 'A':
			return address;
		case 'R':
			return remarks;
		case 'S':
			return status;
		case 'P':
			return pincodes;
		default:
			return "Invalid choice!";
		}

	}

	public static String getTomorrowDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
		return LocalDate.now().plusDays(1).format(formatter);
	}

	public static String getFutureDate(long noOfDays) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
		return LocalDate.now().plusDays(1 + noOfDays).format(formatter);
	}

}
