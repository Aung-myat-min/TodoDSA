package obj.adt;

import obj.Todo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ProgramMainADT {
    LinkedHashMap<Date, LinkedList<Todo>> adt;

    public ProgramMainADT() {
        adt = new LinkedHashMap<>();
    }
}
