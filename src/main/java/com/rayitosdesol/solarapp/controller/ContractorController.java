package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ContractorController {

    private static final String CONTRACTOR_NOT_FOUND = "Contractor not found";
    private static final String EMAIL_ALREADY_IN_USE = "Email already in use";

    private final IContractorService contractorService;

    public ContractorController(IContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping("contractors")
    public ResponseEntity<Object> getAllContractors() {
        List<Contractor> contractors = contractorService.findAll();
        if (contractors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<ContractorDto> contractorDtos = contractors.stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(contractorDtos);
    }

    @GetMapping("contractor/{id}")
    public ResponseEntity<Object> getContractorById(@PathVariable Long id) {
        Contractor contractor = contractorService.findById(id);
        if (contractor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CONTRACTOR_NOT_FOUND);
        }
        return ResponseEntity.ok(convertToDto(contractor));
    }

    @PostMapping("contractor")
    public ResponseEntity<Object> createContractor(@Valid @RequestBody ContractorDto contractorDto) {
        Contractor contractorSave = contractorService.save(contractorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(contractorSave));
    }

    @PutMapping("contractor/{id}")
    public ResponseEntity<Object> updateContractor(@PathVariable Long id, @Valid @RequestBody ContractorDto contractorDto) {
        Contractor existingContractor = contractorService.findById(id);
        if (existingContractor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CONTRACTOR_NOT_FOUND);
        }

        Contractor contractorWithSameEmail = contractorService.findByEmail(contractorDto.getEmailContractor());
        if (contractorWithSameEmail != null && !contractorWithSameEmail.getIdContractor().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(EMAIL_ALREADY_IN_USE);
        }

        contractorDto.setIdContractor(id);
        Contractor updatedContractor = contractorService.update(contractorDto);
        return ResponseEntity.ok(convertToDto(updatedContractor));
    }

    @DeleteMapping("contractor/{id}")
    public ResponseEntity<Object> deleteContractor(@PathVariable Long id) {
        Contractor contractorDelete = contractorService.findById(id);
        if (contractorDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CONTRACTOR_NOT_FOUND);
        }
        contractorService.delete(contractorDelete);
        return ResponseEntity.noContent().build();
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