package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.impl.UnifiedSearchServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UnifiedSearchController {

    private final UnifiedSearchServiceImpl unifiedSearchService;

    public UnifiedSearchController(UnifiedSearchServiceImpl unifiedSearchService) {
        this.unifiedSearchService = unifiedSearchService;
    }

    @GetMapping("search/email/{email}")
    public ResponseEntity<Object> searchByEmail(@PathVariable String email) {
        Object result = unifiedSearchService.findByEmail(email);
        if (result != null) {
            if (result instanceof Contractor) {
                return ResponseEntity.ok(convertToDto((Contractor) result));
            }
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún cliente o contratista con el email proporcionado");
        }
    }

    private ContractorDto convertToDto(Contractor contractor) {
        return ContractorDto.builder()
                .idContractor(contractor.getIdContractor())
                .nameContractor(contractor.getNameContractor())
                .emailContractor(contractor.getEmailContractor())
                .phoneContractor(contractor.getPhoneContractor())
                .locationContractor(contractor.getLocationContractor())
                .expertiseContractor(contractor.getExpertiseContractor())
                .nitEnterprise(contractor.getEnterprise().getNitEnterprise())
                .build();
    }
}