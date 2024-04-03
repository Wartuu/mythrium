package xyz.mythrium.backend.json.output.media;

import xyz.mythrium.backend.entity.media.Note;
import xyz.mythrium.backend.json.output.ApiOutput;

import java.util.Date;

public class NoteContentOutput extends ApiOutput {
    public final String title;
    public final String author;
    public final String content;

    public final Date creation_date;
    public final int views;

    public NoteContentOutput(String title, String author, String content, Date creation_date, int views) {
        super(true, "found note");
        this.title = title;
        this.author = author;
        this.content = content;
        this.creation_date = creation_date;
        this.views = views;
    }

    public NoteContentOutput(Note note, String author) {
        super(true, "found note");
        this.title = note.getTitle();
        this.author = author;
        this.content = note.getContent();
        this.creation_date = note.getCreationDate();
        this.views = note.getViewCount();
    }


}
