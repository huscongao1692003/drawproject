package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ProcessDTO;
import com.drawproject.dev.dto.ProgressDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.Enroll;
import com.drawproject.dev.model.Lesson;
import com.drawproject.dev.model.Process;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.EnrollRepository;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.ProcessRepository;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Enroll enroll = enrollRepository.findByUserUserIdAndCourseCourseId(userId, courseId).orElseThrow();
        Long progress = processRepository.countByEnroll_EnrollIdAndLesson_Topic_Course_CourseIdAndLesson_Status(enroll.getEnrollId(), courseId, DrawProjectConstaints.OPEN);
        progressDTO.setProgress(progress);
        int numLesson = lessonRepository.countByTopicCourseCourseIdAndStatus(courseId, DrawProjectConstaints.OPEN);
        progressDTO.setStatus((progress % numLesson == 0) ? DrawProjectConstaints.COMPLETED : DrawProjectConstaints.IN_PROGRESS);
        return progressDTO;

    }

    public ResponseDTO getProgressStudent(int courseId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Enroll enroll = enrollRepository.findByUserUserIdAndCourseCourseId(user.getUserId(), courseId).orElseThrow();
        List<Lesson> lessons = lessonRepository.findByProcessesEnrollEnrollIdAndTopicCourseCourseIdAndStatus(enroll.getEnrollId(), courseId, DrawProjectConstaints.OPEN);
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setLessons(lessons.stream().map(Lesson::getLessonId).toList());
        processDTO.setProgressDTO(getProcessOfStudent(user.getUserId(), courseId));

        return new ResponseDTO(HttpStatus.FOUND, "Progress of student", processDTO);
    }

    public ResponseDTO recordProgressStudy(Authentication authentication, int courseId, int lessonId) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Enroll enroll = enrollRepository.findByUserUserIdAndCourseCourseId(user.getUserId(), courseId).orElseThrow();
        Process process = processRepository.findByEnrollEnrollIdAndLessonLessonId(enroll.getEnrollId(), lessonId);
        if(process != null) {
            return new ResponseDTO(HttpStatus.NOT_MODIFIED, "Record progress study", "You have completed this lesson");
        }
        process = new Process();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        process.setEnroll(enroll);
        process.setLesson(lesson);
        process.setStatus(DrawProjectConstaints.IN_PROGRESS);
        processRepository.save(process);

        return new ResponseDTO(HttpStatus.OK, "Record progress study", "Congratulation! You have completed the lesson");
    }

}
