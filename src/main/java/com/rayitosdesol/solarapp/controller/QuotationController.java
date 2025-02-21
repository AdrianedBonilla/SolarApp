package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.Quotation;
import com.rayitosdesol.solarapp.service.IQuotationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class QuotationController {

    private final IQuotationService quotationService;

    public QuotationController(IQuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("quotations")
    public ResponseEntity<Object> createQuotation(@Valid @RequestBody QuotationRequestDto requestDto) {
        Quotation quotation = quotationService.createQuotation(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quotation);
    }
}