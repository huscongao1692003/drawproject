package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ProgressDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.EnrollRepository;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.ProcessRepository;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EnrollRepository enrollRepository;

    public ProgressDTO getProcessOfStudent(int userId, int courseId) {
        ProgressDTO progressDTO = new ProgressDTO();
        progressDTO.setCreatedAt(processRepository.findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonNumber(userId, courseId).getCreatedAt());
        progressDTO.setUpdatedAt(processRepository.findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonNumberDesc(userId, courseId).getCreatedAt());

        int progress = processRepository.countByEnrollUserUserIdAndEnrollCourseCourseId(userId, courseId);
        progressDTO.setProgress(progress);
        int numLesson = lessonRepository.countByTopicCourseCourseIdAndStatus(courseId, DrawProjectConstaints.OPEN);
        progressDTO.setStatus((progress % numLesson == 0) ? DrawProjectConstaints.COMPLETED : DrawProjectConstaints.IN_PROGRESS);
        return progressDTO;

    }

    public ResponseDTO getProgressStudent(int courseId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        if(!enrollRepository.existsByUserUserIdAndCourseCourseId(user.getUserId(), courseId)) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Student not found", "Not Allowed");
        }
        ProgressDTO progressDTO = getProcessOfStudent(user.getUserId(), courseId);

        return new ResponseDTO(HttpStatus.FOUND, "Progress of student", progressDTO);
    }

}
