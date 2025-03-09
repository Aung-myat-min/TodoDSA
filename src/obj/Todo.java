package obj;

import obj.utils.CResponse;

public class Todo {
    private final String uniqueId;
    String title;
    String description;
    boolean done = false;

    public Todo(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
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

    public void setDone(boolean done) {
        this.done = done;
    }
}
