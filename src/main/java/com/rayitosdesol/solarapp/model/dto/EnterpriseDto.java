package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnterpriseDto {
    private Long idEnterprise;
    private String nitEnterprise;
    private String nameEnterprise;
}
