package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IClientService;
import com.rayitosdesol.solarapp.service.IContractorService;
import org.springframework.stereotype.Service;

@Service
public class UnifiedSearchService {

    private final IClientService clientService;
    private final IContractorService contractorService;

    public UnifiedSearchService(IClientService clientService, IContractorService contractorService) {
        this.clientService = clientService;
        this.contractorService = contractorService;
    }

    public Object findByEmail(String email) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            return client;
        }

        Contractor contractor = contractorService.findByEmail(email);
        if (contractor != null) {
            return contractor;
        }

        return null;
    }
}