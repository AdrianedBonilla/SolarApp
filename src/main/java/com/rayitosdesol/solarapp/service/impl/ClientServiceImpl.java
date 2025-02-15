package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.service.IClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientDao clientDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientDao clientDao, BCryptPasswordEncoder passwordEncoder) {
        this.clientDao = clientDao;
        this.passwordEncoder = passwordEncoder;
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
        .siteConditionsClient(clientDto.getSiteConditionsClient()).
        build();

        return clientDao.save(client);

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
            client.setSiteConditionsClient(clientDto.getSiteConditionsClient());

            if (clientDto.getPasswordClient() != null && !clientDto.getPasswordClient().isEmpty()) {
                client.setPasswordClient(encodePassword(clientDto.getPasswordClient()));
            }

            return clientDao.save(client);
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
