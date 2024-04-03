package xyz.mythrium.backend.json.input.media;

public class NoteUploadInput {
    public final String title;
    public final String content;
    public final boolean isPrivate;

    public NoteUploadInput(String title, String content, boolean isPrivate) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
    }
}
