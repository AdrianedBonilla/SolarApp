package com.rayitosdesol.solarapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Enterprise;

@Service
public interface IContractorService {

    List<Contractor> findAll();

    Contractor findById(Long idContractor);

    Contractor save(ContractorDto contractor);
    
    void delete(Contractor contractor);

    void deleteById(Long idContractor);

    Enterprise findEnterpriseByNit(String nitEnterprise);

    String encodePassword(String password);

    Contractor findByEmail(String email);

    Contractor update(ContractorDto contractor);

}

