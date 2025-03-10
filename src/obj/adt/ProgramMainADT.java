package obj.adt;

import obj.Todo;
import obj.utils.CResponse;

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

    public void displayTodos() {
        if (adt.isEmpty()) {
            System.out.println("No Todos available.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Displaying all Todos:");

        for (Map.Entry<Date, LinkedList<Todo>> entry : adt.entrySet()) {
            System.out.println("Date: " + dateFormat.format(entry.getKey()));
            for (Todo todo : entry.getValue()) {
                todo.display();
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

    public CResponse markTodoById(String todoId, boolean status) {
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
        Date normalizedDate = calendar.getTime();  // Normalize the input date to 00:00:00

        // Check if the Todos for this date exist
        if (adt.containsKey(normalizedDate)) {
            System.out.println("Displaying Todos for " + normalizedDate);

            // Display each Todo in the list for that date
            for (Todo todo : adt.get(normalizedDate)) {
                todo.display();
            }
        } else {
            System.out.println("No Todos found for the date: " + normalizedDate);
        }
    }
}
