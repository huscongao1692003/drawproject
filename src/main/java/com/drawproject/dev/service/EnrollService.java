package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.model.Enroll;
import com.drawproject.dev.repository.EnrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollService {

    @Autowired
    EnrollRepository enrollRepository;

    public boolean deleteEnroll(int studentId, int courseId) {
        Enroll enroll = enrollRepository.findByUserUserIdAndCourseCourseId(studentId, courseId).orElseThrow();
        enroll.setStatus(DrawProjectConstaints.CLOSE);
        enrollRepository.save(enroll);
        return true;
    }

    public boolean unBannedEnroll(int studentId, int courseId) {
        Enroll enroll = enrollRepository.findByUserUserIdAndCourseCourseId(studentId, courseId).orElseThrow();
        enroll.setStatus(DrawProjectConstaints.ENROLL);
        enrollRepository.save(enroll);
        return true;
    }

}
