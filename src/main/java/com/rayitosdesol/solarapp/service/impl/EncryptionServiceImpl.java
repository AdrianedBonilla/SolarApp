package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.service.EncryptionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final BCryptPasswordEncoder encoder;

    public EncryptionServiceImpl() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}