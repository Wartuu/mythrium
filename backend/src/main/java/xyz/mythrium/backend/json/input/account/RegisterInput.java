package xyz.mythrium.backend.json.input.account;

public class RegisterInput {

    public final String email;
    public final String username;
    public final String password;

    public RegisterInput(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
