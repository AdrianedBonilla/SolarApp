package com.rayitosdesol.solarapp.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nameContact")
    private String nameContact;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(name = "emailContact")
    private String emailContact;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(name = "messageContact")
    private String messageContact;
}