package com.rayitosdesol.solarapp.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Table(name = "quotations")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "projectCost")
    private double projectCost;

    @Column(name = "systemPower")
    private double systemPower;

    @Column(name = "energyGeneration")
    private double energyGeneration;

    @Column(name = "monthlySavings")
    private double monthlySavings;

    @ManyToOne
    @JoinColumn(name = "idContractor", nullable = false, referencedColumnName = "idContractor")
    private Contractor contractor;
}