package com.rayitosdesol.solarapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IEnterpriseService;

@RestController
@RequestMapping("/api/v1")
public class EnterpriseController {

    @Autowired
    private IEnterpriseService enterpriseService;

    @PostMapping("/enterprise")
    public ResponseEntity<Enterprise> createEnterprise(@RequestBody Enterprise enterprise) {
        return ResponseEntity.ok(enterpriseService.createEnterprise(enterprise));
    }

    @GetMapping("/enterprise/{nitEnterprise}")
    public ResponseEntity<Enterprise> getEnterpriseByNit(@PathVariable String nitEnterprise) {
        return enterpriseService.getEnterpriseByNit(nitEnterprise)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

