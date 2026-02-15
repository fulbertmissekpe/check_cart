package com.example.checkcard.utils;


import com.example.checkcard.data.entities.User;
import com.example.checkcard.services.LoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtControl extends OncePerRequestFilter {

    private final JwtTools jwtTools;
    private final LoginService loginService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        System.out.println("--- Request path: " + requestPath);

        // Ignorer le filtre JWT pour les endpoints publics
        if ("/api/v1/auth/login".equals(requestPath) || "/api/v1/auth/me".equals(requestPath) || "/api/v1/auth/logout".equals(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        String email = jwtTools.extractEmail(token);
                        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            UserDetails userDetails = loginService.loadUserByUsername(email);
                            User user = (User) userDetails;
                            if (jwtTools.validateToken(token, user)) {
                                Collection<? extends GrantedAuthority> authorities = jwtTools.extractAuthorities(token);
                                UsernamePasswordAuthenticationToken authToken =
                                        new UsernamePasswordAuthenticationToken(user, null, authorities);
                                System.out.println("Token is valid");
                                System.out.println("role" + authToken.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du traitement du token JWT: " + e.getMessage());
                    }
                    break;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}