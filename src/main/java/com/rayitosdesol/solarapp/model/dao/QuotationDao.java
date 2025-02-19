package com.rayitosdesol.solarapp.model.dao;

import com.rayitosdesol.solarapp.model.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationDao extends JpaRepository<Quotation, Long> {
}