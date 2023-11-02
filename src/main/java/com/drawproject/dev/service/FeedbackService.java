package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.FeedbackDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapFeedback;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Feedback;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.FeedbackRepository;
import com.drawproject.dev.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;


    public ResponsePagingDTO getFeedback(int courseId, String status, int page, int eachPage) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Feedback> feedbacks;
        if(status.equalsIgnoreCase("")) {
            feedbacks = feedbackRepository.findByCoursesCourseId(courseId, pageable);
        } else {
            feedbacks = feedbackRepository.findByCoursesCourseIdAndStatus(courseId, status, pageable);
        }

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                feedbacks.getTotalElements(), page, feedbacks.getTotalPages(), eachPage, MapFeedback.mapListFeedbackToDTO(feedbacks.getContent()));

        if (!feedbacks.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.FOUND);
        }

        return responsePagingDTO;
    }

    public ResponseDTO updateFeedback(int courseId, FeedbackDTO feedbackDTO) {
        if(!courseRepository.existsById(courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", null);
        }

        Feedback feedback = feedbackRepository.findById(feedbackDTO.getFeedbackId()).orElseThrow();
        feedback.setFeedbackInformation(feedbackDTO.getFeedbackInformation());
        feedback.setStar(feedbackDTO.getStar());
        feedbackRepository.save(feedback);
        return new ResponseDTO(HttpStatus.OK, "Feedback updated", MapFeedback.mapFeedbackToDTO(feedback));
    }

    public ResponseDTO createFeedback(int courseId, FeedbackDTO feedbackDTO) {
        //get information about course wanting feedback
        Courses course = courseRepository.findById(courseId).orElseThrow();
        //get user feedback
        User user = userRepository.findById(feedbackDTO.getUserId()).orElseThrow();

        Feedback feedback = new Feedback();
        modelMapper.map(feedbackDTO, feedback);
        feedback.setCourses(course);
        feedback.setUser(user);
        feedback.setStatus(DrawProjectConstaints.OPEN);

        feedbackRepository.save(feedback);
        return new ResponseDTO(HttpStatus.CREATED, "Feeedback created", MapFeedback.mapFeedbackToDTO(feedback));
    }

    public ResponseDTO deleteFeedback(int courseId, int feedbackId) {
        if(!courseRepository.existsById(courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", null);
        }
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow();
        feedback.setStatus(DrawProjectConstaints.CLOSE);
        return new ResponseDTO(HttpStatus.OK, "Feedback deleted", MapFeedback.mapFeedbackToDTO(feedback));
    }

    public ResponseDTO checkFeedback(Authentication authentication, int courseId) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Boolean check = feedbackRepository.existsByUser_UserIdAndCourses_CourseIdAndStatus(user.getUserId(), courseId, DrawProjectConstaints.OPEN);

        if(check) {
            return new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED, "You have been feedback for this course", true);
        }

        return new ResponseDTO(HttpStatus.OK, "Feedback not found", false);

    }

}
