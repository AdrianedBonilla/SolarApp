package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.controller.UnifiedSearchController;
import com.rayitosdesol.solarapp.service.impl.UnifiedSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UnifiedSearchControllerTest {

    @Mock
    private UnifiedSearchService unifiedSearchService;

    @InjectMocks
    private UnifiedSearchController unifiedSearchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchByEmail_Found() {
        String email = "test@example.com";
        Object mockResult = new Object(); // Puedes reemplazar esto con un objeto real de Client o Contractor

        when(unifiedSearchService.findByEmail(email)).thenReturn(mockResult);

        ResponseEntity<Object> response = unifiedSearchController.searchByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResult, response.getBody());
    }

    @Test
    void testSearchByEmail_NotFound() {
        String email = "notfound@example.com";

        when(unifiedSearchService.findByEmail(email)).thenReturn(null);

        ResponseEntity<Object> response = unifiedSearchController.searchByEmail(email);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró ningún cliente o contratista con el email proporcionado", response.getBody());
    }
}