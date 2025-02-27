package com.rayitosdesol.solarapp.model.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rayitosdesol.solarapp.model.entity.Contractor;

public interface ContractorDao extends JpaRepository<Contractor, Long> {
    Optional<Contractor> findByEmailContractorAndEnterprise_NitEnterprise(String email, String nitEnterprise);
    Optional<Contractor> findByEmailContractor(String emailClient);
}