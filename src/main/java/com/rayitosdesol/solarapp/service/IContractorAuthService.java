package com.rayitosdesol.solarapp.service;


import com.rayitosdesol.solarapp.model.entity.Contractor;
import java.util.Optional;

public interface IContractorAuthService {
    Optional<Contractor> authenticate(String email, String password, String nitEnterprise);
}