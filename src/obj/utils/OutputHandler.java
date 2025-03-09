package obj.utils;

public class OutputHandler {
    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    // Print normal log in GREEN
    public static void PrintNormalLog(String message) {
        System.out.println(GREEN + message + RESET);
    }

    // Print warning/error log in YELLOW or RED
    public static void PrintWarningLog(String message) {
        System.out.println(YELLOW + "⚠ " + message + RESET);
    }
}
