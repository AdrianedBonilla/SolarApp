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
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nameContact")
    private String nameContact;

    @Column(name = "emailContact")
    private String emailContact;

    @Column(name = "messageContact")
    private String messageContact;
}