package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ClientDao;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.service.IClienAuthtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientAuthServiceImpl implements IClienAuthtService {

    private static final Logger logger = LoggerFactory.getLogger(ClientAuthServiceImpl.class);

    private final ClientDao clientDao;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientAuthServiceImpl(ClientDao clientDao, BCryptPasswordEncoder passwordEncoder) {
        this.clientDao = clientDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> authenticate(String email, String password) {
        logger.info("Intentando autenticar al usuario con email: {}", email);
        
        return clientDao.findByEmailClient(email)
                .filter(client -> {
                    boolean passwordMatches = passwordEncoder.matches(password, client.getPasswordClient());
                    if (!passwordMatches) {
                        logger.warn("Contraseña incorrecta para el usuario: {}", email);
                    }
                    return passwordMatches;
                }).map(client -> {
                    logger.info("Autenticación exitosa para el usuario: {}", email);
                    return client;
                });
    }
}
