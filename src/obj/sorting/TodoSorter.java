package obj.sorting;

import obj.Todo;

import java.util.*;

public class TodoSorter {

    // Sort Todos by Due Date (newest to oldest) using Merge Sort
    public static List<Todo> sortTodosByDueDate(List<Todo> todos) {
        return mergeSort(todos, Comparator.comparing(Todo::getDueDate, Comparator.reverseOrder()));
    }

    // Merge sort implementation
    private static List<Todo> mergeSort(List<Todo> list, Comparator<Todo> comparator) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Todo> left = new ArrayList<>(list.subList(0, mid));
        List<Todo> right = new ArrayList<>(list.subList(mid, list.size()));

        return merge(mergeSort(left, comparator), mergeSort(right, comparator), comparator);
    }

    // Merge function for merge sort
    private static List<Todo> merge(List<Todo> left, List<Todo> right, Comparator<Todo> comparator) {
        List<Todo> merged = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0) {
                merged.add(left.get(leftIndex++));
            } else {
                merged.add(right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex++));
        }

        return merged;
    }
}
