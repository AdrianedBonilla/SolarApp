package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.EnterpriseDao;
import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EnterpriseServiceImplTest {

    @Mock
    private EnterpriseDao enterpriseDao;

    @InjectMocks
    private EnterpriseServiceImpl enterpriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        EnterpriseDto enterpriseDto = EnterpriseDto.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Rayitos de Sol")
                .build();

        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise("1234567890")
                .nameEnterprise("Rayitos de Sol")
                .build();

        when(enterpriseDao.save(any(Enterprise.class))).thenReturn(enterprise);

        Enterprise savedEnterprise = enterpriseService.save(enterpriseDto);

        assertEquals(enterpriseDto.getNitEnterprise(), savedEnterprise.getNitEnterprise());
        assertEquals(enterpriseDto.getNameEnterprise(), savedEnterprise.getNameEnterprise());
    }

    @Test
    void testFindByNit() {
        String nitEnterprise = "1234567890";
        Enterprise enterprise = Enterprise.builder()
                .nitEnterprise(nitEnterprise)
                .nameEnterprise("Rayitos de Sol")
                .build();

        when(enterpriseDao.findByNitEnterprise(nitEnterprise)).thenReturn(Optional.of(enterprise));

        Optional<Enterprise> foundEnterprise = enterpriseService.findByNit(nitEnterprise);

        assertTrue(foundEnterprise.isPresent());
        assertEquals("1234567890", foundEnterprise.get().getNitEnterprise());
    }

    @Test
    void testFindByNitNotFound() {
        String nitEnterprise = "9999999999";

        when(enterpriseDao.findByNitEnterprise(nitEnterprise)).thenReturn(Optional.empty());

        Optional<Enterprise> foundEnterprise = enterpriseService.findByNit(nitEnterprise);

        assertFalse(foundEnterprise.isPresent());
    }

    @Test
    void testSaveInvalidData() {
        EnterpriseDto enterpriseDto = EnterpriseDto.builder()
                .nitEnterprise("null") // NIT inválido
                .nameEnterprise("Rayitos de Sol")
                .build();

        when(enterpriseDao.save(any(Enterprise.class))).thenThrow(new IllegalArgumentException("NIT cannot be null"));

        try {
            enterpriseService.save(enterpriseDto);
        } catch (IllegalArgumentException e) {
            assertEquals("NIT cannot be null", e.getMessage());
        }
    }
}