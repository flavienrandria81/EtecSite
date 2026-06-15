package com.example.Etudiant.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        String jwt = null;

        // 🔵 1. récupérer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }

        // 🔵 2. validation + extraction
        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                String email = jwtUtils.extractEmail(jwt);
                Long userId = jwtUtils.extractUserId(jwt);
                String role = jwtUtils.extractRole(jwt);

                // 🔵 validation simple
                if (jwtUtils.validateToken(jwt)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    null
                            );

                    // 🔵 stocker infos pour Controller/Service
                    request.setAttribute("userId", userId);
                    request.setAttribute("role", role);
                    request.setAttribute("email", email);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}