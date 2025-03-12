package obj.sorting;

import obj.Todo;
import obj.TodoStatus;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class TodoSorter {

    // Sort Todos by due date and status using merge sort
    public static LinkedHashMap<Date, LinkedList<Todo>> sortTodosByDueDateAndStatus(LinkedHashMap<Date, LinkedList<Todo>> todosMap) {
        // Create a new LinkedHashMap to maintain the order of insertion
        LinkedHashMap<Date, LinkedList<Todo>> sortedMap = new LinkedHashMap<>();

        // Sort each list by status within the same due date using merge sort
        for (Map.Entry<Date, LinkedList<Todo>> entry : todosMap.entrySet()) {
            List<Todo> sortedList = mergeSort(entry.getValue(), Comparator.comparing(Todo::getStatus, new TodoStatusComparator()).thenComparing(Todo::getTitle));
            sortedMap.put(entry.getKey(), new LinkedList<>(sortedList));
        }

        return sortedMap;
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

    // Custom comparator for TodoStatus
    private static class TodoStatusComparator implements Comparator<TodoStatus> {
        @Override
        public int compare(TodoStatus status1, TodoStatus status2) {
            return Integer.compare(getStatusOrder(status1), getStatusOrder(status2));
        }

        private int getStatusOrder(TodoStatus status) {
            switch (status) {
                case Doing:
                    return 1;
                case Pending:
                    return 2;
                case Done:
                    return 3;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }
    }
}