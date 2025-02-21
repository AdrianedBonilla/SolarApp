package com.rayitosdesol.solarapp.service;

import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;

import java.util.Optional;

public interface IEnterpriseService {
    Enterprise save(EnterpriseDto enterpriseDto);
    Optional<Enterprise> findByNit(String nitEnterprise);
}