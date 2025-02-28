package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ContractorAuthServiceImplTest {

    @Mock
    private ContractorDao contractorDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ContractorAuthServiceImpl contractorAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateSuccess() {
        String email = "test@example.com";
        String password = "password";
        String nitEnterprise = "1234567890";

        Contractor contractor = Contractor.builder()
                .emailContractor(email)
                .passwordContractor(password)
                .build();

        when(contractorDao.findByEmailContractorAndEnterprise_NitEnterprise(email, nitEnterprise))
                .thenReturn(Optional.of(contractor));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Optional<Contractor> result = contractorAuthService.authenticate(email, password, nitEnterprise);

        assertTrue(result.isPresent());
    }

    @Test
    void testAuthenticateFailure() {
        String email = "test@example.com";
        String password = "password";
        String nitEnterprise = "1234567890";

        when(contractorDao.findByEmailContractorAndEnterprise_NitEnterprise(email, nitEnterprise))
                .thenReturn(Optional.empty());

        Optional<Contractor> result = contractorAuthService.authenticate(email, password, nitEnterprise);

        assertFalse(result.isPresent());
    }
}