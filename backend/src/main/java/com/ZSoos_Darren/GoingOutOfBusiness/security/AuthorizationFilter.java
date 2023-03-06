package com.ZSoos_Darren.GoingOutOfBusiness.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

@Component
public class AuthorizationFilter implements Filter {

    private final String SECRET_KEY = SecretKeyGenerator.generateSecretKey(32);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String token = getTokenFromRequest(httpRequest);
        if (token != null && validateToken(token)) {
            String userRole = getUserRoleFromToken(token);

            if (hasAccess(httpRequest.getRequestURI(), userRole)) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getTokenFromRequest(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }

    private boolean hasAccess(String uri, String role) {
        // Check if the user has access to the requested URI based on their role
        if (role.equals("admin")) {
            return true; // Admin users have access to all URLs
        } else if (role.equals("user")) {
            return uri.startsWith("/api/user"); //TODO Regular users have access to URLs starting with "/api/user", add more paths
        }
        return false; // Deny access for any other roles
    }
}
