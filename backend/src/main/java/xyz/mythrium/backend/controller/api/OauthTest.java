package xyz.mythrium.backend.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.service.security.OAuthService;

@RestController
@RequestMapping("/api/v1/oauth")
public class OauthTest {

    OAuthService oAuthService;


    @Autowired
    public OauthTest(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/verify")
    public String verify(@RequestBody String jwt) {
        Account account = oAuthService.authenticateJWT(jwt);

        if(account == null) {
            return "invalid jwt session!";
        }

        return account.toString();
    }


}

