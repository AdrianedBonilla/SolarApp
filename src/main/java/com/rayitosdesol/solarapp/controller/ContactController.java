package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;
import com.rayitosdesol.solarapp.service.IContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class ContactController {

    private final IContactService contactService;

    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("contact")
    public ResponseEntity<Object> saveContactMessage(@RequestBody ContactRequestDto requestDto) {
        contactService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}