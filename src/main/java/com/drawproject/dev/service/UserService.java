package com.drawproject.dev.service;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapModel;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;

    public Object getCoursesByUser(int userId, int page, int eachPage) {

        if(!userRepository.existsById(userId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "User not found", null);
        }

        Pageable pageable = PageRequest.of(page - 1, eachPage);

        Page<Courses> course = courseRepository.findByUsersUserId(userId, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                course.getTotalElements(), page, course.getTotalPages(), eachPage, MapModel.mapListToDTO(course.getContent()));

        if(!course.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

}
