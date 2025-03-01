package com.rayitosdesol.solarapp.model.entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Schema(hidden = true)
@Table(name = "contractors")
public class Contractor implements Serializable {

    @Id
    @Column(name = "idContractor") 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContractor;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nameContractor")
    private String nameContractor;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(name = "emailContractor")
    private String emailContractor;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "passwordContractor")
    private String passwordContractor;

    @NotBlank(message = "El teléfono es obligatorio")
    @Column(name = "phoneContractor", length = 50)
    private String phoneContractor;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(name = "locationContractor")
    private String locationContractor;

    @NotBlank(message = "La experiencia es obligatoria")
    @Column(name = "expertiseContractor")
    private String expertiseContractor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nitEnterprise", nullable = false, referencedColumnName = "nitEnterprise")
    @JsonBackReference
    private Enterprise enterprise;
}