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


    @PostMapping("/register_admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User adminUser) {

        // 1. Vérifier si l'email existe déjà
        if (userRepository.findByEmail(adminUser.getEmail()) != null) {
            return ResponseEntity.badRequest()
                    .body("Email is already in use");
        }

        // 2. Sécuriser les rôles acceptés pour éviter l'injection de rôles fantaisistes
        String role = adminUser.getRole();
        if (!"SUPER_ADMIN".equals(role) && !"ADMIN".equals(role) && !"PEDAGOGIQUE_ADMIN".equals(role)) {
            return ResponseEntity.badRequest()
                    .body("Invalid administrative role provided");
        }

        // 3. Encoder le mot de passe reçu en clair depuis l'Admin Service
        adminUser.setPassword(
                passwordEncoder.encode(adminUser.getPassword())
        );

        // 4. Sauvegarder l'administrateur dans la table 'users'
        User savedAdmin = userRepository.save(adminUser);

        return ResponseEntity.ok(savedAdmin);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest()
                    .body("Email is already in use");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()
                            )
                    );

            if (authentication.isAuthenticated()) {

                // récupérer l'utilisateur complet depuis la base
                User dbUser =
                        userRepository.findByEmail(
                                user.getEmail()
                        );

                Map<String, Object> authData =
                        new HashMap<>();

                authData.put(
                        "token",
                        jwtUtils.generationToken(
                                dbUser.getEmail(),
                                dbUser.getRole(),
                                dbUser.getId()
                        )
                );

                authData.put("type", "Bearer");

                authData.put("email", dbUser.getEmail());
                authData.put("role", dbUser.getRole());
                authData.put("userId", dbUser.getId());

                return ResponseEntity.ok(authData);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Email or Password");

        } catch (AuthenticationException e) {

            log.error("Login Error : {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Email or Password");
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        System.out.println("Recherche utilisateur : " + id);
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}