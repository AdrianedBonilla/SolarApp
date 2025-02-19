package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.Quotation;
import com.rayitosdesol.solarapp.service.IQuotationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class QuotationController {

    private final IQuotationService quotationService;

    public QuotationController(IQuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("quotations")
    public Quotation createQuotation(@RequestBody QuotationRequestDto requestDto) {
        return quotationService.createQuotation(requestDto);
    }
}