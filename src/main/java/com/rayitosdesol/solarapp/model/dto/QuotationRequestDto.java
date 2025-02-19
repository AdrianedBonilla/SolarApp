package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class QuotationRequestDto {
    private String projectType;
    private String location;
    private double monthlyConsumption;
    private double tariff;
    private String roofType;
    private double area;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


}
