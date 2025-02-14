package com.rayitosdesol.solarapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rayitosdesol.solarapp.model.dto.EnterpriseDto;
import com.rayitosdesol.solarapp.model.entity.Enterprise;
import com.rayitosdesol.solarapp.service.IEnterpriseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class EnterpriseController {

    @Autowired
    private IEnterpriseService enterpriseService;

    @PostMapping("enterprise")
    public ResponseEntity<EnterpriseDto> createEnterprise(@Valid @RequestBody EnterpriseDto enterpriseDto) {
        Enterprise enterpriseSave = enterpriseService.save(enterpriseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(enterpriseSave));
    }

    @GetMapping("enterprise/{nitEnterprise}")
    public ResponseEntity<EnterpriseDto> findByNit(@PathVariable String nitEnterprise) {
        Enterprise enterprise = enterpriseService.finByNit(nitEnterprise).orElse(null);
        if (enterprise == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(enterprise));
    }
    
        private EnterpriseDto convertToDto(Enterprise enterprise) {
        return EnterpriseDto.builder()
        .idEnterprise(enterprise.getIdEnterprise()  )
        .nitEnterprise(enterprise.getNitEnterprise())
        .nameEnterprise(enterprise.getNameEnterprise())
        .build();
    }
}

