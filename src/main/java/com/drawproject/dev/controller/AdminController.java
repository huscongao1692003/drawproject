package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.UserDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user, userDTO); // Copy properties from User to UserDTO
                    return userDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/disableUser")
    public ResponseEntity<String> disableUser(@RequestParam int userId){
        User user = userRepository.findUserByUserId(userId);
        user.setStatus(DrawProjectConstaints.CLOSE);
        userRepository.save(user);
        return new ResponseEntity<>("User Has Been Disable", HttpStatus.OK);
    }
}
