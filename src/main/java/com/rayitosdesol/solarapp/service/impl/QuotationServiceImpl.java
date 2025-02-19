package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.CityDao;
import com.rayitosdesol.solarapp.model.dao.QuotationDao;
import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.City;
import com.rayitosdesol.solarapp.model.entity.Quotation;
import com.rayitosdesol.solarapp.service.IQuotationService;
import com.rayitosdesol.solarapp.util.EmailUtil;

import jakarta.mail.MessagingException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuotationServiceImpl implements IQuotationService {

    private final QuotationDao quotationDao;
    private final CityDao cityDao;
    private final EmailUtil emailUtil;

    public QuotationServiceImpl(QuotationDao quotationDao, CityDao cityDao, EmailUtil emailUtil) {
        this.quotationDao = quotationDao;
        this.cityDao = cityDao;
        this.emailUtil = emailUtil;
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Transactional
    @Override
    public Quotation createQuotation(QuotationRequestDto requestDto) {
        Quotation quotation = new Quotation();

        double costPerSquareMeter = 1000; 
        quotation.setProjectCost(requestDto.getArea() * costPerSquareMeter);

        double efficiency = 0.15;
        quotation.setSystemPower(requestDto.getArea() * efficiency);

        City city = cityDao.findByName(requestDto.getLocation());
        double solarHoursPerDay = city != null ? city.getSolarHoursPerDay() : 5; 

        quotation.setEnergyGeneration(quotation.getSystemPower() * solarHoursPerDay * 30);

        quotation.setMonthlySavings(requestDto.getMonthlyConsumption() * requestDto.getTariff());
        
        Quotation savedQuotation = quotationDao.save(quotation);

        Map<String, Object> model = new HashMap<>();
        model.put("projectCost", df.format(savedQuotation.getProjectCost()));
        model.put("systemPower", df.format(savedQuotation.getSystemPower()));
        model.put("energyGeneration", df.format(savedQuotation.getEnergyGeneration()));
        model.put("monthlySavings", df.format(savedQuotation.getMonthlySavings()));

        try {
            emailUtil.sendQuotationEmail(requestDto.getEmail(), "Detalles de tu cotización de proyecto solar", model);
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send quotation email", e);
        }

        return savedQuotation;
    }
}