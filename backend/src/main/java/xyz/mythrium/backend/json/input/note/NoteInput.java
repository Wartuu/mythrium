package xyz.mythrium.backend.json.input.note;

import java.time.LocalDateTime;
import java.util.Date;

public class NoteInput {
    public final String content;
    public final String password;
    public final boolean isPrivate;
    public final boolean burnAfterRead;
    public final Date expirationDate;

    public NoteInput(String content, String password, boolean isPrivate, boolean burnAfterRead, Date expirationDate) {
        this.content = content;
        this.password = password;
        this.isPrivate = isPrivate;
        this.burnAfterRead = burnAfterRead;
        this.expirationDate = expirationDate;
    }
}
