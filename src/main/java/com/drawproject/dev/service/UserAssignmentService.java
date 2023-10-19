package com.drawproject.dev.service;


import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.map.MapUserAssignment;
import com.drawproject.dev.model.UserAssignment;
import com.drawproject.dev.repository.UserAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserAssignmentService {

    @Autowired
    UserAssignmentRepository userAssignmentRepository;

    public ResponsePagingDTO getSubmissions(int page, int eachPage, String status, int intructorId) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);

        Page<UserAssignment> userAssignments = userAssignmentRepository.findByStatusAndEnrollCourseInstructorInstructorIdOrderByCreatedAt(status, intructorId, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Found submission of each courses",
                userAssignments.getTotalElements(), page, userAssignments.getTotalPages(), eachPage, MapUserAssignment.mapToSubmissionDTOs(userAssignments.getContent()));

        if(userAssignments.isEmpty()) {
            responsePagingDTO.setStatus(HttpStatus.NOT_FOUND);
            responsePagingDTO.setMessage("Not found submission of each courses");
        }

        return responsePagingDTO;
    }

    public ResponseDTO getStudentWork(int taskId) {

        UserAssignment userAssignment = userAssignmentRepository.findById(taskId).orElseThrow();

        return new ResponseDTO(HttpStatus.FOUND, "Found student work", MapUserAssignment.mapToStudentWork(userAssignment));
    }

    public ResponseDTO gradeHomeWork(int taskId, int grade, String comment) {

            UserAssignment userAssignment = userAssignmentRepository.findById(taskId).orElseThrow();
            userAssignment.setGrade(grade);
            userAssignment.setComment(comment);
            userAssignmentRepository.save(userAssignment);

            return new ResponseDTO(HttpStatus.OK, "Success grading homework", null);
    }

}
