package com.rayitosdesol.solarapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IContractorService;
import com.rayitosdesol.solarapp.model.dao.EnterpriseDao;


@Service
public class ContractorServiceImpl implements IContractorService{ 

    private final ContractorDao contractorDao;
    private final EnterpriseDao enterpriseDao;

    public ContractorServiceImpl(ContractorDao contractorDao, EnterpriseDao enterpriseDao) {
        this.contractorDao = contractorDao;
        this.enterpriseDao = enterpriseDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Contractor> findAll() {
        return contractorDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Contractor findById(Long idContractor) {
        return contractorDao.findById(idContractor).orElse(null);
    }

    @Transactional
    public Contractor save(ContractorDto contractorDto) {
        Enterprise enterprise = enterpriseDao.findByNitEnterprise(contractorDto.getNitEnterprise())
        .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Contractor contractor = Contractor.builder()
        .idContractor(contractorDto.getIdContractor())
        .nameContractor(contractorDto.getNameContractor())
        .emailContractor(contractorDto.getEmailContractor())
        .phoneContractor(contractorDto.getPhoneContractor())
        .locationContractor(contractorDto.getLocationContractor())
        .expertiseContractor(contractorDto.getExpertiseContractor())
        .enterprise(enterprise) 
        .build();
    
        return contractorDao.save(contractor);
    }

    @Transactional
    @Override
    public Contractor update(ContractorDto contractorDto) {
    Optional<Contractor> optionalContractor = contractorDao.findById(contractorDto.getIdContractor());

    if (optionalContractor.isPresent()) {
        Contractor contractor = optionalContractor.get();
        
        contractor.setNameContractor(contractorDto.getNameContractor());
        contractor.setEmailContractor(contractorDto.getEmailContractor());
        contractor.setPhoneContractor(contractorDto.getPhoneContractor());
        contractor.setLocationContractor(contractorDto.getLocationContractor());
        contractor.setExpertiseContractor(contractorDto.getExpertiseContractor());

        if (contractorDto.getNitEnterprise() != null && !contractorDto.getNitEnterprise().isEmpty()) {
            Enterprise enterprise = findEnterpriseByNit(contractorDto.getNitEnterprise());
            contractor.setEnterprise(enterprise);
        }

        return contractorDao.save(contractor);
    } else {
        throw new RuntimeException("El contratista con ID " + contractorDto.getIdContractor() + " no existe");
    }
}

    @Transactional(readOnly = true)
    @Override
    public Contractor findByEmail(String emailContractor) {
        return contractorDao.findByEmailContractor(emailContractor).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Contractor contractor) {
        contractorDao.delete(contractor );
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        contractorDao.deleteById(id);
    }

    @Override
    public Enterprise findEnterpriseByNit(String nitEnterprise) {
        return enterpriseDao.findByNitEnterprise(nitEnterprise)
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }
    
}