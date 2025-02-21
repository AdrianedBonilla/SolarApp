package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.service.EncryptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class EncryptionController {

    private final EncryptionService encryptionService;

    public EncryptionController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @PostMapping("encrypt")
    public ResponseEntity<Object> encryptPassword(@RequestBody String rawPassword) {
        String encryptedPassword = encryptionService.encrypt(rawPassword);
        return ResponseEntity.ok(encryptedPassword);
    }
}