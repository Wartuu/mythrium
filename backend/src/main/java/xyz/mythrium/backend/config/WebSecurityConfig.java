package xyz.mythrium.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    private final AccountAuthenticationProvider accountAuthenticationProvider;
//
//
//    @Autowired
//    public WebSecurityConfig(AccountAuthenticationProvider accountAuthenticationProvider) {
//        this.accountAuthenticationProvider = accountAuthenticationProvider;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                requests -> requests
                        .requestMatchers("/", "/login", "/register").permitAll()
                        .requestMatchers("/static/**", "/assets/**", "/api/**").permitAll()
                        .requestMatchers("/note/**").hasRole("USER")
                        .anyRequest().permitAll()

        );


        return http.build();
    }

    @Bean
    protected AuthenticationManager authManager(HttpSecurity security) throws Exception {
        System.out.println("LOADING MANAGER");
        AuthenticationManagerBuilder builder = security.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.authenticationProvider(accountAuthenticationProvider);
        return builder.build();
    }
}
