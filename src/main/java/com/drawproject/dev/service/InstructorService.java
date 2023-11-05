package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.dashboard.InstructorDTO;
import com.drawproject.dev.dto.instructor.BestInstructor;
import com.drawproject.dev.dto.instructor.InstructorProfile;
import com.drawproject.dev.map.MapCertificate;
import com.drawproject.dev.model.Certificate;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.*;
import com.drawproject.dev.ultils.JsonUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

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

    public ResponseDTO getDataDashBoard(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        InstructorDTO instructorDTO = new InstructorDTO();
        Object number;
        //get number of student
        number = instructorRepository.countStudentOfInstructor(user.getUserId());
        instructorDTO.setNumOfStudent(number == null ? 0 : (int) number);

        //get star of instructor
        number = instructorRepository.getStarOfInstructor(user.getUserId());
        instructorDTO.setStar(number == null ? 0 : (double) number);

        //get number of course all
        instructorDTO.setNumOfCourse(courseRepository.countByInstructorInstructorId(user.getUserId()));

        //get number of course open
        instructorDTO.setNumOfCourseOpen(courseRepository.countByInstructorInstructorIdAndStatus(user.getUserId(), DrawProjectConstaints.OPEN));

        //get total income
        number = instructorRepository.getTotalIncome(user.getUserId());
        instructorDTO.setTotalIncome(number == null ? 0 : (float) number);

        //set number of course by style
        instructorDTO.setNumOfCourseByStyle(instructorRepository.getNumOfCourseByStyle(user.getUserId()));

        //set number of course by category
        instructorDTO.setNumOfCourseByCategory(instructorRepository.getNumOfCourseByCategory(user.getUserId()));

        //set number of posts
        instructorDTO.setNumOfPost(postRepository.countByUserUserId(user.getUserId()));


        return new ResponseDTO(HttpStatus.OK, "Data dashboard", instructorDTO);
    }

    public ResponseDTO getIncomeFollowMonth(Authentication authentication, int year) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        List<Object[]> list = instructorRepository.getIncomeFollowMonth(user.getUserId(), year);
        List<Long> incomeFollowMonth = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            boolean check = false;
            for(Object[] object : list) {
                if(object[0].equals(i)) {
                    incomeFollowMonth.add(Long.parseLong(object[1].toString()));
                    check = true;
                    break;
                }
            }
            if(!check) incomeFollowMonth.add(0L);
        }
        return new ResponseDTO(HttpStatus.OK, "Income follow month", incomeFollowMonth);
    }

}
