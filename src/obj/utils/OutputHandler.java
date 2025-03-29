package obj.utils;

public class OutputHandler {
    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    // Print Success log in GREEN
    public static void PrintSuccessLog(String message) {
        System.out.println();
        System.out.println(GREEN + message + RESET);
        System.out.println();
    }

    // Print warning/error log in YELLOW or RED
    public static void PrintWarningLog(String message) {
        System.out.println();
        System.out.println(YELLOW + "⚠ " + message + RESET);
        System.out.println();
    }

    // Helper method to print bordered text
    public static void printBorderMessage(String message) {
        int width = message.length() + 10; // Adjust the padding
        String border = "=".repeat(width);

        System.out.println(border);
        System.out.printf("%" + (width / 2 + message.length() / 2) + "s%n", message);
        System.out.println(border);
    }

    // Helper method to print error log in RED
    public static void PrintErrorLog(String message) {
        System.out.println();
        System.out.println(RED + "[ERROR] " + message + RESET);
        System.out.println();
    }
}
