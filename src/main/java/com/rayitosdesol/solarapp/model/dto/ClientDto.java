package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private Long idClient;
    private String emailClient;
    private String passwordClient;
    private String nameClient;
    private String phoneClient;
    private String cityClient;
    private String neighborhoodClient;
    private Integer monthlyConsumptionClient;
    private String installationTypeClient;
    private Long contractorId;
    private String subsidyLevel;
    private boolean lowIncome;
    private boolean singleParent;
    private boolean displaced;
    private boolean disabled;
    private boolean elderly;
    private boolean limitedAccessToServices;
    private boolean inadequateHousing;
}