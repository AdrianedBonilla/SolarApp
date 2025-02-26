package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ContractorDao;
import com.rayitosdesol.solarapp.model.dao.DepartmentDao;
import com.rayitosdesol.solarapp.model.dao.QuotationDao;
import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.Contractor;
import com.rayitosdesol.solarapp.model.entity.Department;
import com.rayitosdesol.solarapp.model.entity.Quotation;
import com.rayitosdesol.solarapp.service.IQuotationService;
import com.rayitosdesol.solarapp.util.EmailUtil;

import jakarta.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class QuotationServiceImpl implements IQuotationService {

    private final QuotationDao quotationDao;
    private final DepartmentDao cityDao;
    private final ContractorDao contractorDao;
    private final EmailUtil emailUtil;

    public QuotationServiceImpl(QuotationDao quotationDao, DepartmentDao cityDao, ContractorDao contractorDao, EmailUtil emailUtil) {
        this.quotationDao = quotationDao;
        this.cityDao = cityDao;
        this.contractorDao = contractorDao;
        this.emailUtil = emailUtil;
    }

    @Transactional
    @Override
    public Quotation createQuotation(QuotationRequestDto requestDto) {
        Quotation quotation = new Quotation();

        double costPerSquareMeter = 4500000; 
        quotation.setProjectCost(requestDto.getArea() * costPerSquareMeter);

        double efficiency = 0.15;
        quotation.setSystemPower(requestDto.getArea() * efficiency);

        Department city = cityDao.findByName(requestDto.getLocation());
        double solarHoursPerDay = city != null ? city.getSolarHoursPerDay() : 5; 
        double kWhValue = city != null ? city.getKWhValue() : 800;

        quotation.setEnergyGeneration(quotation.getSystemPower() * solarHoursPerDay * 30);

        quotation.setMonthlySavings(requestDto.getMonthlyConsumption() * kWhValue); 

        Contractor contractor = contractorDao.findById(1L).orElseThrow(() -> new RuntimeException("Contractor not found"));
        quotation.setContractor(contractor);
        
        Quotation savedQuotation = quotationDao.save(quotation);

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("es", "CO"));
        String formattedProjectCost = numberFormat.format(savedQuotation.getProjectCost());
        String formattedSystemPower = numberFormat.format(savedQuotation.getSystemPower());
        String formattedEnergyGeneration = numberFormat.format(savedQuotation.getEnergyGeneration());
        String formattedMonthlySavings = numberFormat.format(savedQuotation.getMonthlySavings());

        Map<String, Object> model = new HashMap<>();
        model.put("projectCost", formattedProjectCost);
        model.put("systemPower", formattedSystemPower);
        model.put("energyGeneration", formattedEnergyGeneration);
        model.put("monthlySavings", formattedMonthlySavings);
        model.put("contractorName", contractor.getNameContractor());
        model.put("projectLocation", requestDto.getLocation());
        model.put("quotationDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        try {
            emailUtil.sendQuotationEmail(requestDto.getEmail(), "Detalles de tu cotización de proyecto solar", model);
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send quotation email", e);
        }

        return savedQuotation;
    }
}