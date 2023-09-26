package com.drawproject.dev.controller;

import com.drawproject.dev.model.Skill;
import com.drawproject.dev.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    SkillService skillService;

    @GetMapping("/getAllSkill")
    public List<Skill> getAllSkill(){
        return skillService.getAllSkill();
    }
}
