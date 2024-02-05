package xyz.mythrium.backend.json.output;

public class NoteReadOutput extends ApiOutput {

    public final String content;
    public final int viewCount;

    public NoteReadOutput(boolean success, String information, String content, int viewCount) {
        super(success, information);
        this.content = content;
        this.viewCount = viewCount;
    }


}
