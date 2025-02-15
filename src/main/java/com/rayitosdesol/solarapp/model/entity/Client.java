package com.rayitosdesol.solarapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @Column(name = "idClient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    
    @NotBlank(message = "El email es obligatorio")
    @Column(name = "emailClient", nullable = false, unique = true)
    private String emailClient;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = 	"passwordClient", nullable = false)
    private String passwordClient;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nameClient", nullable = false)
    private String nameClient;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "phoneClient", nullable = false)
    private String phoneClient;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Column(name = "cityClient", nullable = false)
    private String cityClient;
    
    @NotBlank(message = "El barrio es obligatorio")
    @Column(name = "neighborhoodClient", nullable = false)
    private String neighborhoodClient;
    
    @NotNull(message = "El consumo mensual es obligatorio")
    @Column(name = "monthlyConsumptionClient", nullable = false)
    private Double monthlyConsumptionClient;
    
    @NotBlank(message = "El tipo de instalación es obligatorio")
    @Column(name = "installationTypeClient", nullable = false)
    private String installationTypeClient;
    
    @NotBlank(message = "Las condiciones del sitio son obligatorias")
    @Column(name = "siteConditionsClient", nullable = false)
    private String siteConditionsClient;
}