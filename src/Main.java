import obj.adt.ProgramMainADT;
import obj.method.TodoMethods;

public class Main {
    public static void main(String[] args) {
        ProgramMainADT mainADT = new ProgramMainADT();
        TodoMethods todoMethods = new TodoMethods(mainADT);

        todoMethods.addTodo();
    }
}