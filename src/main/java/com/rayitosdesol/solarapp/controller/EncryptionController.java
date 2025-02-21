package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/encrypt")
public class EncryptionController {

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping
    public String encryptPassword(@RequestBody String rawPassword) {
        return encryptionService.encrypt(rawPassword);
    }
}