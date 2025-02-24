package com.rayitosdesol.solarapp.service;

import com.rayitosdesol.solarapp.model.dto.SubsidyDto;
import com.rayitosdesol.solarapp.model.entity.Subsidy;
import com.rayitosdesol.solarapp.model.entity.Client;

import java.util.List;

public interface ISubsidyService {
    List<Subsidy> findAll();
    Subsidy findById(Long id);
    Subsidy save(Subsidy subsidy);
    Subsidy update(Subsidy subsidy);
    void delete(Long id);
    String determineSubsidyLevel(Client client, SubsidyDto subsidyDto);
}