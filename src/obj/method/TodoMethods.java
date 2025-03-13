package obj.method;

import obj.Todo;
import obj.TodoStatus;
import obj.adt.ProgramMainADT;
import obj.utils.CResponse;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TodoMethods {
    private final ProgramMainADT data;
    private final SimpleDateFormat dateFormat;

    public TodoMethods(ProgramMainADT data) {
        this.data = data;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    }

    // Generate a random unique ID
    private String randomTodoId() {
        return "T-" + String.format("%03d", (int) (Math.random() * 1000));
    }

    public void addTodo() {
        while (true) {
            OutputHandler.printBorderMessage("Starting to add a new Todo...");

            String title;
            String description;
            String dueDateStr;
            CResponse titleResponse, descriptionResponse, dueDateResponse;
            String uniqueId = randomTodoId();
            Todo newTodo = new Todo(uniqueId);

            // Validate title input
            do {
                title = InputHandler.getString("Enter Todo Title: ");
                titleResponse = newTodo.setTitle(title);
                if (!titleResponse.status) {
                    OutputHandler.PrintWarningLog(titleResponse.message);
                }
            } while (!titleResponse.status);

            // Validate description input
            do {
                description = InputHandler.getString("Enter Todo Description: ");
                descriptionResponse = newTodo.setDescription(description);
                if (!descriptionResponse.status) {
                    OutputHandler.PrintWarningLog(descriptionResponse.message);
                }
            } while (!descriptionResponse.status);

            // Validate due date input (New)
            do {
                dueDateStr = InputHandler.getString("Enter Due Date (DD-MM-YYYY): ");
                dueDateResponse = newTodo.setDueDate(dueDateStr);
                if (!dueDateResponse.status) {
                    OutputHandler.PrintWarningLog(dueDateResponse.message);
                }
            } while (!dueDateResponse.status);

            // Add Todo to ProgramMainADT
            CResponse addResponse = data.addTodo(newTodo);
            if (addResponse.status) {
                OutputHandler.PrintSuccessLog(addResponse.message);
            } else {
                OutputHandler.PrintWarningLog(addResponse.message);
            }

            // Ask if user wants to add another Todo
            String continueAdding = InputHandler.getString("Do you want to add another Todo? (yes): ");
            if (!continueAdding.equalsIgnoreCase("yes")) {
                OutputHandler.PrintWarningLog("Stopping Todo addition.");
                break;
            }
        }
    }

    public void updateTodo() {
        OutputHandler.printBorderMessage("Updating a Todo...");

        data.displayTodos();

        if (data.getSize() > 0) {
            while (true) {
                String todoId = InputHandler.getString("Enter Todo ID to update (or 'q' to quit): ");
                if (todoId.equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Update cancelled.");
                    return;
                }

                Todo todoToUpdate = data.getTodoById(todoId);
                if (todoToUpdate == null) {
                    OutputHandler.PrintWarningLog("Todo ID not found! Please try again.");
                    continue;
                }

                // Title update (Keep asking until valid or skipped)
                while (true) {
                    String newTitle = InputHandler.getString("Enter new Title (leave empty to keep current): ");
                    if (newTitle.trim().isEmpty()) break;

                    CResponse titleResponse = todoToUpdate.setTitle(newTitle);
                    if (titleResponse.status) {
                        OutputHandler.PrintSuccessLog("Title updated successfully!");
                        break;
                    } else {
                        OutputHandler.PrintWarningLog(titleResponse.message);
                    }
                }

                // Description update (Keep asking until valid or skipped)
                while (true) {
                    String newDescription = InputHandler.getString("Enter new Description (leave empty to keep current): ");
                    if (newDescription.trim().isEmpty()) break;

                    CResponse descriptionResponse = todoToUpdate.setDescription(newDescription);
                    if (descriptionResponse.status) {
                        OutputHandler.PrintSuccessLog("Description updated successfully!");
                        break;
                    } else {
                        OutputHandler.PrintWarningLog(descriptionResponse.message);
                    }
                }

                // Due date update (Keep asking until valid or skipped)
                while (true) {
                    String newDueDate = InputHandler.getString("Enter new Due Date (DD-MM-YYYY) (leave empty to keep current): ");
                    if (newDueDate.trim().isEmpty()) break;

                    CResponse dueDateResponse = todoToUpdate.setDueDate(newDueDate);
                    if (dueDateResponse.status) {
                        OutputHandler.PrintSuccessLog("Due Date updated successfully!");
                        break;
                    } else {
                        OutputHandler.PrintWarningLog(dueDateResponse.message);
                    }
                }

                OutputHandler.PrintSuccessLog("Todo updated successfully!");
                return;
            }
        }
    }

    public void deleteTodo() {
        OutputHandler.printBorderMessage("Deleting a Todo...");

        // Display all todos before deleting
        data.displayTodos();

        if (data.getSize() > 0) {
            while (true) {
                // Ask for the Todo ID
                String todoId = InputHandler.getString("Enter Todo ID to delete (or 'q' to quit): ");

                // Exit if user inputs 'q'
                if (todoId.equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Deletion cancelled.");
                    return;
                }

                // Check if the ID exists
                Todo todoToDelete = data.getTodoById(todoId);
                if (todoToDelete == null) {
                    OutputHandler.PrintWarningLog("Todo ID not found! Please try again.");
                    continue;
                }

                // Confirm deletion
                String confirm = InputHandler.getString("Are you sure you want to delete this Todo? (yes): ");
                if (!confirm.equalsIgnoreCase("yes")) {
                    OutputHandler.PrintWarningLog("Deletion cancelled.");
                    return;
                }

                // Call deleteTodoById method
                CResponse deleteResponse = data.deleteTodoById(todoId);

                // Output the result
                if (deleteResponse.status) {
                    OutputHandler.PrintSuccessLog(deleteResponse.message);
                } else {
                    OutputHandler.PrintWarningLog(deleteResponse.message);
                }

                return;
            }
        }
    }

    public void updateTodoStatus() {
        OutputHandler.printBorderMessage("Updating Todo Status...");

        if (data.getSize() > 0) {
            while (true) {
                // Ask for the Todo ID or allow search
                String todoId = InputHandler.getString("Enter Todo ID to update status (or type 'search' to find one, 'q' to quit): ");

                // Handle user exit
                if (todoId.equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Update cancelled.");
                    return;
                }

                // Allow user to search for a Todo
                if (todoId.equalsIgnoreCase("search")) {
                    data.displayTodos();  // Assuming you already have this method
                    continue; // Restart loop to ask for ID again
                }

                // Find the Todo by ID
                Todo todoToUpdate = data.getTodoById(todoId);
                if (todoToUpdate == null) {
                    OutputHandler.PrintWarningLog("Todo ID not found! Please try again.");
                    continue;
                }

                // Show current status
                System.out.println("Current Status: " + todoToUpdate.getStatus());

                // Display status options
                System.out.println("Select the new status:");
                for (TodoStatus status : TodoStatus.values()) {
                    System.out.println(status.ordinal() + 1 + ". " + status);
                }

                TodoStatus newStatus = null;
                while (newStatus == null) {
                    try {
                        int option = Integer.parseInt(InputHandler.getString("Enter your choice (1-3): "));
                        if (option >= 1 && option <= TodoStatus.values().length) {
                            newStatus = TodoStatus.values()[option - 1];
                        } else {
                            OutputHandler.PrintWarningLog("Invalid option! Please enter a number between 1 and " + TodoStatus.values().length + ".");
                        }
                    } catch (NumberFormatException e) {
                        OutputHandler.PrintWarningLog("Invalid input! Please enter a valid number.");
                    }
                }

                // Confirm before updating
                String confirm = InputHandler.getString("Are you sure you want to change status to " + newStatus + "? (yes): ");
                if (!confirm.equalsIgnoreCase("yes")) {
                    OutputHandler.PrintWarningLog("Status update cancelled.");
                    return;
                }

                // Call method to update status
                CResponse updateResponse = data.markTodoById(todoId, newStatus);

                // Output the result
                if (updateResponse.status) {
                    OutputHandler.PrintSuccessLog(updateResponse.message);
                } else {
                    OutputHandler.PrintWarningLog(updateResponse.message);
                }

                return; // Exit function after successfully updating the status
            }
        } else {
            OutputHandler.PrintWarningLog("Add at least 1 Todo to update status!");
        }
    }

    public void searchTodo() {
        if (data.getSize() > 0) {
            while (true) {
                OutputHandler.printBorderMessage("Search Todo....");
                System.out.println("Search Options:");
                System.out.println("1. Search by partial date");
                System.out.println("2. Search by title");
                int choice = InputHandler.getInt("Enter your choice (1 or 2) or '-1' to quit:", -1, 2);
                if (choice == -1) {
                    OutputHandler.PrintWarningLog("Search cancelled.");
                    return;
                }
                switch (choice) {
                    case 1:
                        searchTodoByPartialDate();
                        break;
                    case 2:
                        searchTodoByTitle();
                        break;
                    default:
                        OutputHandler.PrintWarningLog("Invalid choice! Please enter 1, 2, or '-1' to quit.");
                }
            }
        } else {
            OutputHandler.PrintWarningLog("Add at least 1 Todo to search!");
        }
    }

    public void showTodayTodos() {
        OutputHandler.printBorderMessage("Showing Todos for Today...");

        // List to hold the matched Todos
        LinkedList<Todo> todayTodos = data.getTodayTodo();

        // Output the result
        if (todayTodos.isEmpty()) {
            OutputHandler.PrintWarningLog("No Todos are due today.");
        } else {
            OutputHandler.PrintSuccessLog("Todos due today:");
            // Loop through all Todos and check if their due date matches today's date
            for (Todo todo : todayTodos) {
                todo.display();
            }
        }
    }

    public void showAllTodos() {
        data.showAllTodos();
    }

    public void sortTodosByDueDateAndSort() {
        OutputHandler.printBorderMessage("Sorting Todos by Due Date and Status...");

        // Get the sorted map from ProgramMainADT
        LinkedHashMap<Date, LinkedList<Todo>> sortedTodos = data.sortTodosByDueDateAndStatus();

        // Output the result
        if (sortedTodos.isEmpty()) {
            OutputHandler.PrintWarningLog("No Todos available to sort.");
        } else {
            OutputHandler.PrintSuccessLog("Todos sorted by Due Date and Status:");
            for (Map.Entry<Date, LinkedList<Todo>> entry : sortedTodos.entrySet()) {
                System.out.println("\n========== Due Date: " + entry.getKey() + " ==========");
                for (Todo todo : entry.getValue()) {
                    todo.display();
                }
            }
        }
    }


    private void searchTodoByPartialDate() {
        OutputHandler.printBorderMessage("Searching for Todos by partial date...");

        while (true) {
            String partialDate = InputHandler.getString("Enter partial date (e.g., MM-yyyy or yyyy) or 'q' to quit: ");
            if (partialDate.equalsIgnoreCase("q")) {
                OutputHandler.PrintWarningLog("Search cancelled.");
                return;
            }

            Date searchDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            dateFormat.setLenient(false);
            try {
                searchDate = dateFormat.parse(partialDate);
            } catch (ParseException e) {
                OutputHandler.PrintWarningLog("Invalid date format! Please use MM-yyyy.");
                continue;
            }

            ArrayList<Todo> matchedTodos = data.searchByPartialDate(searchDate);


            if (matchedTodos.isEmpty()) {
                OutputHandler.PrintWarningLog("No Todos found with the given partial date.");
            } else {
                OutputHandler.PrintSuccessLog("Todos found with the given partial date:");
                for (Todo todo : matchedTodos) {
                    todo.display();
                }
            }

            String continueSearch = InputHandler.getString("Do you want to search again? (yes): ");
            if (!continueSearch.equalsIgnoreCase("yes")) {
                OutputHandler.PrintWarningLog("Exiting search.");
                break;
            }
        }
    }

    private void searchTodoByTitle() {
        OutputHandler.printBorderMessage("Searching for Todos by title...");

        while (true) {
            String title = InputHandler.getString("Enter title to search (or 'q' to quit): ");
            if (title.equalsIgnoreCase("q")) {
                OutputHandler.PrintWarningLog("Search cancelled.");
                return;
            }

            ArrayList<Todo> matchedTodos = data.searchTodoByTitle(title);

            if (matchedTodos.isEmpty()) {
                OutputHandler.PrintWarningLog("No Todos found with the given title.");
            } else {
                OutputHandler.PrintSuccessLog("Todos found with the given title:");
                for (Todo todo : matchedTodos) {
                    todo.display();
                }
            }

            String continueSearch = InputHandler.getString("Do you want to search again? (yes): ");
            if (!continueSearch.equalsIgnoreCase("yes")) {
                OutputHandler.PrintWarningLog("Exiting search.");
                break;
            }
        }
    }
}