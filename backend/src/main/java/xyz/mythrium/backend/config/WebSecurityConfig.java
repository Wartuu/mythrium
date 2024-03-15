package xyz.mythrium.backend.config;


import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.mythrium.backend.component.JwtAuthenticationFilter;
import xyz.mythrium.backend.service.security.OAuthService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final OAuthService oAuthService;

    @Autowired
    public WebSecurityConfig(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> {c.disable();}).addFilterBefore(new JwtAuthenticationFilter(oAuthService), UsernamePasswordAuthenticationFilter.class).authorizeHttpRequests(
                requests -> requests
                        .requestMatchers("/", "/login", "/register", "/static/**", "/assets/**", "/api/v1/**").permitAll()
                        .requestMatchers("/note/**", "/dashboard/**", "/account/**").hasAuthority("user")
                        .requestMatchers("/admin/**").hasAnyAuthority("admin", "moderator")
                        .anyRequest().permitAll()
        ).exceptionHandling(handler ->
                handler
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                        .accessDeniedHandler(accessDeniedHandler())
                );


        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request, response, accessDeniedException) -> {
           response.sendRedirect("/login?redirect=" + request.getRequestURI());
        });
    }

}
