package com.rayitosdesol.solarapp.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ContactRequestDto {
    private String nameContact;
    private String emailContact;
    private String messageContact;
}