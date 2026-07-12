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



        if(userResponse == null){

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



        if(authInfo != null){

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