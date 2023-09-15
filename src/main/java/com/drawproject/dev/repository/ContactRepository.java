package com.drawproject.dev.repository;

import com.drawproject.dev.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findByStatus(String status);
}
