package obj.adt;

import obj.Todo;
import obj.utils.CResponse;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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

        return new CResponse(true, "✅ Todo added successfully for " + today);
    }
}
