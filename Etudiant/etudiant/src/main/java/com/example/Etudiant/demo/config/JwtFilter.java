package com.example.Etudiant.demo.config;

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
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {



        String path = request.getServletPath();



        /*
         * ===============================
         * ROUTES PUBLIQUES
         * ===============================
         */

        if(path.equals("/etudiants/registration")
                || path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html")) {


            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }





        /*
         * ===============================
         * RECUPERATION JWT
         * ===============================
         */


        String authHeader =
                request.getHeader("Authorization");


        String jwt = null;



        if(authHeader != null
                && authHeader.startsWith("Bearer ")) {


            jwt =
                    authHeader.substring(7);

        }




        /*
         * ===============================
         * VALIDATION JWT
         * ===============================
         */


        if(jwt != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {



            try {



                String email =
                        jwtUtils.extractEmail(jwt);



                Long userId =
                        jwtUtils.extractUserId(jwt);



                String role =
                        jwtUtils.extractRole(jwt);




                if(jwtUtils.validateToken(jwt)) {



                    UsernamePasswordAuthenticationToken authentication =


                            new UsernamePasswordAuthenticationToken(

                                    email,

                                    null,

                                    Collections.singletonList(

                                            new SimpleGrantedAuthority(
                                                    "ROLE_" + role
                                            )

                                    )
                            );




                    /*
                     * Stocker les informations JWT
                     */

                    request.setAttribute(
                            "userId",
                            userId
                    );


                    request.setAttribute(
                            "email",
                            email
                    );


                    request.setAttribute(
                            "role",
                            role
                    );



                    System.out.println("====================");
                    System.out.println("JWT VALIDE");
                    System.out.println("Email : " + email);
                    System.out.println("Role : " + role);
                    System.out.println("UserId : " + userId);
                    System.out.println("====================");



                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );

                }



            } catch(Exception e) {


                System.out.println(
                        "Erreur JWT : "
                                + e.getMessage()
                );

            }

        }




        /*
         * continuer la requête
         */

        filterChain.doFilter(
                request,
                response
        );

    }

}