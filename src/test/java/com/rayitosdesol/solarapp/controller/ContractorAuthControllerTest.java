package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IContractorAuthService;
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

class ContractorAuthControllerTest {

    @Mock
    private IContractorAuthService contractorAuthService;

    @InjectMocks
    private ContractorAuthController contractorAuthController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        ContractorDto contractorDto = ContractorDto.builder()
                .emailContractor("test@example.com")
                .passwordContractor("password")
                .nitEnterprise("1234567890")
                .build();

        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Enterprise")
                .build();

        Contractor contractor = Contractor.builder()
                .idContractor(1L)
                .nameContractor("John Doe")
                .emailContractor("test@example.com")
                .enterprise(enterprise)
                .build();

        when(contractorAuthService.authenticate(anyString(), anyString(), anyString())).thenReturn(Optional.of(contractor));

        ResponseEntity<Object> response = contractorAuthController.login(contractorDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contractorDto.getEmailContractor(), ((ContractorDto) response.getBody()).getEmailContractor());
    }

    @Test
    void testLoginFailure() {
        ContractorDto contractorDto = ContractorDto.builder()
                .emailContractor("test@example.com")
                .passwordContractor("password")
                .nitEnterprise("1234567890")
                .build();

        when(contractorAuthService.authenticate(anyString(), anyString(), anyString())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = contractorAuthController.login(contractorDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales incorrectas o empresa no registrada", response.getBody());
    }
}