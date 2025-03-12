import obj.adt.ProgramMainADT;
import obj.method.TodoMethods;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        // Initializing the main data structure and methods for managing todos
        ProgramMainADT mainADT = new ProgramMainADT();
        TodoMethods todoMethods = new TodoMethods(mainADT);

        // Defining valid range for menu choices
        final int min = 1;
        final int max = 7;

        while (true) {
            try {
                // Display the main menu
                OutputHandler.printBorderMessage("Todo Management System");
                System.out.println("1. Add Todo");
                System.out.println("2. Update Todo");
                System.out.println("3. Delete Todo");
                System.out.println("4. Tweak Todo (Complete/Uncomplete)");
                System.out.println("5. Search Todo by Date");
                System.out.println("6. Display All Todos");
                System.out.println("7. Exit");

                // Get the user's choice
                int choice = InputHandler.getInt("Enter your choice: ", min, max);

                // Process the user's choice
                switch (choice) {
                    case 1:
                        todoMethods.addTodo();  // Calls method to add a new todo
                        System.out.println();
                        break;
                    case 2:
                        todoMethods.updateTodo();  // Calls method to update an existing todo
                        System.out.println();
                        break;
                    case 3:
                        todoMethods.deleteTodo();  // Calls method to delete a todo
                        System.out.println();
                        break;
                    case 4:
                        todoMethods.tweakTodo();  // Calls method to mark a todo as complete/uncompleted
                        System.out.println();
                        break;
                    case 5:
                        todoMethods.searchTodo();  // Calls method to search todos by date
                        System.out.println();
                        break;
                    case 6:
                        mainADT.displayTodos();  // Calls method to display all todos
                        System.out.println( );
                        break;
                    case 7:
                        // Exit the program
                        InputHandler.closeScanner();
                        OutputHandler.PrintSuccessLog("Exiting Todo Management System. Goodbye!");
                        return;
                    default:
                        // Handle invalid input
                        OutputHandler.PrintWarningLog(String.format("Invalid choice! Please enter a number between %d and %d.", min, max));
                }
            } catch (InputMismatchException e) {
                // Handle invalid number input
                OutputHandler.PrintErrorLog("Invalid input! Please enter a valid number.");
                // Clear scanner buffer to prevent infinite loop
                InputHandler.getScanner().nextLine();
            } catch (NullPointerException e) {
                // Handle null reference errors
                OutputHandler.PrintErrorLog("A null reference was encountered. Please check your data.");
            } catch (Exception e) {
                // Handle any other unexpected errors
                OutputHandler.PrintErrorLog("Error: " + e.getMessage());
            }
        }
    }
}
