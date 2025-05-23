package obj;

import obj.utils.CResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {
    private final String uniqueId;
    private String title;
    private String description;
    private TodoStatus status = TodoStatus.Pending;
    private Date dueDate;

    public Todo(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public TodoStatus getStatus() {
        return this.status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public CResponse setTitle(String title) {
        if (title == null || title.trim().length() < 2) {
            return new CResponse(false, "Title must be at least 2 characters long!");
        }
        this.title = title;
        return new CResponse(true, "Title set successfully!");
    }

    public CResponse setDescription(String description) {
        if (description == null || description.trim().length() < 2) {
            return new CResponse(false, "Description must be at least 2 characters long!");
        }
        this.description = description;
        return new CResponse(true, "Description set successfully!");
    }

    public CResponse setDueDate(String dueDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            Date parsedDate = sdf.parse(dueDateStr);

            if (parsedDate.before(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000))) {
                return new CResponse(false, "Due Date cannot be in the past!");
            }

            this.dueDate = parsedDate;
            return new CResponse(true, "Due Date set successfully!");
        } catch (ParseException e) {
            return new CResponse(false, "Invalid date format! Use DD-MM-YYYY.");
        }
    }

    public boolean isOverdue() {
        if (dueDate == null) return false;
        return new Date().after(dueDate) && !isToday(dueDate);
    }

    private boolean isToday(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date).equals(sdf.format(new Date()));
    }

    public void display() {
        System.out.println("-----------------------------");
        System.out.println("Todo ID: " + uniqueId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Status: " + status);
        System.out.println("Due Date: " + getDueDate());
        if (isOverdue()) {
            System.out.println("⚠️ Overdue!");
        }
        System.out.println("-----------------------------");
    }
}
