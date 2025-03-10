package obj;

import obj.utils.CResponse;

public class Todo {
    private final String uniqueId;
    String title;
    String description;
    boolean done = false;

    public Todo(String uniqueId) {
        this.uniqueId = uniqueId;  // Constructor to set the unique ID
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public CResponse setTitle(String title) {
        if (title == null || title.trim().length() < 2) {
            return new CResponse(false, "Title must be at least 2 characters long!");  // Validation for the title
        }
        this.title = title;
        return new CResponse(true, "Title set successfully!");
    }

    public CResponse setDescription(String description) {
        if (description == null || description.trim().length() < 2) {
            return new CResponse(false, "Description must be at least 2 characters long!");  // Validation for the description
        }
        this.description = description;
        return new CResponse(true, "Description set successfully!");
    }

    public void display() {
        // Display Todo details
        System.out.println("-----------------------------");
        System.out.println("Todo ID: " + uniqueId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Status: " + (done ? "✅ Completed" : "⏳ Pending"));
        System.out.println("-----------------------------");
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
