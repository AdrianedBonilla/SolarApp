package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IEnterpriseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EnterpriseControllerTest {

    @Mock
    private IEnterpriseService enterpriseService;

    @InjectMocks
    private EnterpriseController enterpriseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEnterprise() {
        EnterpriseDto enterpriseDto = EnterpriseDto.builder().nitEnterprise("1234567890").nameEnterprise("Enterprise").build();
        Enterprise enterprise = Enterprise.builder().nitEnterprise("1234567890").nameEnterprise("Enterprise").build();

        when(enterpriseService.save(any(EnterpriseDto.class))).thenReturn(enterprise);

        ResponseEntity<Object> response = enterpriseController.createEnterprise(enterpriseDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Enterprise", ((Enterprise) response.getBody()).getNameEnterprise());
    }

    @Test
    void testGetEnterprise() {
        Enterprise enterprise = Enterprise.builder().nitEnterprise("1234567890").nameEnterprise("Enterprise").build();

        when(enterpriseService.findByNit(anyString())).thenReturn(Optional.of(enterprise));

        ResponseEntity<Object> response = enterpriseController.getEnterprise("1234567890");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Enterprise", ((Enterprise) response.getBody()).getNameEnterprise());
    }

    @Test
    void testGetEnterpriseNotFound() {
        when(enterpriseService.findByNit(anyString())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = enterpriseController.getEnterprise("1234567890");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Enterprise not found", response.getBody());
    }
}