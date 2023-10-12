package com.drawproject.dev.service;

import com.drawproject.dev.dto.CollectionDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.Collection;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CollectionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    public ResponseDTO createCollection(HttpSession session, CollectionDTO collectionDTO) {
        User user = (User) session.getAttribute("loggedInPerson");
        System.out.println("user: " + user.getUsername());
        Collection collection = new Collection();
        //set data to collection and save them first
        System.out.println(collection.getCollectionId());
        collection.setUser(user);
        collection.setBio(collectionDTO.getBio());
        collection.setExperience(collectionDTO.getExperience());
        collectionRepository.save(collection);

        return new ResponseDTO(HttpStatus.CREATED, "Collection created successfully", collectionDTO);
    }

}
