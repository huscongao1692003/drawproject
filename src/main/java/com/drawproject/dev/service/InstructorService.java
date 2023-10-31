package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.instructor.BestInstructor;
import com.drawproject.dev.dto.instructor.InstructorProfile;
import com.drawproject.dev.map.MapCertificate;
import com.drawproject.dev.model.Certificate;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.repository.CertificateRepository;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.StyleRepository;
import com.drawproject.dev.ultils.JsonUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StyleRepository styleRepository;

    @Autowired
    CourseRepository courseRepository;

    public Instructor saveInstructorRegister(Instructor instructor) {
         return  instructorRepository.save(instructor);
    }

    public ResponseDTO updateInstructor(InstructorProfile instructorProfile) {
        Instructor instructor = instructorRepository.findById(instructorProfile.getInstructorId()).orElseThrow();
        modelMapper.map(instructorProfile, instructor);
        instructor.setExperiences(styleRepository.findByDrawingStyleIdIn(instructorProfile.getStyles()));
        instructorRepository.save(instructor);
        return new ResponseDTO(HttpStatus.OK, "Instructor updated successfully", null);
    }

    public ResponseDTO getExperiences(int instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow();
        return new ResponseDTO(HttpStatus.OK, "Instructor updated successfully", instructor.getExperiences());
    }

    public int getNumOfCourses(int instructorId) {
        return courseRepository.countByInstructorInstructorIdAndStatus(instructorId, DrawProjectConstaints.OPEN);
    }

    public ResponseDTO topInstructor() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Object[]> bestInstructors = instructorRepository.findTopInstructors();
        return new ResponseDTO(HttpStatus.FOUND, "Top instructors", JsonUtils.objectToListBestInstructor(bestInstructors));
    }
}
