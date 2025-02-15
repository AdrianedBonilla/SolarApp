package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString 
public class ContractorDto {
    private Long idContractor;
    private String nameContractor;
    private String emailContractor;
    private String phoneContractor;
    private String locationContractor;
    private String expertiseContractor;
    private String passwordContractor;
    private String nitEnterprise;
}