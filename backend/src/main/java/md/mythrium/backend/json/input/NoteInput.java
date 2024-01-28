package md.mythrium.backend.json.input;

import java.time.LocalDateTime;

public class NoteInput {
    public final String data;
    public final String password;
    public final boolean isPrivate;
    public final boolean burnAfterRead;
    public final LocalDateTime expirationDate;

    public NoteInput(String data, String password, boolean isPrivate, boolean burnAfterRead, LocalDateTime expirationDate) {
        this.data = data;
        this.password = password;
        this.isPrivate = isPrivate;
        this.burnAfterRead = burnAfterRead;
        this.expirationDate = expirationDate;
    }
}
