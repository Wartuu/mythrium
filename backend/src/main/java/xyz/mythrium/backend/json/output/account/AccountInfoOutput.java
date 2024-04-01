package xyz.mythrium.backend.json.output.account;


import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.json.output.ApiOutput;

import java.util.Date;

// TODO: add more informations, roles, etc
public class AccountInfoOutput extends ApiOutput {
    public final String username;
    public final String email;
    public final Date creationDate;

    public AccountInfoOutput(boolean success, String information, Account account) {
        super(success, information);

        this.username = account.getUsername();
        this.email = account.getEmail();
        this.creationDate = account.getCreationDate();
    }
}
