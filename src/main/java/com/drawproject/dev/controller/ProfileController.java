package com.drawproject.dev.controller;

import com.drawproject.dev.model.Profile;
import com.drawproject.dev.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @GetMapping("/displayProfile")
    public Profile displayProfile(HttpSession session) {
        User user = (User) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(user.getUsername());
        profile.setMobileNumber(user.getMobileNum());
        profile.setEmail(user.getEmail());
        if (user.getSkill() != null && user.getSkill().getSkillId() > 0) {
            profile.setSkillId(user.getSkill().getSkillId());
        }

        return profile;
    }

}
