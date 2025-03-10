package obj.adt;

import obj.Todo;
import obj.utils.CResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class ProgramMainADT {
    LinkedHashMap<Date, LinkedList<Todo>> adt;

    public ProgramMainADT() {
        adt = new LinkedHashMap<>();
    }

    public CResponse addTodo(Todo t) {
        Date today = new Date();

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


}
