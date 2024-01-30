package md.mythrium.backend.json.output;

import md.mythrium.backend.json.output.ApiOutput;

public class NoteReadOutput extends ApiOutput {

    public final String content;
    public final int viewCount;

    public NoteReadOutput(boolean success, String information, String content, int viewCount) {
        super(success, information);
        this.content = content;
        this.viewCount = viewCount;
    }


}
