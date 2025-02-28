package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.util.EmailUtil;
import com.rayitosdesol.solarapp.exception.ClientNotFoundException;
import com.rayitosdesol.solarapp.exception.EmailSendingException;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private ClientDao clientDao;

    @Mock
    private ContractorDao contractorDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ClientDto clientDto = ClientDto.builder()
                .idClient(1L)
                .emailClient("john.doe@example.com")
                .passwordClient("password")
                .nameClient("John Doe")
                .phoneClient("1234567890")
                .cityClient("City")
                .neighborhoodClient("Neighborhood")
                .monthlyConsumptionClient(100)
                .installationTypeClient("Type")
                .contractorId(1L)
                .build();

        Contractor contractor = Contractor.builder()
                .idContractor(1L)
                .nameContractor("Contractor")
                .build();

        Client client = Client.builder()
                .idClient(1L)
                .emailClient("john.doe@example.com")
                .passwordClient("encodedPassword")
                .nameClient("John Doe")
                .phoneClient("1234567890")
                .cityClient("City")
                .neighborhoodClient("Neighborhood")
                .monthlyConsumptionClient(100)
                .installationTypeClient("Type")
                .contractor(contractor)
                .build();

        when(contractorDao.findById(any(Long.class))).thenReturn(Optional.of(contractor));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(clientDao.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.save(clientDto);

        assertEquals(clientDto.getEmailClient(), savedClient.getEmailClient());
        assertEquals(clientDto.getNameClient(), savedClient.getNameClient());
    }

    @Test
    void testSaveContractorNotFound() {
        ClientDto clientDto = ClientDto.builder()
                .idClient(1L)
                .emailClient("john.doe@example.com")
                .passwordClient("password")
                .nameClient("John Doe")
                .phoneClient("1234567890")
                .cityClient("City")
                .neighborhoodClient("Neighborhood")
                .monthlyConsumptionClient(100)
                .installationTypeClient("Type")
                .contractorId(1L)
                .build();

        when(contractorDao.findById(any(Long.class))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.save(clientDto);
        });

        assertEquals("Contratista no encontrado", exception.getMessage());
    }

    @Test
    void testUpdateClientNotFound() {
        ClientDto clientDto = ClientDto.builder()
                .idClient(1L)
                .emailClient("john.doe@example.com")
                .passwordClient("password")
                .nameClient("John Doe")
                .phoneClient("1234567890")
                .cityClient("City")
                .neighborhoodClient("Neighborhood")
                .monthlyConsumptionClient(100)
                .installationTypeClient("Type")
                .contractorId(1L)
                .build();

        when(clientDao.findById(any(Long.class))).thenReturn(Optional.empty());

        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> {
            clientService.update(clientDto);
        });

        assertEquals("El cliente con ID 1 no existe", exception.getMessage());
    }

    @Test
    void testUpdate() throws MessagingException, TemplateException, IOException {
        ClientDto clientDto = ClientDto.builder()
                .idClient(1L)
                .emailClient("john.doe@example.com")
                .passwordClient("password")
                .nameClient("John Doe")
                .phoneClient("1234567890")
                .cityClient("City")
                .neighborhoodClient("Neighborhood")
                .monthlyConsumptionClient(100)
                .installationTypeClient("Type")
                .contractorId(1L)
                .build();

        Contractor contractor = Contractor.builder()
                .idContractor(1L)
                .nameContractor("Contractor")
                .build();

        Client client = Client.builder()
                .idClient(1L)
                .emailClient("john.doe@example.com")
                .passwordClient("encodedPassword")
                .nameClient("John Doe")
                .phoneClient("1234567890")
                .cityClient("City")
                .neighborhoodClient("Neighborhood")
                .monthlyConsumptionClient(100)
                .installationTypeClient("Type")
                .contractor(contractor)
                .build();

        when(clientDao.findById(any(Long.class))).thenReturn(Optional.of(client));
        when(contractorDao.findById(any(Long.class))).thenReturn(Optional.of(contractor));
        when(clientDao.save(any(Client.class))).thenReturn(client);

        Client updatedClient = clientService.update(clientDto);

        assertEquals(clientDto.getEmailClient(), updatedClient.getEmailClient());
        assertEquals(clientDto.getNameClient(), updatedClient.getNameClient());
    }
}