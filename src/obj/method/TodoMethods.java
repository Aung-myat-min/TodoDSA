package obj.method;

import obj.Todo;
import obj.adt.ProgramMainADT;
import obj.utils.CResponse;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            String continueAdding = InputHandler.getString("Do you want to add another Todo? (yes/no): ");
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

        if(data.getSize() > 0){
            do {
                // Ask for the Todo ID
                String todoId = InputHandler.getString("Enter Todo ID to delete (or 'q' to quit): ");

                // Exit if user inputs 'q'
                if (todoId.equalsIgnoreCase("q")) {
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
            } while (true);
        }
    }

    public void tweakTodo() {
        OutputHandler.printBorderMessage("Tweaking a Todo...");

        // Display all todos before tweaking
        data.displayTodos();

        if(data.getSize() > 0){
            while (true) {
                // Ask for the Todo ID
                String todoId = InputHandler.getString("Enter Todo ID to tweak (or 'q' to quit): ");

                // Exit if user inputs 'q'
                if (todoId.equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Tweaking cancelled.");
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
    }

    public void searchTodo() {
        System.out.println(data.getSize());
        if(data.getSize() > 2){
            while (true) {
                OutputHandler.printBorderMessage("Searching for Todos by date...");

                // Get date input from user or quit
                String dateInput = InputHandler.getString("Enter date (dd-MM-yyyy) or 'q' to quit: ");
                if (dateInput.equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Search cancelled.");
                    return;
                }

                try {
                    // Parse input date
                    Date searchDate = dateFormat.parse(dateInput);
                    data.showTodoByDate(searchDate);
                } catch (ParseException e) {
                    // Handle invalid date format
                    OutputHandler.PrintWarningLog("Invalid date format! Please use dd-MM-yyyy.");
                    continue;
                }

                // Ask user if they want to continue searching
                String continueSearch = InputHandler.getString("Do you want to search again? (yes/no): ");
                if (!continueSearch.equalsIgnoreCase("yes")) {
                    OutputHandler.PrintWarningLog("Exiting search.");
                    break;
                }
            }
        }else{
            OutputHandler.PrintWarningLog("Add at least 2 Todos to search!");
        }
    }
}
