package xyz.mythrium.backend.json.output.account;

import xyz.mythrium.backend.json.output.ApiOutput;

public class LoginOutput extends ApiOutput {

    public final String token;

    public LoginOutput(boolean success, String information, String token) {
        super(success, information);
        this.token = token;

    }
}
