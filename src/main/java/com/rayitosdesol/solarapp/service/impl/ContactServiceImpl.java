package com.rayitosdesol.solarapp.service.impl;

import com.rayitosdesol.solarapp.model.dao.ContactDao;
import com.rayitosdesol.solarapp.model.dto.ContactRequestDto;
import com.rayitosdesol.solarapp.model.entity.Contact;
import com.rayitosdesol.solarapp.service.IContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl implements IContactService {

    private final ContactDao contactDao;

    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
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
    }
}