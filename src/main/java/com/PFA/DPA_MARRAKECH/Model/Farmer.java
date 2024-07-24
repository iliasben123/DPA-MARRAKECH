package com.PFA.DPA_MARRAKECH.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "farmers")
public class Farmer {
    // getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String cin;
    private String name;
    private String address;
    private String phoneNumber;
    private String transportationAssured;
    private String dateDeDepot;
    private String dateDeffet;
    private String typeDeDossier;
    private String commune;

}
