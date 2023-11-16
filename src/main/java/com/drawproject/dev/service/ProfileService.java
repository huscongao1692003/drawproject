package com.drawproject.dev.service;


import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.Contact;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    InstructorRepository instructorRepository;

    public List<User> findInstructor(){
        List<Integer> instructors = instructorRepository.findAll().stream().map(Instructor::getInstructorId).toList();
        return userRepository.findByUserIdInAndStatus(instructors, DrawProjectConstaints.OPEN);
    }

    public User findInstructorById(int userId){
        User instructor = userRepository.findUserByUserId(userId);
        return instructor;
    }




}
