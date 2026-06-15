package com.example.Etudiant.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String grade;
    private String user_id;
   /* private String id_emploi_du_temps;
    private String encadreur_id;*/
    private String parcours;
    private String mentiont;
    private String matricule;
    private String phone;
    private String photo;
    private String cin;
    private String releve;

}