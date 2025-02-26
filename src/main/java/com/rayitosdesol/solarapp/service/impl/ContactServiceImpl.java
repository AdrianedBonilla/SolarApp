package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ContactDao;
import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;
import com.rayitosdesol.solarapp.model.entity.Contact;
import com.rayitosdesol.solarapp.service.IContactService;
import com.rayitosdesol.solarapp.util.EmailUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContactServiceImpl implements IContactService {

    private final ContactDao contactDao;
    private final EmailUtil emailUtil;

    public ContactServiceImpl(ContactDao contactDao, EmailUtil emailUtil) {
        this.contactDao = contactDao;
        this.emailUtil = emailUtil;
    }

    @Transactional
    @Override
    public void save(ContactRequestDto requestDto) {
        Contact contact = Contact.builder()
                .nameContact(requestDto.getNameContact())
                .emailContact(requestDto.getEmailContact())
                .messageContact(requestDto.getMessageContact())
                .build();

        contactDao.save(contact);

        // Enviar correo electrónico con los detalles del mensaje de contacto
        Map<String, Object> modelEnterprise = new HashMap<>();
        modelEnterprise.put("nameContact", requestDto.getNameContact());
        modelEnterprise.put("emailContact", requestDto.getEmailContact());
        modelEnterprise.put("messageContact", requestDto.getMessageContact());

        Map<String, Object> modelContact = new HashMap<>();
        modelContact.put("nameContact", requestDto.getNameContact());

        try {
            emailUtil.sendContactEmail("fa.developer.test@gmail.com", "Nuevo Mensaje de Contacto", modelEnterprise);
            emailUtil.sendContactConfirmationEmail(requestDto.getEmailContact(), "Mensaje Recibido", modelContact);
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send contact email", e);
        }
    }
}