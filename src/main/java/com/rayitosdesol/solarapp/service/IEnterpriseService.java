package com.rayitosdesol.solarapp.service;

import java.util.Optional;
import com.rayitosdesol.solarapp.model.entity.Enterprise;

public interface IEnterpriseService {
    Enterprise createEnterprise(Enterprise enterprise);
    Optional<Enterprise> getEnterpriseByNit(String nitEnterprise);
}

