package com.rayitosdesol.solarapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    private String emailClient;
    private String passwordClient;
    private String nameClient;
    private String phoneClient;
    private String cityClient;
    private String neighborhoodClient;
    private Integer monthlyConsumptionClient;
    private String installationTypeClient;
    private String subsidyLevel;

    @ManyToOne
    @JoinColumn(name = "contractorId")
    private Contractor contractor;
}