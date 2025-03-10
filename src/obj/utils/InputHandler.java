package obj.utils;

import exceptions.NumberOutOfRangeException;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    // Read a string input safely
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Read an integer input safely with validation
    public static int getInt(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int number = Integer.parseInt(scanner.nextLine().trim());

                // Check if number is within range
                if (number < min || number > max) {
                    throw new NumberOutOfRangeException(String.format("Number out of range! Please enter a number between %d and %d.", min, max));
                }

                return number;
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mError: Invalid input! Please enter a valid number.\u001B[0m"); // Red text
            } catch (NumberOutOfRangeException e) {
                System.out.println("\u001B[33mWarning: " + e.getMessage() + "\u001B[0m"); // Yellow text
            }
        }
    }

    // Close scanner when done
    public static void closeScanner() {
        scanner.close();
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
