package md.mythrium.backend.json.output;

public class NoteOutput extends ApiOutput {

    public final String note;

    public NoteOutput(boolean success, String information, String note) {
        super(success, information);
        this.note = note;
    }


}
