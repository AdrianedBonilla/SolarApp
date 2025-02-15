package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IContractorAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/contractors")
public class ContractorAuthController {

    private final IContractorAuthService contractorAuthService;

    public ContractorAuthController(IContractorAuthService contractorAuthService) {
        this.contractorAuthService = contractorAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ContractorDto contractorDto) {
        Optional<Contractor> contractor = contractorAuthService.authenticate(
                contractorDto.getEmailContractor(), 
                contractorDto.getPasswordContractor(), 
                contractorDto.getNitEnterprise()
        );

        if (contractor.isPresent()) {
            return ResponseEntity.ok(convertToDto(contractor.get()));
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas o empresa no registrada");
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
