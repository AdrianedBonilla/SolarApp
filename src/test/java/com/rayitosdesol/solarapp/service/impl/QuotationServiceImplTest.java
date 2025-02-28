package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.exception.EmailSendingException;
import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dao.DepartmentDao;
import com.rayitosdesol.solarapp.model.dao.QuotationDao;
import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Department;
import com.rayitosdesol.solarapp.model.entity.Quotation;
import com.rayitosdesol.solarapp.util.EmailUtil;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuotationServiceImplTest {

    @Mock
    private QuotationDao quotationDao;

    @Mock
    private DepartmentDao cityDao;

    @Mock
    private ContractorDao contractorDao;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private QuotationServiceImpl quotationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuotation() throws MessagingException, TemplateException, IOException {
        QuotationRequestDto requestDto = QuotationRequestDto.builder()
                .area(100)
                .location("City")
                .monthlyConsumption(500)
                .email("test@example.com")
                .build();

        Department city = Department.builder()
                .name("City")
                .solarHoursPerDay(5)
                .kWhValue(800)
                .build();

        Contractor contractor = Contractor.builder()
                .idContractor(1L)
                .nameContractor("Contractor")
                .build();

        Quotation quotation = Quotation.builder()
                .projectCost(450000000)
                .systemPower(15)
                .energyGeneration(22500)
                .monthlySavings(400000)
                .contractor(contractor)
                .build();

        when(cityDao.findByName(any(String.class))).thenReturn(city);
        when(contractorDao.findById(any(Long.class))).thenReturn(Optional.of(contractor));
        when(quotationDao.save(any(Quotation.class))).thenReturn(quotation);

        Quotation result = quotationService.createQuotation(requestDto);

        assertEquals(450000000, result.getProjectCost());
        assertEquals(15, result.getSystemPower());
        assertEquals(22500, result.getEnergyGeneration());
        assertEquals(400000, result.getMonthlySavings());

        verify(emailUtil, times(1)).sendQuotationEmail(anyString(), anyString(), anyMap());
    }

    @Test
    void testCreateQuotationContractorNotFound() {
        QuotationRequestDto requestDto = QuotationRequestDto.builder()
                .area(100)
                .location("City")
                .monthlyConsumption(500)
                .email("test@example.com")
                .build();

        when(contractorDao.findById(any(Long.class))).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quotationService.createQuotation(requestDto);
        });

        assertEquals("Contractor not found", exception.getMessage());
    }

    @Test
    void testCreateQuotationEmailSendingException() throws MessagingException, TemplateException, IOException {
        QuotationRequestDto requestDto = QuotationRequestDto.builder()
                .area(100)
                .location("City")
                .monthlyConsumption(500)
                .email("test@example.com")
                .build();

        Department city = Department.builder()
                .name("City")
                .solarHoursPerDay(5)
                .kWhValue(800)
                .build();

        Contractor contractor = Contractor.builder()
                .idContractor(1L)
                .nameContractor("Contractor")
                .build();

        Quotation quotation = Quotation.builder()
                .projectCost(450000000)
                .systemPower(15)
                .energyGeneration(22500)
                .monthlySavings(400000)
                .contractor(contractor)
                .build();

        when(cityDao.findByName(any(String.class))).thenReturn(city);
        when(contractorDao.findById(any(Long.class))).thenReturn(Optional.of(contractor));
        when(quotationDao.save(any(Quotation.class))).thenReturn(quotation);
        doThrow(new MessagingException("Failed to send email")).when(emailUtil).sendQuotationEmail(anyString(), anyString(), anyMap());

        EmailSendingException exception = assertThrows(EmailSendingException.class, () -> {
            quotationService.createQuotation(requestDto);
        });

        assertEquals("Failed to send quotation email", exception.getMessage());
    }
}