package xyz.mythrium.backend.json.output;

public class ApiOutput {

    public final boolean success;
    public final String information;


    public ApiOutput(boolean success, String information) {
        this.success = success;
        this.information = information;
    }
}
