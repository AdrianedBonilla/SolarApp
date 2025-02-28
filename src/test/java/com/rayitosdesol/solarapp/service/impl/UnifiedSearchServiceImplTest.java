package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IClientService;
import com.rayitosdesol.solarapp.service.IContractorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UnifiedSearchServiceImplTest {

    @Mock
    private IClientService clientService;

    @Mock
    private IContractorService contractorService;

    @InjectMocks
    private UnifiedSearchServiceImpl unifiedSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmailClient() {
        String email = "client@example.com";
        Client client = Client.builder()
                .emailClient(email)
                .build();

        when(clientService.findByEmail(anyString())).thenReturn(client);

        Object result = unifiedSearchService.findByEmail(email);

        assertEquals(client, result);
    }

    @Test
    void testFindByEmailContractor() {
        String email = "contractor@example.com";
        Contractor contractor = Contractor.builder()
                .emailContractor(email)
                .build();

        when(clientService.findByEmail(anyString())).thenReturn(null);
        when(contractorService.findByEmail(anyString())).thenReturn(contractor);

        Object result = unifiedSearchService.findByEmail(email);

        assertEquals(contractor, result);
    }

    @Test
    void testFindByEmailNotFound() {
        String email = "notfound@example.com";

        when(clientService.findByEmail(anyString())).thenReturn(null);
        when(contractorService.findByEmail(anyString())).thenReturn(null);

        Object result = unifiedSearchService.findByEmail(email);

        assertNull(result);
    }
}