package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ClientDto {
    private Long idClient;
    private String emailClient;
    private String passwordClient;
    private String nameClient;
    private String phoneClient;
    private String cityClient;
    private String neighborhoodClient;
    private Double monthlyConsumptionClient;
    private String installationTypeClient;
    private String siteConditionsClient;
    private Long contractorId;

}