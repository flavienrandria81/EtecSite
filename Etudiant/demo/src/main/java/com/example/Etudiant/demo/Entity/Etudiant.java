package com.example.Etudiant.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String grade;
    private String id_emploi_du_temps;
    private String encadreur_id;
    private String parcours;
    private String mentiont;
    private String matricule;
    private String phone;
    private String photo;
    private String cin;
    private String certificat_certifier;


    public Etudiant() {
    }

    public Etudiant(Long id, String nom, String prenom, String email, String phone,
                    String matricule, String id_emploi_du_temps, String certificat_certifier,
                    String cin, String mentiont, String parcours, String password,
                    String grade, String encadreur_id) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.certificat_certifier = certificat_certifier;
        this.cin = cin;
        this.encadreur_id = encadreur_id;
        this.grade = grade;
        this.id_emploi_du_temps = id_emploi_du_temps;
        this.matricule = matricule;
        this.mentiont = mentiont;
        this.parcours = parcours;
        this.password = password;
        this.phone = phone;
        this.photo = photo;

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public String getId_emploi_du_temps() { return id_emploi_du_temps; }
    public void setId_emploi_du_temps(String id_emploi_du_temps) { this.id_emploi_du_temps = id_emploi_du_temps;}
    public String getMatricule() {return matricule;}

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getEncadreur_id() {
        return encadreur_id;
    }

    public void setEncadreur_id(String encadreur_id) {
        this.encadreur_id = encadreur_id;
    }

    public String getCertificat_certifier() {
        return certificat_certifier;
    }

    public void setCertificat_certifier(String certificat_certifier) {
        this.certificat_certifier = certificat_certifier;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getMentiont() {
        return mentiont;
    }

    public void setMentiont(String mentiont) {
        this.mentiont = mentiont;
    }

    public String getParcours() {
        return parcours;
    }

    public void setParcours(String parcours) {
        this.parcours = parcours;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}