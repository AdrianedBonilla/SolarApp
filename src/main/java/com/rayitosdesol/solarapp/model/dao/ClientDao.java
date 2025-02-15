package com.rayitosdesol.solarapp.model.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rayitosdesol.solarapp.model.entity.Client;

public interface ClientDao extends JpaRepository<Client, Long> {
    Optional<Client> findByEmailClient(String emailClient);
}
