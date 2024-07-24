package com.PFA.DPA_MARRAKECH.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cin;
    private String maladies;
    private String justificatif;
    private String dateDeTraitement;
    private String typeDeDemande;
    private String commune;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    public String getMaladies() { return maladies; }
    public void setMaladies(String maladies) { this.maladies = maladies; }
    public String getJustificatif() { return justificatif; }
    public void setJustificatif(String justificatif) { this.justificatif = justificatif; }
    public String getDateDeTraitement() { return dateDeTraitement; }
    public void setDateDeTraitement(String dateDeTraitement) { this.dateDeTraitement = dateDeTraitement; }
    public String getTypeDeDemande() { return typeDeDemande; }
    public void setTypeDeDemande(String typeDeDemande) { this.typeDeDemande = typeDeDemande; }
    public String getCommune() { return commune; }
    public void setCommune(String commune) { this.commune = commune; }
}
