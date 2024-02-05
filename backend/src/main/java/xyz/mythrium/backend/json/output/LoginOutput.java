package xyz.mythrium.backend.json.output;

public class LoginOutput extends ApiOutput {

    public final String token;

    public LoginOutput(boolean success, String information, String token) {
        super(success, information);
        this.token = token;

    }
}
