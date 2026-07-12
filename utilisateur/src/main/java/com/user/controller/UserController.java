package com.user.controller;

import com.user.config.JwtUtils;
import com.user.entity.User;
import com.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;


    /**
     * Création du SUPER_ADMIN
     * Un seul SUPER_ADMIN autorisé dans le système
     */
    @PostMapping("/register-super-admin")
    public ResponseEntity<?> registerSuperAdmin(
            @RequestBody User user
    ){

        // Vérifier si un SUPER_ADMIN existe déjà
        if(userRepository.countByRole("SUPER_ADMIN") > 0){

            return ResponseEntity
                    .badRequest()
                    .body("Un SUPER_ADMIN existe déjà");

        }


        // Vérifier email
        if(userRepository.findByEmail(user.getEmail()) != null){

            return ResponseEntity
                    .badRequest()
                    .body("Email déjà utilisé");

        }


        // Forcer le rôle
        user.setRole("SUPER_ADMIN");


        // Encoder password
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );


        User savedUser =
                userRepository.save(user);


        return ResponseEntity.ok(savedUser);
    }



    /**
     * Création d'un ADMIN
     * Seulement SUPER_ADMIN peut créer un ADMIN
     */
    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerAdmin(
            @RequestBody User adminUser
    ){

        if(userRepository.findByEmail(adminUser.getEmail()) != null){

            return ResponseEntity
                    .badRequest()
                    .body("Email déjà utilisé");

        }


        // Le rôle est imposé
        adminUser.setRole("ADMIN");


        adminUser.setPassword(
                passwordEncoder.encode(
                        adminUser.getPassword()
                )
        );


        User savedAdmin =
                userRepository.save(adminUser);


        return ResponseEntity.ok(savedAdmin);
    }



    /**
     * Inscription normale
     * Exemple : étudiant
     */
    @PostMapping("/registration")
    public ResponseEntity<?> register(
            @RequestBody User user
    ){

        if(userRepository.findByEmail(user.getEmail()) != null){

            return ResponseEntity
                    .badRequest()
                    .body("Email déjà utilisé");

        }


        // Rôle par défaut
        user.setRole("ETUDIANT");


        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );


        User savedUser =
                userRepository.save(user);


        return ResponseEntity.ok(savedUser);
    }



    /**
     * Connexion JWT
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User user
    ){

        try {


            Authentication authentication =
                    authenticationManager.authenticate(

                            new UsernamePasswordAuthenticationToken(

                                    user.getEmail(),

                                    user.getPassword()
                            )
                    );



            if(authentication.isAuthenticated()){


                User dbUser =
                        userRepository.findByEmail(
                                user.getEmail()
                        );


                Map<String,Object> authData =
                        new HashMap<>();


                String token =
                        jwtUtils.generationToken(

                                dbUser.getEmail(),

                                dbUser.getRole(),

                                dbUser.getId()

                        );


                authData.put(
                        "token",
                        token
                );


                authData.put(
                        "type",
                        "Bearer"
                );


                authData.put(
                        "email",
                        dbUser.getEmail()
                );


                authData.put(
                        "role",
                        dbUser.getRole()
                );


                authData.put(
                        "userId",
                        dbUser.getId()
                );


                return ResponseEntity.ok(authData);

            }


            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect");



        }catch(AuthenticationException e){


            log.error(
                    "Erreur login : {}",
                    e.getMessage()
            );


            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect");

        }

    }



    /**
     * Récupérer un utilisateur
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(
            @PathVariable Long id
    ){

        return userRepository.findById(id)

                .map(ResponseEntity::ok)

                .orElse(
                        ResponseEntity.notFound().build()
                );
    }



    /**
     * Supprimer utilisateur
     * Seulement SUPER_ADMIN
     * Impossible de supprimer SUPER_ADMIN
     */
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id
    ){


        User user =
                userRepository.findById(id)
                        .orElse(null);



        if(user == null){

            return ResponseEntity
                    .notFound()
                    .build();

        }



        if("SUPER_ADMIN".equals(user.getRole())){


            return ResponseEntity
                    .badRequest()
                    .body(
                            "Impossible de supprimer le SUPER_ADMIN"
                    );

        }



        userRepository.delete(user);



        return ResponseEntity.ok(
                "Utilisateur supprimé"
        );

    }

}