package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.service.IClienAuthtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ClientAuthControllerTest {

    @Mock
    private IClienAuthtService clientAuthService;

    @InjectMocks
    private ClientAuthController clientAuthController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        ClientDto clientDto = ClientDto.builder()
                .emailClient("test@example.com")
                .passwordClient("password")
                .build();

        Client client = Client.builder()
                .idClient(1L)
                .emailClient("test@example.com")
                .nameClient("John Doe")
                .build();

        when(clientAuthService.authenticate(anyString(), anyString())).thenReturn(Optional.of(client));

        ResponseEntity<?> response = clientAuthController.login(clientDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto.getEmailClient(), ((ClientDto) response.getBody()).getEmailClient());
    }

    @Test
    void testLoginFailure() {
        ClientDto clientDto = ClientDto.builder()
                .emailClient("test@example.com")
                .passwordClient("password")
                .build();

        when(clientAuthService.authenticate(anyString(), anyString())).thenReturn(Optional.empty());

        ResponseEntity<?> response = clientAuthController.login(clientDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales incorrectas", response.getBody());
    }
}