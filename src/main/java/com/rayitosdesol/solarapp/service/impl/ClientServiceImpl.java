package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IClientService;
import com.rayitosdesol.solarapp.util.EmailUtil;
import com.rayitosdesol.solarapp.exception.ClientNotFoundException;
import com.rayitosdesol.solarapp.exception.EmailSendingException;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {
   
    private final ClientDao clientDao;
    private final ContractorDao contractorDao;
    private final EmailUtil emailUtil;

    public ClientServiceImpl(ClientDao clientDao, ContractorDao contractorDao, EmailUtil emailUtil) {
        this.clientDao = clientDao;
        this.contractorDao = contractorDao;
        this.emailUtil = emailUtil;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Transactional
    @Override
    public Client save(ClientDto clientDto) {
        Client client = Client.builder()
                .idClient(clientDto.getIdClient())
                .emailClient(clientDto.getEmailClient())
                .nameClient(clientDto.getNameClient())
                .phoneClient(clientDto.getPhoneClient())
                .cityClient(clientDto.getCityClient())
                .neighborhoodClient(clientDto.getNeighborhoodClient())
                .monthlyConsumptionClient(clientDto.getMonthlyConsumptionClient())
                .installationTypeClient(clientDto.getInstallationTypeClient())
                .subsidyLevel(determineSubsidyLevel(clientDto))
                .build();

        if (clientDto.getContractorId() != null) {
            Contractor contractor = contractorDao.findById(clientDto.getContractorId())
                    .orElseThrow(() -> new RuntimeException("Contratista no encontrado"));
            client.setContractor(contractor);
        }

        return clientDao.save(client);
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
            client.setSubsidyLevel(determineSubsidyLevel(clientDto));

            if (clientDto.getContractorId() != null) {
                Contractor contractor = contractorDao.findById(clientDto.getContractorId())
                        .orElseThrow(() -> new RuntimeException("Contratista no encontrado"));
                client.setContractor(contractor);
            }

            Client updatedClient = clientDao.save(client);

            try {
                emailUtil.sendSubsidyEmail(updatedClient.getEmailClient(), updatedClient.getSubsidyLevel());
            } catch (MessagingException | TemplateException | IOException e) {
                e.printStackTrace();
                throw new EmailSendingException("Failed to send subsidy email", e);
            }

            return updatedClient;
        } else {
            throw new ClientNotFoundException("El cliente con ID " + clientDto.getIdClient() + " no existe");
        }
    }

    @Transactional
    @Override
    public void delete(ClientDto clientDto) {
        Client client = clientDao.findById(clientDto.getIdClient())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        clientDao.delete(client);
    }

    private String determineSubsidyLevel(ClientDto clientDto) {
        int score = 0;
        if (clientDto.isLowIncome()) score += 3;
        if (clientDto.isSingleParent()) score += 2;
        if (clientDto.isDisplaced()) score += 2;
        if (clientDto.isDisabled()) score += 2;
        if (clientDto.isElderly()) score += 1;
        if (clientDto.isLimitedAccessToServices()) score += 1;
        if (clientDto.isInadequateHousing()) score += 1;

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