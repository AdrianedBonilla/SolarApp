package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IEnterpriseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class EnterpriseController {

    private final IEnterpriseService enterpriseService;

    public EnterpriseController(IEnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @PostMapping("enterprise")
    public ResponseEntity<Object> createEnterprise(@Valid @RequestBody EnterpriseDto enterpriseDto) {
        Enterprise enterprise = enterpriseService.save(enterpriseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(enterprise);
    }

    @GetMapping("enterprise/{nitEnterprise}")
    public ResponseEntity<Object> getEnterprise(@PathVariable("nitEnterprise") String nitEnterprise) {
        Optional<Enterprise> enterprise = enterpriseService.findByNit(nitEnterprise);
        if (enterprise.isPresent()) {
            return ResponseEntity.ok(enterprise.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enterprise not found");
        }
    }
}