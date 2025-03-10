package obj.method;

import obj.Todo;
import obj.adt.ProgramMainADT;
import obj.utils.CResponse;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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

    // Add a new Todo
    public void addTodo() {
        System.out.println("Starting to add a new Todo...");

        String title;
        String description;
        CResponse titleResponse, descriptionResponse;
        String uniqueId = randomTodoId();
        Todo newTodo = new Todo(uniqueId);

        // Keep asking until a valid title is entered
        do {
            title = InputHandler.getString("Enter Todo Title: ");
            titleResponse = newTodo.setTitle(title);
            if (!titleResponse.status) {
                OutputHandler.PrintWarningLog(titleResponse.message); // Invalid title
            }
        } while (!titleResponse.status);

        // Keep asking until a valid description is entered
        do {
            description = InputHandler.getString("Enter Todo Description: ");
            descriptionResponse = newTodo.setDescription(description);
            if (!descriptionResponse.status) {
                OutputHandler.PrintWarningLog(descriptionResponse.message); // Invalid description
            }
        } while (!descriptionResponse.status);

        // Add to ProgramMainADT
        CResponse addResponse = data.addTodo(newTodo);

        if (addResponse.status) {
            OutputHandler.PrintSuccessLog(addResponse.message); // Success
        } else {
            OutputHandler.PrintWarningLog(addResponse.message); // Failure
        }
    }

    public void updateTodo() {
        System.out.println("Updating a Todo...");

        // Display all todos before updating
        data.displayTodos();

        while (true) {
            // Ask for the Todo ID
            String todoId = InputHandler.getString("Enter Todo ID to update (or 'q' to quit): ");

            // Exit if user inputs 'q'
            if (todoId.equalsIgnoreCase("q")) {
                System.out.println("Update cancelled.");
                return;
            }

            // Find the Todo by ID
            Todo todoToUpdate = data.getTodoById(todoId);
            if (todoToUpdate == null) {
                OutputHandler.PrintWarningLog("Todo ID not found! Please try again.");
                continue;
            }

            // Ask for a new title
            String newTitle = InputHandler.getString("Enter new Title (leave empty to keep current): ");
            if (newTitle.trim().isEmpty()) {
                newTitle = null; // Keep current title
            }

            // Ask for a new description
            String newDescription = InputHandler.getString("Enter new Description (leave empty to keep current): ");
            if (newDescription.trim().isEmpty()) {
                newDescription = null; // Keep current description
            }

            // Call updateTodoById method
            CResponse updateResponse = data.updateTodoById(todoId, newTitle, newDescription);

            // Output the result
            if (updateResponse.status) {
                OutputHandler.PrintSuccessLog(updateResponse.message);
            } else {
                OutputHandler.PrintWarningLog(updateResponse.message);
            }

            return;
        }
    }

    public void deleteTodo() {
        System.out.println("Deleting a Todo...");

        // Display all todos before deleting
        data.displayTodos();

        do {
            // Ask for the Todo ID
            String todoId = InputHandler.getString("Enter Todo ID to delete (or 'q' to quit): ");

            // Exit if user inputs 'q'
            if (todoId.equalsIgnoreCase("q")) {
                System.out.println("Deletion cancelled.");
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
        } while (true);
    }

    public void tweakTodo() {
        System.out.println("Tweaking a Todo...");

        // Display all todos before tweaking
        data.displayTodos();

        while (true) {
            // Ask for the Todo ID
            String todoId = InputHandler.getString("Enter Todo ID to tweak (or 'q' to quit): ");

            // Exit if user inputs 'q'
            if (todoId.equalsIgnoreCase("q")) {
                System.out.println("Tweaking cancelled.");
                return;
            }

            // Find the Todo by ID
            Todo todoToTweak = data.getTodoById(todoId);
            if (todoToTweak == null) {
                OutputHandler.PrintWarningLog("Todo ID not found! Please try again.");
                continue;
            }

            // Ask if user wants to mark it as completed or uncompleted
            String statusInput = InputHandler.getString("Do you want to mark this Todo as completed? (yes/no): ");
            boolean status = statusInput.equalsIgnoreCase("yes");

            // Call markTodoById method
            CResponse markResponse = data.markTodoById(todoId, status);

            // Output the result
            if (markResponse.status) {
                OutputHandler.PrintSuccessLog(markResponse.message);
            } else {
                OutputHandler.PrintWarningLog(markResponse.message);
            }

            return;
        }
    }

    public void searchTodo() {
        while (true) {
            System.out.println("Searching for Todos by date...");

            String dateInput = InputHandler.getString("Enter date (dd-MM-yyyy) or 'q' to quit: ");
            if (dateInput.equalsIgnoreCase("q")) {
                System.out.println("Search cancelled.");
                return;
            }

            try {
                Date searchDate = dateFormat.parse(dateInput);
                data.showTodoByDate(searchDate);
            } catch (ParseException e) {
                OutputHandler.PrintWarningLog("Invalid date format! Please use dd-MM-yyyy.");
                continue;
            }

            String continueSearch = InputHandler.getString("Do you want to search again? (yes/no): ");
            if (!continueSearch.equalsIgnoreCase("yes")) {
                System.out.println("Exiting search.");
                break;
            }
        }
    }
}
