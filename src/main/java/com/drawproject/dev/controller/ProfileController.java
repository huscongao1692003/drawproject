package com.drawproject.dev.controller;

import com.drawproject.dev.dto.PostDTO;
import com.drawproject.dev.model.*;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.PostRepository;
import com.drawproject.dev.repository.SkillRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.FileService;
import com.drawproject.dev.service.InstructorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    InstructorService instructorService;

    @Autowired
    FileService fileService;

    @GetMapping
    public Profile displayProfile(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        Profile profile = new Profile();
        profile.setFullName(user.getFullName());
        profile.setMobileNumber(user.getMobileNum());
        profile.setEmail(user.getEmail());
        profile.setAvatar(user.getAvatar());
        if (user.getSkill() != null && user.getSkill().getSkillId() > 0) {
            Skill skill = skillRepository.findBySkillId(user.getSkill().getSkillId());
            profile.setSkill(skill.getSkillId());
        }
        return profile;
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateProfile(MultipartFile image, @Valid Profile profile, Errors errors, Authentication authentication){
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }
        Skill skill = skillRepository.findBySkillId(profile.getSkill());
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        user.setFullName(profile.getFullName());
        user.setEmail(profile.getEmail());
        user.setMobileNum(profile.getMobileNumber());
        user.setAvatar(fileService.uploadFile(image, user.getUserId(), "image", "avatars"));
        user.setSkill(skill);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>("Update Profile Successful", HttpStatus.OK);
    }

    @PostMapping("instructor")
    public ResponseEntity<String> updateProfileInstructor(@Valid @RequestBody Instructor instructor, Errors errors, Authentication authentication){
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        Instructor instructor1 = new Instructor();
        instructor1.setInstructorId(user.getUserId());
        instructor1.setBio(instructor.getBio());
        instructor1.setPayment(instructor.getPayment());
        instructor1.setEducation(instructor.getEducation());
        instructorService.saveInstructorRegister(instructor1);
        return new ResponseEntity<>("Update Instructor Successful", HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> showPostUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        // Retrieve posts by user ID from the database
        List<Posts> userPosts = postRepository.findPostsByUserUserId(user.getUserId());

        if (!userPosts.isEmpty()) {
            // Convert the list of Post entities to a list of PostDTOs
            List<PostDTO> postDTOs = userPosts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(postDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
