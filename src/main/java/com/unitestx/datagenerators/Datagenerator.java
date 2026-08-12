package com.unitestx.datagenerators;

import java.security.SecureRandom;
import java.util.Random;

public class Datagenerator {

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

	public static String generateName(char ch) {
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
		String[] VEHICLETYPE = { "3 Wheeler", "4 Wheeler", "2 Wheeler" };
		String[] PINCODES = { "562149", "560029" };
		String[] IFSC = { "SBIN0005931", "SBIN0041201", "SBIN0041190", "SBIN0040784", "SBIN0013346", "SBIN0041002",
				"SBIN0017041", "SBIN0041004", "SBIN0041203", "SBIN0041202", "SBIN0040015", "SBIN0040463", "SBIN0021733",
				"SBIN0018230", "SBIN0070624", "SBIN0032294", "SBIN0040007", "SBIN0050573", "SBIN0070242", "SBIN0051162",
				"SBIN0040432", "SBIN0020852", "SBIN0021615", "SBIN0021745", "SBIN0003287", "SBIN0004200", "SBIN0006559",
				"SBIN0004457", "SBIN0011355", "SBIN0011746", "SBIN0006762", "KARB0000016", "KARB0000020", "KARB0000002",
				"KARB0000014", "KARB0000030", "KARB0000021", "KARB0000012", "KARB0000003" };

		SecureRandom random = new SecureRandom();
		String firstname = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		String middlename = MIDDLE_NAMES[random.nextInt(MIDDLE_NAMES.length)];
		String lastname = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		String address = ADDRESSES[random.nextInt(ADDRESSES.length)];
		String remarks = REMARKS[random.nextInt(REMARKS.length)];
		String status = STATUS[random.nextInt(STATUS.length)];
		String vechicle = VEHICLETYPE[random.nextInt(VEHICLETYPE.length)];
		String ifscCodes = IFSC[random.nextInt(IFSC.length)];
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
		case 'V':
			return vechicle;
		case 'I':
			return ifscCodes;
		case 'P':
			return pincodes;
		default:
			return "Invalid choice!";
		}

	}

	public static String generateAccountNumber() {
		Random random = new Random();
		int length = 16; // total account number length

		StringBuilder acc = new StringBuilder();
		for (int i = 0; i < length; i++) {
			acc.append(random.nextInt(10)); // random digit 0–9
		}

		return acc.toString();
	}

	public static String generateUdyamNumber() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();

		// Helper to generate random letters
		StringBuilder part1 = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			part1.append(letters.charAt(random.nextInt(letters.length())));
		}

		StringBuilder part2 = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			part2.append(letters.charAt(random.nextInt(letters.length())));
		}

		// Helper to generate random digits
		StringBuilder part3 = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			part3.append(random.nextInt(10));
		}

		StringBuilder part4 = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			part4.append(random.nextInt(10));
		}

		return part1 + "-" + part2 + "-" + part3 + "-" + part4;
	}

	public static String generateVoterID() {
		Random rand = new Random();
		StringBuilder letters = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			char letter = (char) ('A' + rand.nextInt(26));
			letters.append(letter);
		}
		StringBuilder digits = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			digits.append(rand.nextInt(10));
		}
		// Combine letters + digits
		return letters.toString() + digits.toString();
	}

	public static String generateAadharNumber() {
		return Verhoeff_Logic_Generate_Valid_Aadhar.generateTestAadharNum();
	}

	public static String generatePANNumber() {
		return RandomPANGenerator.generateTestPanNum();
	}

}
