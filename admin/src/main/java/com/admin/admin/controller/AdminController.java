package com.admin.admin.controller;

import com.admin.admin.dto.AdminDto;
import com.admin.admin.dto.AdminRequestDTO;
import com.admin.admin.entity.Admin;
import com.admin.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;



    /**
     * Création d'un ADMIN
     * Accessible uniquement par SUPER_ADMIN
     */
    @PostMapping("/register")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerAdmin(
            @RequestBody AdminRequestDTO request
    ){

        Admin admin =
                adminService.createAdmin(request);


        return ResponseEntity.ok(admin);

    }





    /**
     * Récupérer le profil complet d'un ADMIN
     */
    @GetMapping("/profile/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<AdminDto> getAdminProfile(
            @PathVariable Long userId
    ){

        AdminDto adminDto =
                adminService.getAdmin(userId);


        return ResponseEntity.ok(adminDto);

    }





    /**
     * Supprimer un ADMIN
     * Seulement SUPER_ADMIN
     */
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteAdmin(
            @PathVariable Long userId
    ){

        adminService.deleteAdmin(userId);


        return ResponseEntity.ok(
                "Administrateur supprimé avec succès"
        );

    }

}