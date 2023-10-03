package com.drawproject.dev.controller;

import com.drawproject.dev.dto.PostDTO;
import com.drawproject.dev.model.Posts;
import com.drawproject.dev.model.Profile;
import com.drawproject.dev.model.Skill;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.PostRepository;
import com.drawproject.dev.repository.SkillRepository;
import com.drawproject.dev.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Profile displayProfile(HttpSession session) {
        User user = (User) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(user.getUsername());
        profile.setMobileNumber(user.getMobileNum());
        profile.setEmail(user.getEmail());
        if (user.getSkill() != null && user.getSkill().getSkillId() > 0) {
            Skill skill = skillRepository.findBySkillId(user.getSkill().getSkillId());
            profile.setSkill(skill);
        }
        return profile;
    }

    @PostMapping
    public ResponseEntity<String> updateProfile(@Valid @RequestBody Profile profile, Errors errors,HttpSession session){
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }
        User user = (User) session.getAttribute("loggedInPerson");
        user.setUsername(profile.getName());
        user.setEmail(profile.getEmail());
        user.setMobileNum(profile.getMobileNumber());
        user.setAvatar(profile.getAvatar());
        if(user.getSkill() ==null || !(user.getSkill().getSkillId()>0)){
            user.setSkill(new Skill());
        }
        User savedUser = userRepository.save(user);
        session.setAttribute("loggedInPerson", savedUser);
        return new ResponseEntity<>("Update Profile Successful", HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> showPostUser(HttpSession session) {
        User user = (User) session.getAttribute("loggedInPerson");

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
