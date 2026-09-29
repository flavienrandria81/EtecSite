package com.admin.admin.controller;

import com.admin.admin.dto.AdminDto;
import com.admin.admin.dto.AdminRequestDTO;
import com.admin.admin.dto.UserResponseDTO;
import com.admin.admin.entity.Admin;
import com.admin.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;


    /**
     * ============================================================
     * LOGIN ADMIN
     * ============================================================
     *
     * POST /api/admin/login
     *
     * Cette route est publique.
     *
     * Exemple de requête :
     *
     * {
     *     "username": "admin",
     *     "password": "123456"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AdminRequestDTO request
    ) {

        try {

            UserResponseDTO response =
                    adminService.login(request);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(401)
                    .body(e.getMessage());
        }
    }


    /**
     * ============================================================
     * CRÉER UN ADMIN
     * ============================================================
     *
     * POST /api/admin
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createAdmin(
            @RequestBody AdminRequestDTO request
    ) {

        try {

            Admin admin =
                    adminService.createAdmin(request);

            return ResponseEntity.ok(admin);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    /**
     * ============================================================
     * RÉCUPÉRER UN ADMIN
     * ============================================================
     *
     * GET /api/admin/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAdmin(
            @PathVariable Long userId
    ) {

        try {

            AdminDto admin =
                    adminService.getAdmin(userId);

            return ResponseEntity.ok(admin);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    /**
     * ============================================================
     * SUPPRIMER UN ADMIN
     * ============================================================
     *
     * DELETE /api/admin/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAdmin(
            @PathVariable Long userId
    ) {

        try {

            adminService.deleteAdmin(userId);

            return ResponseEntity.ok(
                    "Administrateur supprimé avec succès"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}

