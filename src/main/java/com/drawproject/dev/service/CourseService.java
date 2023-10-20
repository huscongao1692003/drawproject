package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.CourseDTO;
import com.drawproject.dev.dto.course.CourseDetail;
import com.drawproject.dev.dto.course.CoursePreviewDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.model.*;
import com.drawproject.dev.repository.*;
import com.drawproject.dev.ultils.IdUtils;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * The type Course service.
 */
@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StyleRepository styleRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    EnrollRepository enrollRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    FileService fileService;

    /**
     * Gets top course by category.
     *
     * @return top list course preview DTO by category
     */
    public ResponseDTO getTopCourseByCategory(int limit) {
        Map<Integer, List<CoursePreviewDTO>> list = new HashMap<>();

        categoryRepository.findAll().forEach(category ->
                list.put(category.getCategoryId(), MapCourse.mapListToDTO(
                        courseRepository.findTopCourseByCategory
                                (category.getCategoryId(), limit))));

        return new ResponseDTO(HttpStatus.OK, "Course found!", list);
    }

    public ResponsePagingDTO searchCourse(int page, int eachPage, Integer star,
                                    List<Integer> categories, List<Integer> skills, String search) {

        Pageable pageable = PageRequest.of(page - 1, eachPage);


        if (categories == null) {
            categories = categoryRepository.findAll().stream().map(Category::getCategoryId).toList();
        }
        if (skills == null) {
            skills = skillRepository.findAll().stream().map(Skill::getSkillId).toList();
        }
        Page<Courses> courses = courseRepository.searchCourse(categories, skills, search, star, pageable);
        int totalPage = courses.getTotalPages();

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                courses.getTotalElements(), page, courses.getTotalPages(), eachPage, MapCourse.mapListToDTO(courses.getContent()));

        if(!courses.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

    public ResponseDTO saveCourse(HttpSession session, CourseDTO courseDTO) {
        Courses course = new Courses();
        User user = (User) session.getAttribute("loggedInPerson");
        //set instructor
        Instructor instructor = instructorRepository.findById(user.getUserId()).orElseThrow();

        course.setInstructor(instructor);
        course = setProperties(course, courseDTO);
        //set status course
        course.setStatus(DrawProjectConstaints.CLOSE);
        course = courseRepository.save(course);
        //save image
        course.setImage(fileService.uploadFile(courseDTO.getImage(), course.getCourseId(),
                DrawProjectConstaints.IMAGE, "courses"));
        courseRepository.save(course);

        return new ResponseDTO(HttpStatus.CREATED, "Course created successfully", "Your course will be reviewed by us! Please wait for a while.");
    }

    public ResponseDTO updateCourse(CourseDTO courseDTO) {
        Courses course = courseRepository.findById(courseDTO.getCourseId()).orElseThrow();
        course = setProperties(course, courseDTO);
        course.setStatus(courseDTO.getStatus());
        courseRepository.save(course);
        return new ResponseDTO(HttpStatus.OK, "Update Course Successfully", null);
    }

    public Courses setProperties(Courses course, CourseDTO courseDTO) {
        modelMapper.map(courseDTO, course);
        //set category
        Category category = categoryRepository.findById(courseDTO.getCategory()).orElseThrow();
        course.setCategory(category);
        //set style
        Style style = styleRepository.findById(courseDTO.getStyle()).orElseThrow();
        course.setStyle(style);
        //set skill
        Skill skill = skillRepository.findById(courseDTO.getSkill()).orElseThrow();
        course.setSkill(skill);
        //set image
        String fileName = IdUtils.generateCode(courseDTO.getCourseId(), "COURSE");
        course.setImage(fileName);

        return course;
    }

    public ResponseDTO getCourseDetailsById(int courseId) {

        CourseDetail courseDetail = new CourseDetail();
        Courses course = courseRepository.findById(courseId).orElseThrow();
        modelMapper.map(course, courseDetail);

        //set number of student on course
        courseDetail.setNumStudent(enrollRepository.countByCourseCourseIdAndStatus(courseId, DrawProjectConstaints.ENROLL));
        //set number of topic
        courseDetail.setNumQuiz(assignmentRepository.countByLessonTopicCourseCourseId(courseId));

        ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK, "FOUND COURSE", courseDetail);

        return responseDTO;
    }

    public ResponseDTO checkEnroll(int courseId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInPerson");

        //set status buy/enroll
        if(user.getRoles().equals(DrawProjectConstaints.USER_ROLE)) {
            Boolean enroll = enrollRepository.existsByUserUserIdAndCourseCourseId(courseId, user.getUserId());
            if(enroll) {
                return new ResponseDTO(HttpStatus.ACCEPTED, "You have enrolled this course", DrawProjectConstaints.ENROLL);
            } else {
                return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, "You have not enrolled this course", DrawProjectConstaints.UNENROLL);
            }
        } else {
            return new ResponseDTO(HttpStatus.LOCKED, "You just see course at this role", DrawProjectConstaints.VIEW);
        }

    }

    public ResponseDTO deleteCourse(int courseId) {
        Courses course = courseRepository.findById(courseId).orElseThrow();

        course.setStatus(DrawProjectConstaints.CLOSE);

        courseRepository.save(course);

        return new ResponseDTO(HttpStatus.OK, "Delete course successfully", "Your course have been removed!");
    }

    public Object getCoursesByUser(int userId, int page, int eachPage) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Courses> course = courseRepository.findByEnrollsUserUserId(userId, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                course.getTotalElements(), page, course.getTotalPages(), eachPage, MapCourse.mapListToDTO(course.getContent()));

        if(!course.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

    public Object getCoursesByInstructor(int instructorId, int page, int eachPage) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Courses> course = courseRepository.findByInstructorInstructorId(instructorId, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                course.getTotalElements(), page, course.getTotalPages(), eachPage, MapCourse.mapListToDTO(course.getContent()));

        if(!course.isEmpty()) {
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

    public ResponseDTO viewAllCourse(int page, int eachPage) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Courses> courses = courseRepository.findAll(pageable);
        if(courses.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "Course not found", null);
        }
        return new ResponseDTO(HttpStatus.FOUND, "Course found", MapCourse.mapListToDTO(courses.getContent()));
    }

    public ResponseDTO reportCourse(int courseId, String message) {
        Courses course = courseRepository.findById(courseId).orElseThrow();

        course.setStatus(DrawProjectConstaints.CLOSE);

        courseRepository.save(course);

        return new ResponseDTO(HttpStatus.OK, message, DrawProjectConstaints.CLOSE);
    }

    public ResponseDTO openCourse(int courseId, String message) {
        Courses course = courseRepository.findById(courseId).orElseThrow();

        course.setStatus(DrawProjectConstaints.OPEN);

        courseRepository.save(course);

        return new ResponseDTO(HttpStatus.OK, message, DrawProjectConstaints.OPEN);
    }

}
