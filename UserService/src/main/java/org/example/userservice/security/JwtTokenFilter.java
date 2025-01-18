package org.example.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.userservice.error.AppError;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.example.userservice.exception.*;
import java.io.IOException;
import java.time.LocalDate;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);

        }

        try {
            if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            exceptionHandler((HttpServletResponse) servletResponse, ex);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void exceptionHandler(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        AppError appError;
        if (ex instanceof ExpiredJwtException) {
            appError = new AppError(401, "Expired JWT", LocalDate.now().atStartOfDay());

        } else {
            appError = new AppError(401, "Invalid JWT", LocalDate.now().atStartOfDay());
        }
        response.getWriter().write(objectMapper.writeValueAsString(appError));
    }
}



