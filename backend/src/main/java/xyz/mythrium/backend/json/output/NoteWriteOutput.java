package xyz.mythrium.backend.json.output;

public class NoteWriteOutput extends ApiOutput {
    public final String uuid;

    public NoteWriteOutput(boolean success, String information, String uuid) {
        super(success, information);
        this.uuid = uuid;
    }
}
