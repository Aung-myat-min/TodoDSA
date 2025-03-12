package obj.adt;

import obj.Todo;
import obj.TodoStatus;
import obj.sorting.TodoSorter;
import obj.utils.CResponse;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.text.SimpleDateFormat;
import java.util.*;

public class ProgramMainADT {
    LinkedHashMap<Date, LinkedList<Todo>> adt;

    public ProgramMainADT() {
        adt = new LinkedHashMap<>();
    }

    public CResponse addTodo(Todo t) {
        // Set the calendar to today's date
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();  // Get the date with 0 hour, 0 min, 0 sec

        // If no list exists for today, create one
        adt.putIfAbsent(today, new LinkedList<>());

        // Add the new Todo to today's list
        adt.get(today).add(t);

        return new CResponse(true, "Todo added successfully for " + today);
    }

    public int getSize() {
        return adt.size();
    }

    public void displayTodos() {
        if (adt.isEmpty()) {
            OutputHandler.PrintWarningLog("No Todos available.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Displaying all Todos:");

        for (Map.Entry<Date, LinkedList<Todo>> entry : adt.entrySet()) {
            System.out.println("Date: " + dateFormat.format(entry.getKey()));

            for (Todo todo : entry.getValue()) {
                // Display the Todo details
                todo.display();

                // Ask user to continue or quit
                String userInput = InputHandler.getString("Press [space] to continue or [q] to quit: ").trim();

                // Handle user input
                if (userInput.equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Exiting the todo display.");
                    return; // Stop displaying todos
                } else if (!userInput.isEmpty()) {
                    // Handle invalid input
                    OutputHandler.PrintWarningLog("Invalid input! Please press [space] to continue or [q] to quit.");
                }
            }
        }
    }


    public Todo getTodoById(String todoId) {
        for (Date date : adt.keySet()) {
            for (Todo todo : adt.get(date)) {
                if (todo.getUniqueId().equalsIgnoreCase(todoId)) {
                    return todo;
                }
            }
        }
        return null; // Return null if not found
    }

    public CResponse updateTodoById(String todoId, String title, String description) {
        Todo todo = getTodoById(todoId);

        if (todo == null) {
            return new CResponse(false, "Todo with ID " + todoId + " not found!");
        }

        boolean updated = false;
        if (title != null && !title.trim().isEmpty()) {
            CResponse titleResponse = todo.setTitle(title);
            if (!titleResponse.status) {
                return titleResponse;
            }
        }

        if (description != null && !description.trim().isEmpty()) {
            CResponse descResponse = todo.setDescription(description);
            if (!descResponse.status) {
                return descResponse;
            }
        }

        updated = true;

        if (updated) {
            return new CResponse(true, "Todo updated successfully!");
        } else {
            return new CResponse(false, "No changes made to the Todo.");
        }
    }

    public CResponse deleteTodoById(String todoId) {
        for (Date date : adt.keySet()) {
            LinkedList<Todo> todoList = adt.get(date);

            // Search for the todo by ID
            for (Todo todo : todoList) {
                if (todo.getUniqueId().equalsIgnoreCase(todoId)) {
                    todoList.remove(todo);
                    return new CResponse(true, "Todo with ID " + todoId + " deleted successfully!");
                }
            }
        }

        return new CResponse(false, "Todo with ID " + todoId + " not found!");
    }

    public CResponse markTodoById(String todoId, TodoStatus status) {
        // Get the todo by ID
        Todo todo = getTodoById(todoId);

        if (todo == null) {
            return new CResponse(false, "Todo with ID " + todoId + " not found!");
        }

        // Mark the todo as complete
        todo.setDone(true);
        CResponse markResponse = new CResponse(status, "");

        if (markResponse.status) {
            String statusMessage = status ? "completed!" : "uncompleted!";
            return new CResponse(true, "Todo with ID " + todoId + " marked as" + statusMessage);
        } else {
            return markResponse;
        }
    }

    public void showTodoByDate(Date d) {
        // Format the input date to match the date stored in the map (0 hour, 0 min, 0 sec)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date normalizedDate = calendar.getTime();  // Normalize to 00:00:00

        // Check if the Todos for this date exist
        if (adt.containsKey(normalizedDate)) {
            List<Todo> todos = adt.get(normalizedDate);

            // Sort the Todos
            List<Todo> sortedTodos = TodoSorter.mergeSort(todos);

            // Print header
            System.out.println("\n=================================================================");
            System.out.println("| ID       | Completed | Title                        | Description");
            System.out.println("=================================================================");

            // Print each sorted Todo
            for (Todo todo : sortedTodos) {
                String id = todo.getUniqueId();
                String completed = todo.isDone() ? "Yes" : "No ";
                String title = todo.getTitle();
                String description = todo.getDescription();

                // Format output with fixed width
                System.out.printf("| %-8s | %-9s | %-28s | %s%n", id, completed, title, description);
            }

            System.out.println("=================================================================\n");
        } else {
            System.out.println("No Todos found for the date: " + new SimpleDateFormat("dd-MM-yyyy").format(normalizedDate));
        }
    }

    public void searchTodo() {
    }
}
