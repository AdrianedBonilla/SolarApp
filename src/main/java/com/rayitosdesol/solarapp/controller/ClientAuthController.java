package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.service.IClienAuthtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/")
public class ClientAuthController {

    private final IClienAuthtService clientAuthService;

    public ClientAuthController(IClienAuthtService clientAuthService) {
        this.clientAuthService = clientAuthService;
    }

    @PostMapping("clients/login")
    public ResponseEntity<?> login(@RequestBody ClientDto clientDto) {
        Optional<Client> client = clientAuthService.authenticate(
                clientDto.getEmailClient(),
                clientDto.getPasswordClient()
        );

        if (client.isPresent()) {
            return ResponseEntity.ok(convertToDto(client.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
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
                .build();
    }
}