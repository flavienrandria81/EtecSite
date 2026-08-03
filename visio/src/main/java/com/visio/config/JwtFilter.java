package com.visio.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.equals("/visio")
                || path.startsWith("/visio/")
                || path.startsWith("/visio-ws")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }

        if (jwt != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {

                String email = jwtUtils.extractEmail(jwt);
                Long userId = jwtUtils.extractUserId(jwt);
                String role = jwtUtils.extractRole(jwt);

                if (jwtUtils.validateToken(jwt)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority("ROLE_" + role)
                                    )
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    request.setAttribute("userId", userId);
                    request.setAttribute("email", email);
                    request.setAttribute("role", role);
                }

            } catch (Exception e) {
                logger.warn("Token JWT invalide : " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
