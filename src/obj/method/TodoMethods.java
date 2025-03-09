package obj.method;

import obj.Todo;
import obj.adt.ProgramMainADT;
import obj.utils.CResponse;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.util.UUID;

public class TodoMethods {
    private final ProgramMainADT data;

    public TodoMethods(ProgramMainADT data) {
        this.data = data;
    }

    // Generate a random unique ID
    private String randomTodoId() {
        return UUID.randomUUID().toString();
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
                OutputHandler.PrintWarningLog(titleResponse.message); // Warning for invalid title
            }
        } while (!titleResponse.status);

        // Keep asking until a valid description is entered
        do {
            description = InputHandler.getString("Enter Todo Description: ");
            descriptionResponse = newTodo.setDescription(description);
            if (!descriptionResponse.status) {
                OutputHandler.PrintWarningLog(descriptionResponse.message); // Warning for invalid description
            }
        } while (!descriptionResponse.status);

        // Add to ProgramMainADT
        CResponse addResponse = data.addTodo(newTodo);

        if (addResponse.status) {
            OutputHandler.PrintSuccessLog(addResponse.message); // Success log
        } else {
            OutputHandler.PrintWarningLog(addResponse.message); // Failure log
        }
    }

    public void updateTodo() {
    }

    public void deleteTodo() {
    }

    public void doneTodo() {
    }

    public void searchTodo() {
    }
}
