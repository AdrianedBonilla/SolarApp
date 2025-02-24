package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.dao.SubsidyDao;
import com.rayitosdesol.solarapp.model.dto.SubsidyDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Subsidy;
import com.rayitosdesol.solarapp.service.ISubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubsidyServiceImpl implements ISubsidyService {

    @Autowired
    private SubsidyDao subsidyDao;

    @Autowired
    private ClientDao clientDao;

    @Transactional(readOnly = true)
    @Override
    public List<Subsidy> findAll() {
        return subsidyDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Subsidy findById(Long id) {
        return subsidyDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Subsidy save(Subsidy subsidy) {
        return subsidyDao.save(subsidy);
    }

    @Transactional
    @Override
    public Subsidy update(Subsidy subsidy) {
        Subsidy existingSubsidy = subsidyDao.findById(subsidy.getId())
                .orElseThrow(() -> new RuntimeException("Subsidio no encontrado"));

        existingSubsidy.setLevel(subsidy.getLevel());
        existingSubsidy.setClient(subsidy.getClient());

        return subsidyDao.save(existingSubsidy);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Subsidy subsidy = subsidyDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Subsidio no encontrado"));

        subsidyDao.delete(subsidy);
    }

    @Override
    public String determineSubsidyLevel(Client client, SubsidyDto subsidyDto) { 
        int score = 0;
        if (subsidyDto.isLowIncome()) score += 3;
        if (subsidyDto.isSingleParent()) score += 2;
        if (subsidyDto.isDisplaced()) score += 2;
        if (subsidyDto.isDisabled()) score += 2;
        if (subsidyDto.isElderly()) score += 1;
        if (subsidyDto.isLimitedAccessToServices()) score += 1;
        if (subsidyDto.isInadequateHousing()) score += 1;

        if (score >= 8) {
            return "Nivel 3";
        } else if (score >= 5) {
            return "Nivel 2";
        } else if (score >= 3) {
            return "Nivel 1";
        } else {
            return "No aplicable";
        }
    }
}