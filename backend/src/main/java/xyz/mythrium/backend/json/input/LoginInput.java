package xyz.mythrium.backend.json.input;

public class LoginInput {
    public final String username;
    public final String password;

    public LoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
