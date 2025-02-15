package com.rayitosdesol.solarapp.service;

import java.util.Optional;

import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;

public interface IEnterpriseService {
    Enterprise save(EnterpriseDto enterprise);
    Optional<Enterprise> finByNit(String nitEnterprise);
}

