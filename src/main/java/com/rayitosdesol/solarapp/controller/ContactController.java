package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;
import com.rayitosdesol.solarapp.service.IContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

    private final IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public void saveContactMessage(@RequestBody ContactRequestDto requestDto) {
        contactService.save(requestDto);
    }
}