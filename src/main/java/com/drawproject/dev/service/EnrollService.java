package com.drawproject.dev.service;

import com.drawproject.dev.model.Enroll;
import com.drawproject.dev.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollService {

    @Autowired
    EnrollRepository enrollRepository;


}
