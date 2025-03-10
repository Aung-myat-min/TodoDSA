package obj.utils;

public class CResponse {
    public boolean status;  // Status of the response (success or failure)
    public String message;  // Message associated with the response

    public CResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
