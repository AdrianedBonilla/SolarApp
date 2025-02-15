package com.rayitosdesol.solarapp.service;

import java.util.Optional;

import com.rayitosdesol.solarapp.model.entity.Client;

public interface IClienAuthtService {
    Optional<Client> authenticate(String email, String password);
}
