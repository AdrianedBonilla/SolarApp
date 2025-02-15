package com.rayitosdesol.solarapp.service;

import com.rayitosdesol.solarapp.model.dto.ClientDto;
import com.rayitosdesol.solarapp.model.entity.Client;

import java.util.List;

public interface IClientService {
    
    List<Client> findAll();
    
    Client findById(Long idClient);
    
    Client findByEmail(String emailClient);
    
    Client save(ClientDto clientDto);
    
    Client update(ClientDto clientDto);
    
    void delete(ClientDto clientDto);

    String encodePassword(String password);

}
