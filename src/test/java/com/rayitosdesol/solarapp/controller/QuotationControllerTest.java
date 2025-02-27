package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.Quotation;
import com.rayitosdesol.solarapp.service.IQuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class QuotationControllerTest {

    @Mock
    private IQuotationService quotationService;

    @InjectMocks
    private QuotationController quotationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuotation() {
        QuotationRequestDto requestDto = QuotationRequestDto.builder()
                .area(100)
                .location("City")
                .monthlyConsumption(500)
                .email("test@example.com")
                .build();

        Quotation quotation = Quotation.builder()
                .projectCost(450000000)
                .systemPower(15)
                .energyGeneration(22500)
                .monthlySavings(400000)
                .build();

        when(quotationService.createQuotation(any(QuotationRequestDto.class))).thenReturn(quotation);

        ResponseEntity<Object> response = quotationController.createQuotation(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(quotation, response.getBody());
    }
}