package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IClientService;
import com.rayitosdesol.solarapp.service.IContractorService;
import com.rayitosdesol.solarapp.util.EmailUtil;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class ClientController {

    private static final String CLIENT_NOT_FOUND = "Client not found";

    private final IClientService clientService;
    private final IContractorService contractorService;
    private final EmailUtil emailUtil;

    public ClientController(IClientService clientService, IContractorService contractorService, EmailUtil emailUtil) {
        this.clientService = clientService;
        this.contractorService = contractorService;
        this.emailUtil = emailUtil;
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

    @GetMapping("client/email/{email}")
    public ResponseEntity<Object> getClientByEmail(@PathVariable String email) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CLIENT_NOT_FOUND);
        }
    }

    @PostMapping("client")
    public ResponseEntity<Object> createClient(@Valid @RequestBody ClientDto clientDto) {

        Client clientWithSameEmail = clientService.findByEmail(clientDto.getEmailClient());
        if (clientWithSameEmail != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ya está en uso");
        }

        Client clientSave = clientService.save(clientDto);

        if (clientDto.getContractorId() != null) {
            Contractor contractor = contractorService.findById(clientDto.getContractorId());
            if (contractor != null) {
                Map<String, Object> model = new HashMap<>();
                model.put("contractorName", contractor.getNameContractor());
                model.put("clientName", clientSave.getNameClient());
                model.put("clientEmail", clientSave.getEmailClient());
                model.put("clientPhone", clientSave.getPhoneClient());
                model.put("clientLocation", clientSave.getCityClient());

                try {
                    emailUtil.sendContractorNotificationEmail(contractor.getEmailContractor(),
                            "Nuevo Cliente Registrado", model);
                } catch (MessagingException | TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

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

        if (clientDto.getContractorId() != null) {
            Contractor contractor = contractorService.findById(clientDto.getContractorId());
            if (contractor != null) {
                Map<String, Object> model = new HashMap<>();
                model.put("contractorName", contractor.getNameContractor());
                model.put("clientName", updatedClient.getNameClient());
                model.put("clientEmail", updatedClient.getEmailClient());
                model.put("clientPhone", updatedClient.getPhoneClient());
                model.put("clientLocation", updatedClient.getCityClient());

                try {
                    emailUtil.sendContractorNotificationEmail(contractor.getEmailContractor(), "Cliente Actualizado",
                            model);
                } catch (MessagingException | TemplateException | IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
                .subsidyLevel(client.getSubsidyLevel())
                .build();
    }
}