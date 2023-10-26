package com.drawproject.dev.controller;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/style")
public class StyleController {

    @Autowired
    StyleService styleService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAllStyle() {
        return ResponseEntity.ok(styleService.getAllStyle());
    }

}
