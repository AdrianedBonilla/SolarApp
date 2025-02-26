package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.service.impl.UnifiedSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UnifiedSearchController {

    private final UnifiedSearchService unifiedSearchService;

    public UnifiedSearchController(UnifiedSearchService unifiedSearchService) {
        this.unifiedSearchService = unifiedSearchService;
    }

    @GetMapping("search/email/{email}")
    public ResponseEntity<Object> searchByEmail(@PathVariable String email) {
        Object result = unifiedSearchService.findByEmail(email);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún cliente o contratista con el email proporcionado");
        }
    }
}