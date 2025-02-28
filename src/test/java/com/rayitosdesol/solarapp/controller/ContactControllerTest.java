package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;
import com.rayitosdesol.solarapp.service.IContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class ContactControllerTest {

    @Mock
    private IContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveContactMessage() {
        ContactRequestDto requestDto = ContactRequestDto.builder()
                .nameContact("John Doe")
                .emailContact("john.doe@example.com")
                .messageContact("Hello")
                .build();

        doNothing().when(contactService).save(requestDto);

        ResponseEntity<Object> response = contactController.saveContactMessage(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(contactService).save(requestDto);
    }
}