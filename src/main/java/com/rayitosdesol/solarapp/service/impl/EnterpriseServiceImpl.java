package com.rayitosdesol.solarapp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rayitosdesol.solarapp.model.dao.EnterpriseDao;
import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IEnterpriseService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnterpriseServiceImpl implements IEnterpriseService {
    
    private final EnterpriseDao enterpriseDao;

    public EnterpriseServiceImpl(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }

    @Override
    public Enterprise save(EnterpriseDto enterpriseDto) {
        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise(enterpriseDto.getNitEnterprise())
                .nameEnterprise(enterpriseDto.getNameEnterprise())
                .build(); 
        return enterpriseDao.save(enterprise);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Enterprise> finByNit(String nitEnterprise) {
        return enterpriseDao.findByNitEnterprise(nitEnterprise);
    
    }
}