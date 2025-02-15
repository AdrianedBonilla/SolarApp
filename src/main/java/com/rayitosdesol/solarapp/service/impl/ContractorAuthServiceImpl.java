package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IContractorAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ContractorAuthServiceImpl implements IContractorAuthService {

    private static final Logger logger = LoggerFactory.getLogger(ContractorAuthServiceImpl.class);

    private final ContractorDao contractorDao;
    private final PasswordEncoder passwordEncoder;

    public ContractorAuthServiceImpl(ContractorDao contractorDao, PasswordEncoder passwordEncoder) {
        this.contractorDao = contractorDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Contractor> authenticate(String email, String password, String nitEnterprise) {
        logger.info("Intentando autenticar al usuario con email: {} y empresa NIT: {}", email, nitEnterprise);
        
        return contractorDao.findByEmailContractorAndEnterprise_NitEnterprise(email, nitEnterprise)
                .filter(contractor -> {
                    boolean passwordMatches = passwordEncoder.matches(password, contractor.getPasswordContractor());
                    if (!passwordMatches) {
                        logger.warn("Contraseña incorrecta para el usuario: {}", email);
                    }
                    return passwordMatches;
                }).map(contractor -> {
                    logger.info("Autenticación exitosa para el usuario: {}", email);
                    return contractor;
                });
    }
}

