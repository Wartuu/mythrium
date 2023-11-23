package md.mythrium.backend.json;

public class ApiError {

    public final int id;
    public final String description;


    public ApiError(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
