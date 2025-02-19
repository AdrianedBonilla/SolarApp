package com.rayitosdesol.solarapp.service;

import com.rayitosdesol.solarapp.model.dto.QuotationRequestDto;
import com.rayitosdesol.solarapp.model.entity.Quotation;

public interface IQuotationService {
    Quotation createQuotation(QuotationRequestDto requestDto);
}
