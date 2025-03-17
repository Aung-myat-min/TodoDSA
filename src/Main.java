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
        final int max = 9;

        while (true) {
            try {
                // Display the main menu
                OutputHandler.printBorderMessage("Todo Management System");
                System.out.println("1. Add Todo");
                System.out.println("2. Update Todo");
                System.out.println("3. Delete Todo");
                System.out.println("4. Change Todo Status");
                System.out.println("5. Search Todo");
                System.out.println("6. Display Todos for Today");
                System.out.println("7. Display All Todos");
                System.out.println("8. Display Sorted Todos");
                System.out.println("9. Exit");

                // Get the user's choice
                int choice = InputHandler.getInt("Enter your choice: ", min, max);

                // Process the user's choice
                switch (choice) {
                    case 1:
                        todoMethods.addTodo();
                        break;
                    case 2:
                        todoMethods.updateTodo();
                        break;
                    case 3:
                        todoMethods.deleteTodo();
                        break;
                    case 4:
                        todoMethods.updateTodoStatus();
                        break;
                    case 5:
                        todoMethods.searchTodo();
                        break;
                    case 6:
                        todoMethods.showTodayTodos();
                        break;
                    case 7:
                        todoMethods.showAllTodos();
                        break;
                    case 8:
                        todoMethods.sortTodosByDueDateAndStatus();
                        break;
                    case 9:
                        // Exit the program
                        InputHandler.closeScanner();
                        OutputHandler.PrintSuccessLog("Exiting Todo Management System. Goodbye!");
                        return;
                    default:
                        OutputHandler.PrintWarningLog(String.format("Invalid choice! Please enter a number between %d and %d.", min, max));
                }
            } catch (InputMismatchException e) {
                OutputHandler.PrintErrorLog("Invalid input! Please enter a valid number.");
                InputHandler.getScanner().nextLine(); // Clear scanner buffer
            } catch (NullPointerException e) {
                OutputHandler.PrintErrorLog("A null reference was encountered. Please check your data.");
            } catch (Exception e) {
                OutputHandler.PrintErrorLog("Error: " + e.getMessage());
            }
        }
    }
}
