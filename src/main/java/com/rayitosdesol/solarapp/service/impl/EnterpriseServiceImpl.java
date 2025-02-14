package com.rayitosdesol.solarapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayitosdesol.solarapp.model.dao.EnterpriseDao;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IEnterpriseService;

@Service
public class EnterpriseServiceImpl implements IEnterpriseService {
    
    @Autowired
    private EnterpriseDao enterpriseDao;

    @Override
    public Enterprise createEnterprise(Enterprise enterprise) {
        return enterpriseDao.save(enterprise);
    }

    @Override
    public Optional<Enterprise> getEnterpriseByNit(String nitEnterprise) {
        return enterpriseDao.findByNitEnterprise(nitEnterprise);
    }
}