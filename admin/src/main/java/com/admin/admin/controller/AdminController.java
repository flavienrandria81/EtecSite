package com.admin.admin.controller;

import com.admin.admin.client.UserFeignClient;
import com.admin.admin.dto.AdminDto;
import com.admin.admin.dto.UserRegistrationDTO;
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
    private final UserFeignClient userFeignClient;

    /**
     * CREATE : Inscrire un nouvel administrateur (SUPER_ADMIN, ADMIN, PEDAGOGIQUE_ADMIN)
     * Cette méthode appelle le User Service via OpenFeign pour enregistrer les identifiants.
     * Accessible uniquement par un SUPER_ADMIN ou un ADMIN.
     */
    @PostMapping("/register-staff")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<?> registerNewAdmin(@RequestBody UserRegistrationDTO registrationDTO) {
        // Envoi de la requête au User Service (le JwtInterceptor propage le token automatiquement)
        ResponseEntity<?> response = userFeignClient.registerAdmin(registrationDTO);

        /* * Note optionnelle : Si vous souhaitez créer automatiquement une ligne vide
         * dans votre table 'admin_profiles' locale dès l'inscription, vous pouvez récupérer
         * l'ID utilisateur retourné par la réponse et appeler une méthode de votre AdminService ici.
         */

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    /**
     * READ : Récupérer le profil complet et fusionné d'un administrateur
     * Combine les informations de la table locale (Admin) et du User Service (UserRegistrationDTO).
     * Accessible par SUPER_ADMIN, ADMIN ou PEDAGOGIQUE_ADMIN.
     */
    @GetMapping("/profile/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'PEDAGOGIQUE_ADMIN')")
    public ResponseEntity<AdminDto> getAdminProfile(@PathVariable Long userId) {
        AdminDto adminDto = adminService.getAdmin(userId);
        return ResponseEntity.ok(adminDto);
    }

    /**
     * DELETE : Supprimer un administrateur globalement
     * Supprime d'abord les identifiants dans le User Service via Feign, puis nettoie la table locale.
     * Action hautement sécurisée, réservée au SUPER_ADMIN.
     */
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> deleteAdminAccount(@PathVariable Long userId) {
        adminService.deleteAdmin(userId);
        return ResponseEntity.ok("L'administrateur a été supprimé avec succès de tous les microservices.");
    }
}