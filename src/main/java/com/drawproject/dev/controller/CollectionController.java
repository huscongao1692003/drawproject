package com.drawproject.dev.controller;

import com.drawproject.dev.dto.CollectionDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.service.CollectionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CollectionController {

    @Autowired
    CollectionService collectionService;


}
