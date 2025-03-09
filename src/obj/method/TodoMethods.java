package obj.method;

import obj.Todo;
import obj.adt.ProgramMainADT;
import obj.utils.CResponse;
import obj.utils.InputHandler;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TodoMethods {
    private static final Logger LOGGER = Logger.getLogger(TodoMethods.class.getName());
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
        LOGGER.info("Starting to add a new Todo...");

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
                LOGGER.warning("Invalid Title: " + titleResponse.message);
                System.out.println("⚠ " + titleResponse.message);
            }
        } while (!titleResponse.status);

        // Keep asking until a valid description is entered
        do {
            description = InputHandler.getString("Enter Todo Description: ");
            descriptionResponse = newTodo.setDescription(description);
            if (!descriptionResponse.status) {
                LOGGER.warning("Invalid Description: " + descriptionResponse.message);
                System.out.println("⚠ " + descriptionResponse.message);
            }
        } while (!descriptionResponse.status);

        // Add to data
//        data.addTodo(newTodo);
        LOGGER.info("✅ Todo added successfully with ID: " + uniqueId);
        System.out.println("✅ Todo added successfully with ID: " + uniqueId);
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
