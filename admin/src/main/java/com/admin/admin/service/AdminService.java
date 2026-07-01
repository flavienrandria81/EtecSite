package com.admin.admin.service;


import com.admin.admin.client.UserFeignClient;
import com.admin.admin.dto.AdminDto;
import com.admin.admin.dto.UserRegistrationDTO;
import com.admin.admin.entity.Admin;
import com.admin.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserFeignClient userFeignClient;

    // READ : Récupérer le profil complet
    public AdminDto getAdmin(Long userId) {
        // 1. Récupérer les infos locales
        Admin admin = adminRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profil Admin local introuvable"));

        // 2. Récupérer les infos d'authentification du User Service via Feign
        UserRegistrationDTO authInfo = userFeignClient.getUserById(userId).getBody();

        // 3. Fusionner les données dans le DTO global
        AdminDto adminDto = new AdminDto();
        adminDto.setUserId(userId);
        if (authInfo != null) {
            adminDto.setUsername(authInfo.getUsername());
            adminDto.setEmail(authInfo.getEmail());
            adminDto.setRole(authInfo.getRole());
        }
        adminDto.setNom(admin.getFirstName());
        adminDto.setPrenom(admin.getLastName());
        adminDto.setDepartement(admin.getPhoneNumber());

        return adminDto;
    }

    // DELETE : Supprimer partout
    public void deleteAdmin(Long userId) {
        // 1. Supprimer dans le User Service via Feign
        userFeignClient.deleteUser(userId);

        // 2. Supprimer les données locales du Admin Service
        adminRepository.findByUserId(userId).ifPresent(adminRepository::delete);
    }
}
