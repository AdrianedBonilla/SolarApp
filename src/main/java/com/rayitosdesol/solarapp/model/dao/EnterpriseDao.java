package com.rayitosdesol.solarapp.model.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rayitosdesol.solarapp.model.entity.Enterprise;

public interface EnterpriseDao extends JpaRepository<Enterprise, Long> {
    Optional<Enterprise> findByNitEnterprise(String nitEnterprise);
}
