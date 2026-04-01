package sdu.project.realworldback.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest)
                .getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Token ")) {
            bearerToken = bearerToken.substring("Token ".length());
            try {
                if (jwtTokenProvider.isValid(bearerToken)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    log.warn("Invalid or expired JWT token");
                }
            } catch (Exception ex) {
                log.warn("JWT validation error: {}", ex.getMessage());
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
