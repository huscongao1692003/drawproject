package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ProgressDTO;
import com.drawproject.dev.repository.LessonRepository;
import com.drawproject.dev.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    LessonRepository lessonRepository;

    public ProgressDTO getProcessOfStudent(int userId, int courseId) {
        ProgressDTO progressDTO = new ProgressDTO();
        progressDTO.setCreatedAt(processRepository.findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonIndex(userId, courseId).getCreatedAt());
        progressDTO.setUpdatedAt(processRepository.findTopByEnrollUserUserIdAndEnrollCourseCourseIdOrderByLessonIndexDesc(userId, courseId).getCreatedAt());

        int progress = processRepository.countByEnrollUserUserIdAndEnrollCourseCourseId(userId, courseId);
        progressDTO.setProgress(progress);
        int numLesson = lessonRepository.countByTopicCourseCourseId(courseId);
        progressDTO.setStatus((progress % numLesson == 0) ? DrawProjectConstaints.COMPELETED : DrawProjectConstaints.IN_PROGRESS);
        return progressDTO;

    }

}
