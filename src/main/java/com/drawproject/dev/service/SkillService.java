package com.drawproject.dev.service;

import com.drawproject.dev.model.Skill;
import com.drawproject.dev.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    public List<Skill> getAllSkill(){
        List<Skill> skills = skillRepository.findAll();
        return skills;
    }
}
