package com.rayitosdesol.solarapp.model.entity;

import java.io.Serializable;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Schema(hidden = true)
@Table(name = "enterprises")
public class Enterprise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnterprise;

    @NotBlank(message = "El NIT de la empresa es obligatorio")
    @Column(name = "nitEnterprise", unique = true)
    private String nitEnterprise;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Column(name = "nameEnterprise")
    private String nameEnterprise;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contractor> contractors;
}
