package com.drawproject.dev.controller;

import com.drawproject.dev.dto.InstructorDTO;
import com.drawproject.dev.dto.InstructorDetailDTO;
import com.drawproject.dev.dto.OrderAdminDTO;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.model.Orders;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.OrderService;
import com.drawproject.dev.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/instructor")
public class InstructorController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProfileService profileService;

    @Autowired
    OrderService orderService;

   @Autowired
   ModelMapper modelMapper;

   @Autowired
    InstructorRepository instructorRepository;

    @GetMapping
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
        User instructor = userRepository.findUserByUserId(userId);

        if (instructor != null) {
            InstructorDetailDTO instructorDetailDTO = modelMapper.map(instructor, InstructorDetailDTO.class);
            instructorDetailDTO.setInstructorId(userId);
            return ResponseEntity.ok(instructorDetailDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
