package com.rayitosdesol.solarapp.model.dao;

import com.rayitosdesol.solarapp.model.entity.Subsidy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsidyDao extends JpaRepository<Subsidy, Long> {
}