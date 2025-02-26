package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.service.IClientService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ClientController {

    private static final String CLIENT_NOT_FOUND = "Client not found";

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("client/{id}")
    public ResponseEntity<Object> getClientById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CLIENT_NOT_FOUND);
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping("client")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClientDto clientDto) {
        Client clientSave = clientService.save(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(clientSave));
    }

    @PutMapping("client/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
        Client existingClient = clientService.findById(id);
        if (existingClient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }

        Client clientWithSameEmail = clientService.findByEmail(clientDto.getEmailClient());
        if (clientWithSameEmail != null && !clientWithSameEmail.getIdClient().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ya está en uso");
        }

        clientDto.setIdClient(id);
        Client updatedClient = clientService.update(clientDto);
        return ResponseEntity.ok(convertToDto(updatedClient));
    }

    @DeleteMapping("client/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable Long id) {
        Client clientDelete = clientService.findById(id);
        if (clientDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }
        clientService.delete(convertToDto(clientDelete));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ClientDto convertToDto(Client client) {
        return ClientDto.builder()
                .idClient(client.getIdClient())
                .emailClient(client.getEmailClient())
                .nameClient(client.getNameClient())
                .phoneClient(client.getPhoneClient())
                .cityClient(client.getCityClient())
                .neighborhoodClient(client.getNeighborhoodClient())
                .monthlyConsumptionClient(client.getMonthlyConsumptionClient())
                .installationTypeClient(client.getInstallationTypeClient())
                .contractorId(client.getContractor() != null ? client.getContractor().getIdContractor() : null)
                .subsidyLevel(client.getSubsidyLevel()) // Nuevo campo
                .build();
    }
}