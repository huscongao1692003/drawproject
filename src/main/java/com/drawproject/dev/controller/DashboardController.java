package com.drawproject.dev.controller;

import com.drawproject.dev.dto.DashboardResponseDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> displayDashboard(Authentication authentication, HttpSession session) {
        String username = authentication.getName();

        // Try to find the user by username, and provide a default value if not found
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            session.setAttribute("loggedInPerson",user);
            DashboardResponseDTO response = new DashboardResponseDTO(user.getUsername(), authentication.getAuthorities());
            return ResponseEntity.ok(response);
        }


        // If user or authentication is not present, return an error response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
