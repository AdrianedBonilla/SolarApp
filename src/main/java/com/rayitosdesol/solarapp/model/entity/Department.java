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
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "solarHoursPerDay")
    private double solarHoursPerDay;

    @Column(name = "kWhValue")
    private double kWhValue; 
}