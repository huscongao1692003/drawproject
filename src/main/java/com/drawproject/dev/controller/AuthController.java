package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.AuthResponseDTO;
import com.drawproject.dev.dto.LoginDTO;
import com.drawproject.dev.dto.RegisterDTO;
import com.drawproject.dev.exceptions.ResourceNotFoundException;
import com.drawproject.dev.model.Roles;
import com.drawproject.dev.model.Skills;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.RoleRepository;
import com.drawproject.dev.repository.SkillRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.security.JWTGenerator;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPwd()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDto, Errors errors) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        if(errors.hasErrors()){
            return new ResponseEntity<>("Check Validation", HttpStatus.BAD_REQUEST);
        }
        if (!registerDto.getPwd().equals(registerDto.getConfirmPwd())) {
            return new ResponseEntity<>("Password and Confirm Password do not match", HttpStatus.BAD_REQUEST);
        }

        if (!registerDto.getEmail().equals(registerDto.getConfirmEmail())) {
            return new ResponseEntity<>("Email and Confirm Email do not match", HttpStatus.BAD_REQUEST);
        }else {
            Skills skill = skillRepository.findById(registerDto.getSkillId())
                    .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setPwd(passwordEncoder.encode((registerDto.getPwd())));
            user.setEmail(registerDto.getEmail());
            user.setMobileNum(registerDto.getMobileNum());
            user.setStatus(DrawProjectConstaints.OPEN);
            user.setSkill(skill);
            Roles roles = roleRepository.findByName("USER").get();
            user.setRoles(roles);

            userRepository.save(user);

            return new ResponseEntity<>("User registered success!", HttpStatus.OK);
        }
    }
}