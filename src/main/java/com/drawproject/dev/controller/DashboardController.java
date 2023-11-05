package com.drawproject.dev.controller;

import com.drawproject.dev.dto.DashboardResponseDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.InstructorService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Slf4j
public class DashboardController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstructorService instructorService;

    @Autowired
    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> displayDashboard(Authentication authentication) {
        String username = authentication.getName();

        // Try to find the user by username, and provide a default value if not found
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            DashboardResponseDTO response = new DashboardResponseDTO(user.getUsername(), authentication.getAuthorities());
            return ResponseEntity.ok(response);
        }


        // If user or authentication is not present, return an error response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/instructor")
    public ResponseEntity<ResponseDTO> getNumOfStudents(Authentication authentication) {
        return ResponseEntity.ok().body(instructorService.getDataDashBoard(authentication));
    }

    @GetMapping("/instructor/income-month")
    public ResponseEntity<ResponseDTO> getIncomeFollowMonth(Authentication authentication, int year) {
        return ResponseEntity.ok().body(instructorService.getIncomeFollowMonth(authentication, year));
    }

}
