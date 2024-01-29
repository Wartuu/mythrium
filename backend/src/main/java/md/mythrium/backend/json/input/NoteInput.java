package md.mythrium.backend.json.input;

import java.time.LocalDateTime;
import java.util.Date;

public class NoteInput {
    public final String data;
    public final String password;
    public final boolean isPrivate;
    public final boolean burnAfterRead;
    public final Date expirationDate;

    public NoteInput(String data, String password, boolean isPrivate, boolean burnAfterRead, Date expirationDate) {
        this.data = data;
        this.password = password;
        this.isPrivate = isPrivate;
        this.burnAfterRead = burnAfterRead;
        this.expirationDate = expirationDate;
    }
}
