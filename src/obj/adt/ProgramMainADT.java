package obj.adt;

import obj.Todo;
import obj.TodoStatus;
import obj.sorting.TodoSorter;
import obj.utils.CResponse;
import obj.utils.InputHandler;
import obj.utils.OutputHandler;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Displaying all Todos:");

        for (Map.Entry<Date, LinkedList<Todo>> entry : adt.entrySet()) {
            System.out.println("Date: " + dateFormat.format(entry.getKey()));

            Iterator<Todo> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                // Display the first Todo
                Todo todo1 = iterator.next();
                todo1.display();

                // Display the second Todo if available
                if (iterator.hasNext()) {
                    Todo todo2 = iterator.next();
                    todo2.display();
                } else {
                    break;
                }

                // Ask user to continue or quit
                System.out.println("Press enter to continue or [q] to stop displaying Todos.");
                String userInput = InputHandler.getString(": ");

                // Handle user input
                if (userInput.trim().equalsIgnoreCase("q")) {
                    OutputHandler.PrintWarningLog("Exiting the todo display.");
                    return;
                }
            }
        }
    }

    public void showAllTodos() {
        System.out.println();
        if (adt.isEmpty()) {
            OutputHandler.PrintWarningLog("No Todos available.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        OutputHandler.printBorderMessage("Displaying all Todos:");

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

    public CResponse markTodoById(String todoId, TodoStatus status) {
        // Get the todo by ID
        Todo todo = getTodoById(todoId);

        if (todo == null) {
            return new CResponse(false, "Todo with ID " + todoId + " not found!");
        }

        // Mark the todo as complete
        todo.setStatus(status);
        CResponse markResponse = new CResponse(true, "");

        if (markResponse.status) {
            return new CResponse(true, "Todo with ID " + todoId + " marked as " + valueOf(status).toLowerCase() + "!");
        } else {
            return markResponse;
        }
    }

    public ArrayList<Todo> searchByPartialDate(Date date) {
        ArrayList<Todo> result = new ArrayList<>();
        Calendar inputCal = Calendar.getInstance();
        inputCal.setTime(date);

        for (LinkedList<Todo> todoList : adt.values()) {
            for (Todo todo : todoList) {
                Calendar dueCal = Calendar.getInstance();
                dueCal.setTime(todo.getDueDate()); // Compare with Due Date

                if (dueCal.get(Calendar.YEAR) == inputCal.get(Calendar.YEAR) && dueCal.get(Calendar.MONTH) == inputCal.get(Calendar.MONTH)) {
                    result.add(todo);
                }
            }
        }
        return result;
    }

    public ArrayList<Todo> searchTodoByTitle(String title) {
        ArrayList<Todo> result = new ArrayList<>();

        for (LinkedList<Todo> todoList : adt.values()) {
            for (Todo todo : todoList) {
                if (todo.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    result.add(todo);
                }
            }
        }

        return result;
    }

    public LinkedList<Todo> getTodayTodo() {
        // Set the calendar to today's date
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();  // Get the date with 0 hour, 0 min, 0 sec

        LinkedHashMap<Date, LinkedList<Todo>> sortedTodos = sortTodosByDueDate();

        // Return the list of todos for today if it exists
        if (sortedTodos.containsKey(today)) {
            return sortedTodos.get(today);
        } else {
            return new LinkedList<>();
        }
    }

    public LinkedHashMap<Date, LinkedList<Todo>> sortTodosByDueDate() {
        // Get all Todos from the ADT and flatten into a single list
        List<Todo> allTodos = new ArrayList<>();
        for (LinkedList<Todo> todoList : adt.values()) {
            allTodos.addAll(todoList);
        }

        // Sort Todos by Due Date (newest to oldest)
        List<Todo> sortedTodos = TodoSorter.sortTodosByDueDate(allTodos);

        // Reconstruct the sorted LinkedHashMap
        LinkedHashMap<Date, LinkedList<Todo>> sortedMap = new LinkedHashMap<>();
        for (Todo todo : sortedTodos) {
            sortedMap.computeIfAbsent(todo.getDueDate(), k -> new LinkedList<>()).add(todo);
        }

        return sortedMap;
    }

}
