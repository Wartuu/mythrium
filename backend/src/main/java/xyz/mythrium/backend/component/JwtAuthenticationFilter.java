package xyz.mythrium.backend.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.mythrium.backend.entity.account.Account;
import xyz.mythrium.backend.entity.account.Role;
import xyz.mythrium.backend.service.security.OAuthService;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private OAuthService oAuthService;


    public JwtAuthenticationFilter(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = extractJwtFromRequest(request);
        UsernamePasswordAuthenticationToken defaultAuthorization =
                new UsernamePasswordAuthenticationToken(null, null, Set.of());

        if(jwt.isEmpty()) {
            SecurityContextHolder.getContext().setAuthentication(defaultAuthorization);
            filterChain.doFilter(request, response);
            return;
        }

        Account account = oAuthService.authenticateJWT(jwt.get());

        if(account == null) {
            SecurityContextHolder.getContext().setAuthentication(defaultAuthorization);
            filterChain.doFilter(request, response);
            return;
        }


        Set<GrantedAuthority> authoritySet = new HashSet<>();

        for (Role role : account.getRoles()) {
            authoritySet.add(new AccountAuthority(role.getName()));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(account.getUsername(), jwt.get(), authoritySet);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    private Optional<String> extractJwtFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null)
            return Optional.empty();

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("session"))
                return Optional.of(cookie.getValue());
        }


        return Optional.empty();
    }
}
