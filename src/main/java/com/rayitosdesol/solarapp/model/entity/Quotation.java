package com.rayitosdesol.solarapp.model.entity;

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
}