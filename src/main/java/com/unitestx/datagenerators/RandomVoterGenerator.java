package com.unitestx.datagenerators;
import java.util.Random;

public class RandomVoterGenerator {

    // Method to generate random code like ABC1234567
    public static String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        // First 3 letters
        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            code.append(letter);
        }

        // Next 7 digits
        for (int i = 0; i < 7; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }

        return code.toString();
    }
    public static void main(String[] args) {
        // Generate 10 random codes
        for (int i = 0; i < 10; i++) {
            System.out.println(generateCode());
        }
    }
}
