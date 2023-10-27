package com.drawproject.dev.service;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.UserCourseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapUser;
import com.drawproject.dev.model.Enroll;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.EnrollRepository;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    CourseRepository coursesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EnrollRepository enrollRepository;

    @Autowired
    ProcessService processService;

    public Object getStudentEnrollCourse(int courseId, int page, int eachPage) {

        if(!coursesRepository.existsById(courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", null);
        }

        Pageable pageable = PageRequest.of(page - 1, eachPage);

        Page<User> users = userRepository.findByEnrollsCourseCourseId(courseId, pageable);

        List<UserCourseDTO> list = MapUser.mapUserCourseToDTOs(users.getContent());
        //set process
        list.forEach(userCourseDTO -> userCourseDTO.setProgressDTO(processService.getProcessOfStudent(userCourseDTO.getUserID(), courseId)));

        long total = users.getTotalElements();
        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                users.getTotalElements(), page, users.getTotalPages(), eachPage, list);

        if(total > 0) {
            responsePagingDTO.setStatus(HttpStatus.OK);
            responsePagingDTO.setMessage("Found user");
        }

        return responsePagingDTO;

    }

    public ResponseDTO getImageUser(int userId) {
        String image = userRepository.findById(userId).orElseThrow().getAvatar();
        return new ResponseDTO(HttpStatus.OK, "Found user", image);
    }

}
