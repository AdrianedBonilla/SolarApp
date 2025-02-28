package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IContractorService;
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
import static org.mockito.Mockito.when;

class ContractorControllerTest {

    @Mock
    private IContractorService contractorService;

    @InjectMocks
    private ContractorController contractorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContractors() {
        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Enterprise")
                .build();

        List<Contractor> contractors = new ArrayList<>();
        contractors.add(Contractor.builder().idContractor(1L).nameContractor("John Doe").enterprise(enterprise).build());

        when(contractorService.findAll()).thenReturn(contractors);

        ResponseEntity<Object> response = contractorController.getAllContractors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<?>) response.getBody()).size());
    }

    @Test
    void testGetContractorById() {
        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Enterprise")
                .build();

        Contractor contractor = Contractor.builder().idContractor(1L).nameContractor("John Doe").enterprise(enterprise).build();

        when(contractorService.findById(anyLong())).thenReturn(contractor);

        ResponseEntity<Object> response = contractorController.getContractorById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", ((ContractorDto) response.getBody()).getNameContractor());
    }

    @Test
    void testGetContractorByIdNotFound() {
        when(contractorService.findById(anyLong())).thenReturn(null);

        ResponseEntity<Object> response = contractorController.getContractorById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Contractor not found", response.getBody());
    }

    @Test
    void testCreateContractor() {
        ContractorDto contractorDto = ContractorDto.builder().nameContractor("John Doe").build();

        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Enterprise")
                .build();

        Contractor contractor = Contractor.builder().idContractor(1L).nameContractor("John Doe").enterprise(enterprise).build();

        when(contractorService.save(any(ContractorDto.class))).thenReturn(contractor);

        ResponseEntity<Object> response = contractorController.createContractor(contractorDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", ((ContractorDto) response.getBody()).getNameContractor());
    }

    @Test
    void testUpdateContractor() {
        ContractorDto contractorDto = ContractorDto.builder().nameContractor("John Doe").build();

        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Enterprise")
                .build();

        Contractor contractor = Contractor.builder().idContractor(1L).nameContractor("John Doe").enterprise(enterprise).build();

        when(contractorService.findById(anyLong())).thenReturn(contractor);
        when(contractorService.update(any(ContractorDto.class))).thenReturn(contractor);

        ResponseEntity<Object> response = contractorController.updateContractor(1L, contractorDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", ((ContractorDto) response.getBody()).getNameContractor());
    }

    @Test
    void testDeleteContractor() {
        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Enterprise")
                .build();

        Contractor contractor = Contractor.builder().idContractor(1L).nameContractor("John Doe").enterprise(enterprise).build();

        when(contractorService.findById(anyLong())).thenReturn(contractor);

        ResponseEntity<Object> response = contractorController.deleteContractor(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}