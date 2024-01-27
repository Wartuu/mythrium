package md.mythrium.backend.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;


@Component
public class RedirectConfig implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        if(uri.toLowerCase(Locale.ROOT).startsWith("/api")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(uri.toLowerCase(Locale.ROOT).startsWith("/assets")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
    }
}
