package com.rayitosdesol.solarapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(name = "emailClient")
    private String emailClient;
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "passwordClient")
    private String passwordClient;
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nameClient", length = 255)
    private String nameClient;
    @NotBlank(message = "El telefono es obligatorio")
    @Column(name = "phoneClient", length = 20)
    private String phoneClient;
    @NotBlank(message = "La ciudad es obligatoria")
    @Column(name = "cityClient", length = 100)
    private String cityClient;
    @NotBlank(message = "El barrio es obligatorio")
    @Column(name = "neighborhoodClient", length = 100)
    private String neighborhoodClient;
    @Column(name = "monthlyConsumptionClient")
    private Integer monthlyConsumptionClient;
    @Column(name = "installationTypeClient")
    private String installationTypeClient;
    @Column(name = "subsidyLevel", length = 50)
    private String subsidyLevel;

    @ManyToOne
    @JoinColumn(name = "idContractor", nullable = false, referencedColumnName = "idContractor")
    private Contractor contractor;
}