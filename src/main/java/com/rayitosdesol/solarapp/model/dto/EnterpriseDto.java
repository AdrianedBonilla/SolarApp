package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class EnterpriseDto {
    private Long idEnterprise;
    private String nitEnterprise;
    private String nameEnterprise;
}
