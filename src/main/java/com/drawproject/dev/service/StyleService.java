package com.drawproject.dev.service;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.repository.StyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StyleService {

    @Autowired
    StyleRepository styleRepository;

    public ResponseDTO getAllStyle() {
        return new ResponseDTO(HttpStatus.FOUND, "Style found", styleRepository.findAll());
    }

}
