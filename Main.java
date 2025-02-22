import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SecureRandom random = new SecureRandom();

        System.out.print("How many passwords would you like to generate? ");
        int numPasswords = scanner.nextInt();
        scanner.nextLine();

        System.out.println();

        System.out.print("Please enter words and/or numbers to include in your password (comma-separated): ");
        String customInput = scanner.nextLine().trim();
        List<String> customWords = new ArrayList<>();
        if (!customInput.isEmpty()) {
            String[] splitInput = customInput.split(",");
            for (String word : splitInput) {
                customWords.add(word.trim());
            }
        }

        System.out.println();

        System.out.print("Please enter password length (min 6): ");
        int length = scanner.nextInt();
        scanner.nextLine();
        length = Math.max(length, 6);

        System.out.println();


        System.out.print("Include lowercase letters? (yes/no): ");
        boolean useLower = scanner.nextLine().trim().equalsIgnoreCase("yes");
        System.out.print("Include uppercase letters? (yes/no): ");
        boolean useUpper = scanner.nextLine().trim().equalsIgnoreCase("yes");
        System.out.print("Include numbers? (yes/no): ");
        boolean useNumbers = scanner.nextLine().trim().equalsIgnoreCase("yes");
        System.out.print("Include symbols? (yes/no): ");
        boolean useSymbols = scanner.nextLine().trim().equalsIgnoreCase("yes");

        System.out.println();

        for (int i = 0; i < numPasswords; i++) {
            String password = generatePassword(length, useLower, useUpper, useNumbers, useSymbols, customWords, random);
            System.out.println("Your new password " + (i + 1) + ": " + password);
            System.out.println("Strength of this password: " + checkStrength(password));
        }

        scanner.close();
    }

    private static String generatePassword(int length, boolean useLower, boolean useUpper, boolean useNumbers, boolean useSymbols, List<String> customWords, SecureRandom random) {
        String charPool = "";
        if (useLower) charPool += LOWERCASE;
        if (useUpper) charPool += UPPERCASE;
        if (useNumbers) charPool += NUMBERS;
        if (useSymbols) charPool += SYMBOLS;


        StringBuilder password = new StringBuilder();
        List<String> remainingWords = new ArrayList<>(customWords);


        while (!remainingWords.isEmpty() && password.length() < length) {
            String word = remainingWords.remove(random.nextInt(remainingWords.size()));
            password.append(word);
        }


        while (password.length() < length) {
            password.append(charPool.charAt(random.nextInt(charPool.length())));
        }


        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        Collections.shuffle(passwordChars);


        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }


    private static String checkStrength(String password) {
        int score = 0;


        if (password.length() >= 12) score++;  // Reward for long passwords

        if (password.matches(".*[a-z].*")) score++;

        if (password.matches(".*[A-Z].*")) score++;

        if (password.matches(".*[0-9].*")) score++;

        if (password.matches(".*[!@#$%^&*()-_=+\\[\\]{}|;:'\",.<>?/].*")) score++;

        System.out.println();

        switch (score) {
            case 5:
                return "Very Strong";
            case 4:
                return "Strong";
            case 3:
                return "Moderate";
            case 2:
                return "Weak";
            default:
                return "Very Weak";
        }
    }
}
