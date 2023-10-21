package com.drawproject.dev.service;


import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.dto.user_assignment.StudentWork;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.map.MapUserAssignment;
import com.drawproject.dev.model.UserAssignment;
import com.drawproject.dev.repository.AssignmentRepository;
import com.drawproject.dev.repository.EnrollRepository;
import com.drawproject.dev.repository.UserAssignmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserAssignmentService {

    @Autowired
    UserAssignmentRepository userAssignmentRepository;

    @Autowired
    EnrollRepository enrollRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;

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
            userAssignment.setStatus(DrawProjectConstaints.COMPELETED);
            userAssignmentRepository.save(userAssignment);

            return new ResponseDTO(HttpStatus.OK, "Success grading homework", null);
    }

    public ResponseDTO createStudentWork(MultipartFile requestImage, StudentWork studentWork) {
        UserAssignment userAssignment = new UserAssignment();
        modelMapper.map(studentWork, userAssignment);

        userAssignment.setEnroll(enrollRepository.findById(studentWork.getEnrollId()).orElseThrow());
        userAssignment.setAssignment(assignmentRepository.findById(studentWork.getAssignmentId()).orElseThrow());
        userAssignment.setImage(fileService.uploadFile(requestImage, userAssignment.getEnroll().getUser().getUserId(),
                DrawProjectConstaints.IMAGE, "assignments"));
        userAssignment.setStatus(DrawProjectConstaints.PENDING);

        userAssignmentRepository.save(userAssignment);

        return new ResponseDTO(HttpStatus.CREATED, "Success create student work", "Your homework will be soon reviewed by the instructor!");
    }

    public ResponseDTO updateStudentWork(MultipartFile requestImage, StudentWork studentWork) {
        UserAssignment userAssignment = userAssignmentRepository.findById(studentWork.getTaskId()).orElseThrow();
        modelMapper.map(studentWork, userAssignment);
        userAssignment.setEnroll(enrollRepository.findById(studentWork.getEnrollId()).orElseThrow());
        userAssignment.setAssignment(assignmentRepository.findById(studentWork.getAssignmentId()).orElseThrow());
        if(requestImage != null) {
            userAssignment.setImage(fileService.uploadFile(requestImage, userAssignment.getEnroll().getUser().getUserId(),
                    DrawProjectConstaints.IMAGE, "assignments"));
        }
        userAssignment.setStatus(studentWork.getStatus());

        userAssignmentRepository.save(userAssignment);

        return new ResponseDTO(HttpStatus.OK, "Success update student work", "Your homework will be soon reviewed by the instructor!");

    }

}
