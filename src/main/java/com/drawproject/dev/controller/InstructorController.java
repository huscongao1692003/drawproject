package com.drawproject.dev.controller;

import com.drawproject.dev.dto.InstructorDTO;
import com.drawproject.dev.dto.InstructorDetailDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.service.InstructorService;
import com.drawproject.dev.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/instructor")
public class InstructorController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    InstructorService instructorService;

    @GetMapping("")
    public ResponseEntity<List<InstructorDTO>> showInstructor() {
        List<User> users = profileService.findInstructor();

        if (!users.isEmpty()) {
            List<InstructorDTO> instructorDTOS = users.stream()
                    .map(instructor -> {
                        InstructorDTO dto = modelMapper.map(instructor, InstructorDTO.class);
                        dto.setNumberOfCourse(instructor.getEnrolls().size()); // Set the number of courses
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(instructorDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<InstructorDetailDTO> showInstructorDetail(@PathVariable int userId) {
        User instructor = profileService.findInstructorById(userId);

        if (instructor != null) {
            InstructorDetailDTO instructorDetailDTO = modelMapper.map(instructor, InstructorDetailDTO.class);
            return ResponseEntity.ok(instructorDetailDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/certificates")
    public ResponseEntity<ResponseDTO> getCertificates(@PathVariable("userId") int userId) {
        return ResponseEntity.ok().body(instructorService.getCertificates(userId));
    }

}
