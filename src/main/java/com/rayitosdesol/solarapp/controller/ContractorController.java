package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContractorDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.service.IContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/")
public class ContractorController {

    private final IContractorService contractorService;

    @Autowired
    public ContractorController(IContractorService contractorService) {
        this.contractorService = contractorService;
    }

   @GetMapping("contractors")
    public ResponseEntity<List<ContractorDto>> getAllContractors() {
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
    public ResponseEntity<ContractorDto> getContractorById(@PathVariable Long id) {
        Contractor contractor = contractorService.findById(id);
        if (contractor == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(contractor));
    }

    @PostMapping("contractor")
    public ResponseEntity<ContractorDto> createContractor(@Valid @RequestBody ContractorDto contractorDto) {
        Contractor contractorSave = contractorService.save(contractorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(contractorSave));
    }

    @PutMapping("contractor/{id}")
    public ResponseEntity<ContractorDto> updateContractor(@PathVariable Long id, @Valid @RequestBody ContractorDto contractorDto) {
        Contractor existingContractor = contractorService.findById(id);
        if (existingContractor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Contractor contractorWithSameEmail = contractorService.findByEmail(contractorDto.getEmailContractor());
        if (contractorWithSameEmail != null && !contractorWithSameEmail.getIdContractor().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        contractorDto.setIdContractor(id);
        Contractor updatedContractor = contractorService.update(contractorDto);
        return ResponseEntity.ok(convertToDto(updatedContractor));
    }

    @DeleteMapping("contractor/{id}")
    public ResponseEntity<Map<String, Object>> deleteContractor(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Contractor contractorDelete = contractorService.findById(id);
            if (contractorDelete == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            contractorService.delete(contractorDelete);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
