package com.rayitosdesol.solarapp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private Long idClient;
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String emailClient;

    @NotBlank(message = "El nombre es obligatorio")
    private String nameClient;

    @NotBlank(message = "El teléfono es obligatorio")
    private String phoneClient;

    @NotBlank(message = "La ciudad es obligatoria")
    private String cityClient;

    @NotBlank(message = "El barrio es obligatorio")
    private String neighborhoodClient;

    private Integer monthlyConsumptionClient;

    @NotBlank(message = "El tipo de instalación es obligatorio")
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