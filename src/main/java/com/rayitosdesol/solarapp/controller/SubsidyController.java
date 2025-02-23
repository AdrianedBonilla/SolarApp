package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.SubsidyDto;
import com.rayitosdesol.solarapp.model.entity.Client;
import com.rayitosdesol.solarapp.model.entity.Subsidy;
import com.rayitosdesol.solarapp.service.IClientService;
import com.rayitosdesol.solarapp.service.ISubsidyService;
import com.rayitosdesol.solarapp.util.EmailUtil;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class SubsidyController {

    @Autowired
    private ISubsidyService subsidyService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private EmailUtil emailUtil;

    @GetMapping("subsidies")
    public ResponseEntity<List<Subsidy>> getAllSubsidies() {
        List<Subsidy> subsidies = subsidyService.findAll();
        return ResponseEntity.ok(subsidies);
    }

    @GetMapping("subsidy/{id}")
    public ResponseEntity<Object> getSubsidyById(@PathVariable Long id) {
        Subsidy subsidy = subsidyService.findById(id);
        if (subsidy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subsidio no encontrado");
        }
        return ResponseEntity.ok(subsidy);
    }

    @PostMapping("subsidy")
    public ResponseEntity<Object> createSubsidy(@RequestBody SubsidyDto subsidyDto) throws MessagingException, TemplateException, IOException {
        Client client = clientService.findById(subsidyDto.getClientId());
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }

        if (client.getContractor() == null || client.getContractor().getIdContractor() != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente no tiene un contratista válido para aplicar a subsidios");
        }

        String subsidyLevel = subsidyService.determineSubsidyLevel(client, subsidyDto);

        Subsidy subsidy = Subsidy.builder()
                .level(subsidyLevel)
                .client(client)
                .build();

        Subsidy savedSubsidy = subsidyService.save(subsidy);

        emailUtil.sendSubsidyEmail(client.getEmailClient(), subsidyLevel);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubsidy);
    }

    @PutMapping("subsidy/{id}")
    public ResponseEntity<Object> updateSubsidy(@PathVariable Long id, @RequestBody SubsidyDto subsidyDto) {
        Subsidy existingSubsidy = subsidyService.findById(id);
        if (existingSubsidy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subsidio no encontrado");
        }

        // Convertir SubsidyDto a Subsidy
        Subsidy subsidy = Subsidy.builder()
                .id(subsidyDto.getId())
                .level(subsidyDto.getLevel())
                .client(existingSubsidy.getClient())
                .build();

        Subsidy updatedSubsidy = subsidyService.update(subsidy);
        return ResponseEntity.ok(updatedSubsidy);
    }

    @DeleteMapping("subsidy/{id}")
    public ResponseEntity<Object> deleteSubsidy(@PathVariable Long id) {
        Subsidy subsidy = subsidyService.findById(id);
        if (subsidy == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subsidio no encontrado");
        }
        subsidyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}