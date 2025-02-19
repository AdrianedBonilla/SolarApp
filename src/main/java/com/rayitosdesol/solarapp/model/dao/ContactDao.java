package com.rayitosdesol.solarapp.model.dao;

import com.rayitosdesol.solarapp.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDao extends JpaRepository<Contact, Long> {
}