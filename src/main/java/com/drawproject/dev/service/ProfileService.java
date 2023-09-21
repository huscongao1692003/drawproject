package com.drawproject.dev.service;


import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.Contact;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    UserRepository userRepository;

    public List<User> findInstructor(){
        List<User> instructor = userRepository.findByRolesName(DrawProjectConstaints.INSTRUCTOR);
        return instructor;
    }



}
