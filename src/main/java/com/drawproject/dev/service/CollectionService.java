package com.drawproject.dev.service;

import com.drawproject.dev.dto.CollectionDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CollectionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository collectionRepository;



}
