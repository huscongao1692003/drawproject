package com.drawproject.dev.controller;


import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.service.ArtWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController {

    @Autowired
    ArtWorkService artworkService;

    @GetMapping("/artworks")
    public ResponseEntity<ResponsePagingDTO> getArtworks(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                         @RequestParam(value = "status", required = false) String status) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(artworkService.viewArtWork(page, eachPage, status));
    }

}
