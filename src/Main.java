import obj.adt.ProgramMainADT;
import obj.method.TodoMethods;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        ProgramMainADT mainADT = new ProgramMainADT();
        TodoMethods todoMethods = new TodoMethods(mainADT);

        while (true) {
            try {
                OutputHandler.printBorderMessage("Todo Management System");
                System.out.println("1. Add Todo");
                System.out.println("2. Update Todo");
                System.out.println("3. Delete Todo");
                System.out.println("4. Tweak Todo (Complete/Uncomplete)");
                System.out.println("5. Search Todo by Date");
                System.out.println("6. Display All Todos");
                System.out.println("7. Exit");

                int choice = InputHandler.getInt("Enter your choice: ", 1, 7);

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
                        todoMethods.tweakTodo();
                        break;
                    case 5:
                        todoMethods.searchTodo();
                        break;
                    case 6:
                        mainADT.displayTodos();
                        break;
                    case 7:
                        OutputHandler.PrintSuccessLog("Exiting Todo Management System. Goodbye!");
                        return;
                    default:
                        OutputHandler.PrintWarningLog("Invalid choice! Please enter a number between 1 and 7.");
                }
            } catch (InputMismatchException e) {
                OutputHandler.PrintErrorLog("Invalid input! Please enter a valid number.");
                // Clear scanner buffer
                InputHandler.getScanner().nextLine();
            } catch (NullPointerException e) {
                OutputHandler.PrintErrorLog("A null reference was encountered. Please check your data.");
            } catch (Exception e) {
                OutputHandler.PrintErrorLog("Error: " + e.getMessage());
            }
        }
    }
}
