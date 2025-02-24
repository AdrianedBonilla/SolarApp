package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubsidyDto {
    private Long id;
    private String level;
    private Long clientId;
    private boolean lowIncome;
    private boolean singleParent;
    private boolean displaced;
    private boolean disabled;
    private boolean elderly;
    private boolean limitedAccessToServices;
    private boolean inadequateHousing;
}