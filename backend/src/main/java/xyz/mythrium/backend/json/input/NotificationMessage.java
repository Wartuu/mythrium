package xyz.mythrium.backend.json.input;

public class NotificationMessage {
    public final String type;
    public final String title;
    public final String message;

    public NotificationMessage(String type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }
}
