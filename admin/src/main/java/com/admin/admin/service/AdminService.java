package com.admin.admin.service;

import com.admin.admin.client.UserFeignClient;
import com.admin.admin.dto.AdminDto;
import com.admin.admin.dto.AdminRequestDTO;
import com.admin.admin.dto.UserRegistrationDTO;
import com.admin.admin.dto.UserResponseDTO;
import com.admin.admin.entity.Admin;
import com.admin.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    private final UserFeignClient userFeignClient;


    /**
     * ============================================================
     * LOGIN ADMIN
     * ============================================================
     *
     * Le username et le password sont envoyés depuis React.
     *
     * Exemple :
     *
     * {
     *     "username": "admin",
     *     "password": "123456"
     * }
     *
     * La vérification réelle du compte doit être effectuée
     * par le User Service puisque le mot de passe est géré
     * dans le User Service.
     */
    public UserResponseDTO login(AdminRequestDTO request) {

        if (request == null) {
            throw new RuntimeException("Les données de connexion sont obligatoires");
        }

        if (request.getUsername() == null ||
                request.getUsername().trim().isEmpty()) {

            throw new RuntimeException("Le username est obligatoire");
        }

        if (request.getPassword() == null ||
                request.getPassword().trim().isEmpty()) {

            throw new RuntimeException("Le mot de passe est obligatoire");
        }


        /*
         * Ici, le User Service doit vérifier :
         *
         * username
         * password
         *
         * et retourner les informations de l'utilisateur
         * ainsi que le JWT si ton User Service le génère.
         *
         * Cette partie dépend de la méthode exposée
         * actuellement par ton UserFeignClient.
         */


        // À remplacer par ton appel réel au User Service
        throw new RuntimeException(
                "La méthode de connexion du User Service doit être configurée dans UserFeignClient"
        );
    }


    /**
     * Créer un nouvel ADMIN
     * Le compte est créé dans User Service
     * Le profil est créé dans Admin Service
     */
    public Admin createAdmin(AdminRequestDTO request) {

        // 1 - Création du compte utilisateur
        UserRegistrationDTO user =
                new UserRegistrationDTO();

        user.setUsername(request.getUsername());

        user.setEmail(request.getEmail());

        user.setPassword(request.getPassword());


        UserResponseDTO userResponse =
                userFeignClient
                        .registerAdmin(user)
                        .getBody();


        if (userResponse == null) {

            throw new RuntimeException(
                    "Erreur lors de la création du compte utilisateur"
            );
        }


        // 2 - Création du profil Admin local

        Admin admin = new Admin();

        admin.setUserId(
                userResponse.getId()
        );


        admin.setFirstName(
                request.getFirstName()
        );


        admin.setLastName(
                request.getLastName()
        );


        admin.setPhoneNumber(
                request.getPhoneNumber()
        );


        return adminRepository.save(admin);
    }


    /**
     * Récupérer le profil complet Admin
     */
    public AdminDto getAdmin(Long userId) {

        Admin admin =
                adminRepository.findByUserId(userId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Profil Admin local introuvable"
                                )
                        );


        UserResponseDTO authInfo =
                userFeignClient
                        .getUserById(userId)
                        .getBody();


        AdminDto adminDto =
                new AdminDto();


        adminDto.setUserId(userId);


        if (authInfo != null) {

            adminDto.setUsername(
                    authInfo.getUsername()
            );

            adminDto.setEmail(
                    authInfo.getEmail()
            );

            adminDto.setRole(
                    authInfo.getRole()
            );
        }


        adminDto.setNom(
                admin.getFirstName()
        );


        adminDto.setPrenom(
                admin.getLastName()
        );


        adminDto.setDepartement(
                admin.getPhoneNumber()
        );


        return adminDto;
    }


    /**
     * Supprimer un Admin
     * Supprime User Service + profil local
     */
    public void deleteAdmin(Long userId) {

        // suppression dans User Service
        userFeignClient.deleteUser(userId);


        // suppression profil Admin local
        adminRepository.findByUserId(userId)

                .ifPresent(
                        adminRepository::delete
                );
    }
}