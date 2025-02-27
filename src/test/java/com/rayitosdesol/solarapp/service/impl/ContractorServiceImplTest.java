package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dao.EnterpriseDao;
import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ContractorServiceImplTest {

    @Mock
    private ContractorDao contractorDao;

    @Mock
    private EnterpriseDao enterpriseDao;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ContractorServiceImpl contractorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ContractorDto contractorDto = ContractorDto.builder()
                .idContractor(1L)
                .nameContractor("John Doe")
                .emailContractor("john.doe@example.com")
                .phoneContractor("1234567890")
                .locationContractor("Location")
                .expertiseContractor("Expertise")
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
                .emailContractor("john.doe@example.com")
                .phoneContractor("1234567890")
                .locationContractor("Location")
                .expertiseContractor("Expertise")
                .passwordContractor("encodedPassword")
                .enterprise(enterprise)
                .build();

        when(enterpriseDao.findByNitEnterprise(any(String.class))).thenReturn(Optional.of(enterprise));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(contractorDao.save(any(Contractor.class))).thenReturn(contractor);

        Contractor savedContractor = contractorService.save(contractorDto);

        assertEquals(contractorDto.getNameContractor(), savedContractor.getNameContractor());
        assertEquals(contractorDto.getEmailContractor(), savedContractor.getEmailContractor());
    }

    @Test
    void testSaveEnterpriseNotFound() {
        ContractorDto contractorDto = ContractorDto.builder()
                .idContractor(1L)
                .nameContractor("John Doe")
                .emailContractor("john.doe@example.com")
                .phoneContractor("1234567890")
                .locationContractor("Location")
                .expertiseContractor("Expertise")
                .passwordContractor("password")
                .nitEnterprise("1234567890")
                .build();

        when(enterpriseDao.findByNitEnterprise(any(String.class))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contractorService.save(contractorDto);
        });

        assertEquals("Empresa no encontrada", exception.getMessage());
    }
}