package md.mythrium.backend.json.output;

public class NoteReadOutput extends ApiOutput {

    public final String note;

    public NoteReadOutput(boolean success, String information, String note) {
        super(success, information);
        this.note = note;
    }


}
