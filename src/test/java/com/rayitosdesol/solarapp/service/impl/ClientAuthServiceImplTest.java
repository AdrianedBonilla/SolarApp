package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ClientAuthServiceImplTest {

    @Mock
    private ClientDao clientDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientAuthServiceImpl clientAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateSuccess() {
        String email = "test@example.com";
        String password = "password";

        Client client = Client.builder()
                .emailClient(email)
                .passwordClient(password)
                .build();

        when(clientDao.findByEmailClient(email)).thenReturn(Optional.of(client));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Optional<Client> result = clientAuthService.authenticate(email, password);

        assertTrue(result.isPresent());
    }

    @Test
    void testAuthenticateFailure() {
        String email = "test@example.com";
        String password = "password";

        when(clientDao.findByEmailClient(email)).thenReturn(Optional.empty());

        Optional<Client> result = clientAuthService.authenticate(email, password);

        assertFalse(result.isPresent());
    }
}