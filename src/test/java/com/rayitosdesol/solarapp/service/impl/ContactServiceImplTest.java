package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.exception.EmailSendingException;
import com.rayitosdesol.solarapp.model.dao.ContactDao;
import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;
import com.rayitosdesol.solarapp.model.entity.Contact;
import com.rayitosdesol.solarapp.util.EmailUtil;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactServiceImplTest {

    @Mock
    private ContactDao contactDao;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() throws MessagingException, TemplateException, IOException {
        ContactRequestDto requestDto = ContactRequestDto.builder()
                .nameContact("John Doe")
                .emailContact("john.doe@example.com")
                .messageContact("Hello")
                .build();

        Contact contact = Contact.builder()
                .nameContact("John Doe")
                .emailContact("john.doe@example.com")
                .messageContact("Hello")
                .build();

        when(contactDao.save(any(Contact.class))).thenReturn(contact);

        contactService.save(requestDto);

        verify(contactDao, times(1)).save(any(Contact.class));
        verify(emailUtil, times(1)).sendContactEmail(anyString(), anyString(), anyMap());
        verify(emailUtil, times(1)).sendContactConfirmationEmail(anyString(), anyString(), anyMap());
    }

    @Test
    void testSaveEmailSendingException() throws MessagingException, TemplateException, IOException {
        ContactRequestDto requestDto = ContactRequestDto.builder()
                .nameContact("John Doe")
                .emailContact("john.doe@example.com")
                .messageContact("Hello")
                .build();

        Contact contact = Contact.builder()
                .nameContact("John Doe")
                .emailContact("john.doe@example.com")
                .messageContact("Hello")
                .build();

        when(contactDao.save(any(Contact.class))).thenReturn(contact);
        doThrow(new MessagingException("Failed to send email")).when(emailUtil).sendContactEmail(anyString(), anyString(), anyMap());

        EmailSendingException exception = assertThrows(EmailSendingException.class, () -> {
            contactService.save(requestDto);
        });

        assertEquals("Failed to send contact email", exception.getMessage());
    }
}