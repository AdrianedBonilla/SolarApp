package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.service.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ClientControllerTest {

    @Mock
    private IClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(Client.builder().idClient(1L).nameClient("John Doe").build());

        when(clientService.findAll()).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.getAllClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetClientById() {
        Client client = Client.builder().idClient(1L).nameClient("John Doe").build();

        when(clientService.findById(anyLong())).thenReturn(client);

        ResponseEntity<Object> response = clientController.getClientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", ((Client) response.getBody()).getNameClient());
    }

    @Test
    void testGetClientByIdNotFound() {
        when(clientService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Object> response = clientController.getClientById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Client not found", response.getBody());
    }

    @Test
    void testGetClientByEmail() {
        Client client = Client.builder().idClient(1L).nameClient("John Doe").emailClient("john.doe@example.com").build();

        when(clientService.findByEmail(anyString())).thenReturn(client);

        ResponseEntity<Object> response = clientController.getClientByEmail("john.doe@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", ((Client) response.getBody()).getNameClient());
    }

    @Test
    void testGetClientByEmailNotFound() {
        when(clientService.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<Object> response = clientController.getClientByEmail("john.doe@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Client not found", response.getBody());
    }

    @Test
    void testCreateClient() {
        ClientDto clientDto = ClientDto.builder().nameClient("John Doe").emailClient("john.doe@example.com").build();
        Client client = Client.builder().idClient(1L).nameClient("John Doe").emailClient("john.doe@example.com").build();

        when(clientService.save(any(ClientDto.class))).thenReturn(client);

        ResponseEntity<Object> response = clientController.createClient(clientDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", ((ClientDto) response.getBody()).getNameClient());
    }

    @Test
    void testUpdateClient() {
        ClientDto clientDto = ClientDto.builder().nameClient("John Doe").emailClient("john.doe@example.com").build();
        Client client = Client.builder().idClient(1L).nameClient("John Doe").emailClient("john.doe@example.com").build();

        when(clientService.findById(anyLong())).thenReturn(client);
        when(clientService.update(any(ClientDto.class))).thenReturn(client);

        ResponseEntity<Object> response = clientController.updateClient(1L, clientDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", ((ClientDto) response.getBody()).getNameClient());
    }

    @Test
    void testDeleteClient() {
        Client client = Client.builder().idClient(1L).nameClient("John Doe").build();

        when(clientService.findById(anyLong())).thenReturn(client);

        ResponseEntity<Object> response = clientController.deleteClient(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}