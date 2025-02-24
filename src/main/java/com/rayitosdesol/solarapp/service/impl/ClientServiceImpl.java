package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.dto.SubsidyDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Subsidy;
import com.rayitosdesol.solarapp.service.IClientService;
import com.rayitosdesol.solarapp.service.ISubsidyService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientDao clientDao;
    private final ContractorDao contractorDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ISubsidyService subsidyService;

    public ClientServiceImpl(ClientDao clientDao, ContractorDao contractorDao, BCryptPasswordEncoder passwordEncoder, ISubsidyService subsidyService) {
        this.clientDao = clientDao;
        this.contractorDao = contractorDao;
        this.passwordEncoder = passwordEncoder;
        this.subsidyService = subsidyService;
    }

    @Transactional
    @Override
    public Client save(ClientDto clientDto) {
        Client client = Client.builder()
                .idClient(clientDto.getIdClient())
                .emailClient(clientDto.getEmailClient())
                .passwordClient(passwordEncoder.encode(clientDto.getPasswordClient()))
                .nameClient(clientDto.getNameClient())
                .phoneClient(clientDto.getPhoneClient())
                .cityClient(clientDto.getCityClient())
                .neighborhoodClient(clientDto.getNeighborhoodClient())
                .monthlyConsumptionClient(clientDto.getMonthlyConsumptionClient())
                .installationTypeClient(clientDto.getInstallationTypeClient())
                .build();

        if (clientDto.getContractorId() != null) {
            Contractor contractor = contractorDao.findById(clientDto.getContractorId())
                    .orElseThrow(() -> new RuntimeException("Contratista no encontrado"));
            client.setContractor(contractor);
        }

        Client savedClient = clientDao.save(client);

        // Convertir ClientDto a SubsidyDto
        SubsidyDto subsidyDto = SubsidyDto.builder()
                .clientId(clientDto.getIdClient())
                .lowIncome(clientDto.isLowIncome())
                .singleParent(clientDto.isSingleParent())
                .displaced(clientDto.isDisplaced())
                .disabled(clientDto.isDisabled())
                .elderly(clientDto.isElderly())
                .limitedAccessToServices(clientDto.isLimitedAccessToServices())
                .inadequateHousing(clientDto.isInadequateHousing())
                .build();

        // Determinar y guardar el nivel de subsidio
        String subsidyLevel = subsidyService.determineSubsidyLevel(savedClient, subsidyDto);
        Subsidy subsidy = Subsidy.builder()
                .level(subsidyLevel)
                .client(savedClient)
                .build();
        subsidyService.save(subsidy);

        return savedClient;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Client findById(Long idClient) {
        return clientDao.findById(idClient).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Client findByEmail(String emailClient) {
        return clientDao.findByEmailClient(emailClient).orElse(null);
    }

    @Transactional
    @Override
    public Client update(ClientDto clientDto) {
        Optional<Client> optionalClient = clientDao.findById(clientDto.getIdClient());

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            client.setEmailClient(clientDto.getEmailClient());
            client.setNameClient(clientDto.getNameClient());
            client.setPhoneClient(clientDto.getPhoneClient());
            client.setCityClient(clientDto.getCityClient());
            client.setNeighborhoodClient(clientDto.getNeighborhoodClient());
            client.setMonthlyConsumptionClient(clientDto.getMonthlyConsumptionClient());
            client.setInstallationTypeClient(clientDto.getInstallationTypeClient());

            if (clientDto.getPasswordClient() != null && !clientDto.getPasswordClient().isEmpty()) {
                client.setPasswordClient(encodePassword(clientDto.getPasswordClient()));
            }

            if (clientDto.getContractorId() != null) {
                Contractor contractor = contractorDao.findById(clientDto.getContractorId())
                        .orElseThrow(() -> new RuntimeException("Contratista no encontrado"));
                client.setContractor(contractor);
            }

            Client updatedClient = clientDao.save(client);

            // Convertir ClientDto a SubsidyDto
            SubsidyDto subsidyDto = SubsidyDto.builder()
                    .clientId(clientDto.getIdClient())
                    .lowIncome(clientDto.isLowIncome())
                    .singleParent(clientDto.isSingleParent())
                    .displaced(clientDto.isDisplaced())
                    .disabled(clientDto.isDisabled())
                    .elderly(clientDto.isElderly())
                    .limitedAccessToServices(clientDto.isLimitedAccessToServices())
                    .inadequateHousing(clientDto.isInadequateHousing())
                    .build();

            // Determinar y actualizar el nivel de subsidio
            String subsidyLevel = subsidyService.determineSubsidyLevel(updatedClient, subsidyDto);
            Subsidy subsidy = Subsidy.builder()
                    .level(subsidyLevel)
                    .client(updatedClient)
                    .build();
            subsidyService.update(subsidy);

            return updatedClient;
        } else {
            throw new RuntimeException("El cliente con ID " + clientDto.getIdClient() + " no existe");
        }
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    @Override
    public void delete(ClientDto clientDto) {
        Client client = clientDao.findById(clientDto.getIdClient())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        clientDao.delete(client);
    }
}