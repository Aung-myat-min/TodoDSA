package obj.sorting;

import obj.Todo;
import java.util.List;
import java.util.ArrayList;

public class TodoSorter {

    public static List<Todo> mergeSort(List<Todo> todos) {
        if (todos.size() <= 1) {
            return todos;
        }

        int mid = todos.size() / 2;
        List<Todo> left = new ArrayList<>(todos.subList(0, mid));
        List<Todo> right = new ArrayList<>(todos.subList(mid, todos.size()));

        return merge(mergeSort(left), mergeSort(right));
    }

    private static List<Todo> merge(List<Todo> left, List<Todo> right) {
        List<Todo> sorted = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            Todo leftTodo = left.get(i);
            Todo rightTodo = right.get(j);

            // 1. Sort by completion status (Uncompleted first)
            if (!leftTodo.isDone() && rightTodo.isDone()) {
                sorted.add(leftTodo);
                i++;
            } else if (leftTodo.isDone() && !rightTodo.isDone()) {
                sorted.add(rightTodo);
                j++;
            }
            // 2. If both have the same completion status, sort by title
            else if (leftTodo.getTitle().compareToIgnoreCase(rightTodo.getTitle()) <= 0) {
                sorted.add(leftTodo);
                i++;
            } else {
                sorted.add(rightTodo);
                j++;
            }
        }

        // Add remaining elements
        while (i < left.size()) {
            sorted.add(left.get(i++));
        }
        while (j < right.size()) {
            sorted.add(right.get(j++));
        }

        return sorted;
    }
}
