package com.rayitosdesol.solarapp.service;

import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;

public interface IContactService {
    void save(ContactRequestDto requestDto);
}