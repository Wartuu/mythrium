package md.mythrium.backend.json.output;


import com.fasterxml.jackson.annotation.JsonAlias;
import md.mythrium.backend.entity.account.Account;

import java.util.Date;

// TODO: add more informations, roles, etc
public class AccountInfoOutput extends ApiOutput {
    public final String username;
    public final Date creationDate;
    public final String token;

    public AccountInfoOutput(boolean success, String information, Account account) {
        super(success, information);

        this.username = account.getUsername();
        this.creationDate = account.getCreationDate();
        this.token = account.getSessionToken();
    }
}
