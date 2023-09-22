package com.drawproject.dev.controller;

import com.drawproject.dev.dto.InstructorDTO;
import com.drawproject.dev.dto.InstructorDetailDTO;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.User;
import com.drawproject.dev.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/showInstructor")
    public ResponseEntity<List<InstructorDTO>> showInstructor() {
        List<User> users = profileService.findInstructor();

        if (!users.isEmpty()) {
            List<InstructorDTO> instructorDTOS = users.stream()
                    .map(instructor -> {
                        InstructorDTO dto = modelMapper.map(instructor, InstructorDTO.class);
                        dto.setNumberOfCourse(instructor.getCourses().size()); // Set the number of courses
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(instructorDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/showInstructorDetail")
    public ResponseEntity<InstructorDetailDTO> showInstructorDetail(@RequestParam int userId) {
        User instructor = profileService.findInstructorById(userId);

        if (instructor != null) {
            InstructorDetailDTO instructorDetailDTO = modelMapper.map(instructor, InstructorDetailDTO.class);
            return ResponseEntity.ok(instructorDetailDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
