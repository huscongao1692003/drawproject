package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.Mail;
import com.drawproject.dev.dto.QuickSearchData;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.*;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.model.*;
import com.drawproject.dev.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    @Autowired
    MailService mailService;

    @Autowired
    LessonService lessonService;


    @Autowired
    LessonRepository lessonRepository;

    /**
     * Gets top course by category.
     *
     * @return top list course preview DTO by category
     */
    public ResponseDTO getTopCourseByCategory(int limit) {

        List<CoursePreviewDTO> coursePreviewDTOS = MapCourse.mapListToDTO(courseRepository.findTopCourse(limit));
        coursePreviewDTOS.forEach(coursePreviewDTO -> coursePreviewDTO.setAvatar(userRepository.findById(coursePreviewDTO.getInstructorId()).orElseThrow().getAvatar()));

        return new ResponseDTO(HttpStatus.OK, "Course found!", coursePreviewDTOS);
    }

    public ResponsePagingDTO searchCourse(int page, int eachPage, Integer star,
                                    List<Integer> categories, List<Integer> skills, List<Integer> styles, String search) {

        Pageable pageable = PageRequest.of(page - 1, eachPage);

        if (categories == null) {
            categories = categoryRepository.findAll().stream().map(Category::getCategoryId).toList();
        }
        if (skills == null) {
            skills = skillRepository.findAll().stream().map(Skill::getSkillId).toList();
        }
        if (styles == null) {
            styles = styleRepository.findAll().stream().map(Style::getDrawingStyleId).toList();
        }

        Page<Courses> courses = courseRepository.searchCourse(categories, skills, styles, search, star, pageable);


        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                courses.getTotalElements(), page, courses.getTotalPages(), eachPage, courses.getContent());

        if(!courses.isEmpty()) {
            List<CoursePreviewDTO> coursePreviewDTOS = MapCourse.mapListToDTO(courses.getContent());
            coursePreviewDTOS.forEach(coursePreviewDTO -> {
                coursePreviewDTO.setAvatar(userRepository.findById(coursePreviewDTO.getInstructorId()).orElseThrow().getAvatar());
                coursePreviewDTO.setNumLesson(lessonRepository.countByTopicCourseCourseIdAndStatus(coursePreviewDTO.getCourseId(), DrawProjectConstaints.OPEN));
            });
            responsePagingDTO.setData(coursePreviewDTOS);
            responsePagingDTO.setMessage("Course found");
            responsePagingDTO.setStatus(HttpStatus.OK);
        }

        return responsePagingDTO;
    }

    public ResponseDTO saveCourse(Authentication authentication, CourseDTO courseDTO) {
        Courses course = new Courses();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
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

    public ResponseDTO updateCourse(int courseId, CourseDTO courseDTO) {
        Courses course = courseRepository.findById(courseId).orElseThrow();
        course = setProperties(course, courseDTO);
        course.setStatus(courseDTO.getStatus());
        if(!courseDTO.getImage().getOriginalFilename().equalsIgnoreCase(course.getImage())) {
            course.setImage(fileService.uploadFile(courseDTO.getImage(), course.getCourseId(),
                    DrawProjectConstaints.IMAGE, "courses"));
        }
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
        //set videoIntro
        courseDetail.setVideoIntro(lessonService.getTrailler(courseId));
        //set number of lesson
        courseDetail.setNumLesson(lessonRepository.countByTopicCourseCourseIdAndStatus(courseId, DrawProjectConstaints.OPEN));

        return new ResponseDTO(HttpStatus.OK, "FOUND COURSE", courseDetail);
    }

    public ResponseDTO checkEnroll(int courseId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        //set status buy/enroll
        if(user.getRoles().getName().equalsIgnoreCase(DrawProjectConstaints.USER_ROLE)) {
            Optional<Enroll> enroll = enrollRepository.findByUserUserIdAndCourseCourseId(user.getUserId(), courseId);
            if(enroll.isPresent()) {
                if(enroll.get().getStatus().equalsIgnoreCase(DrawProjectConstaints.ENROLL)) {
                    return new ResponseDTO(HttpStatus.ACCEPTED, "You have enrolled this course", DrawProjectConstaints.ENROLL);
                } else if(enroll.get().getStatus().equalsIgnoreCase(DrawProjectConstaints.CLOSE)) {
                    return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, "You have been banned this course", DrawProjectConstaints.CLOSE);
                }
            }
            return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, "You have not enrolled this course", DrawProjectConstaints.UNENROLL);
        } else if(user.getRoles().getName().equalsIgnoreCase(DrawProjectConstaints.INSTRUCTOR)) {
            Boolean owner = courseRepository.existsByInstructorInstructorIdAndCourseId(user.getUserId(), courseId);
            if(owner) {
                return new ResponseDTO(HttpStatus.ACCEPTED, "You have owner this course", DrawProjectConstaints.OWNER);
            }
            return new ResponseDTO(HttpStatus.LOCKED, "You have not owner this course", DrawProjectConstaints.VIEW);
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

    public Object getCoursesByInstructor(int instructorId, int page, int eachPage, String status) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Courses> course;
        if(status.equalsIgnoreCase("")) {
            course = courseRepository.findByInstructorInstructorId(instructorId, pageable);
        } else {
            course = courseRepository.findByInstructorInstructorIdAndStatus(instructorId, status, pageable);
        }

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
        if(!courses.isEmpty()) {
            List<CoursePreviewDTO> coursePreviewDTOS = MapCourse.mapListToDTO(courses.getContent());
            coursePreviewDTOS.forEach(coursePreviewDTO -> {
                coursePreviewDTO.setNumLesson(lessonRepository.countByTopicCourseCourseIdAndStatus(coursePreviewDTO.getCourseId(), DrawProjectConstaints.OPEN));
            });
            return new ResponseDTO(HttpStatus.FOUND, "Course found", coursePreviewDTOS);
        }

        return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", courses.getContent());
    }

    public ResponseDTO reportCourse(int courseId, String message) {
        Courses course = courseRepository.findById(courseId).orElseThrow();

        course.setStatus(DrawProjectConstaints.CLOSE);

        courseRepository.save(course);

        //notificaiton email
        User user = userRepository.findById(course.getInstructor().getInstructorId()).orElseThrow();
        if(!message.isBlank()) {
            Mail mail = new Mail(user.getEmail(), DrawProjectConstaints.TEMPLATE_REPORT_COURSE);
            mailService.sendMessage(mail, new HashMap<String, Object>() {
                {
                    put("courseId", course.getCourseId());
                    put("message", message);
                    put("courseName", course.getCourseTitle());
                    put("fullName", user.getFullName());
                    put("createAt", course.getCreatedAt());
                    put("image", course.getImage());
                }
            });
        }

        return new ResponseDTO(HttpStatus.OK, message, DrawProjectConstaints.CLOSE);
    }

    public ResponseDTO openCourse(int courseId, String message) {
        Courses course = courseRepository.findById(courseId).orElseThrow();
        if(lessonRepository.countByTopicCourseCourseIdAndStatus(courseId, DrawProjectConstaints.OPEN) <= 3) {
            return new ResponseDTO(HttpStatus.NOT_ACCEPTABLE, "This course is not enough lesson to Open! Please, create lesson to Open.", "Project need at least 3 lesson to open");
        }
        course.setStatus(DrawProjectConstaints.OPEN);
        courseRepository.save(course);

        return new ResponseDTO(HttpStatus.OK, message, DrawProjectConstaints.OPEN);
    }

    public ResponseDTO getNumOfCourseForEachFeature() {

        Map<String, List<CourseFeature>> features = new HashMap<>();
        features.put("Skill", courseRepository.getCourseOfSkills());
        features.put("Category", courseRepository.getCourseOfCategories());
        features.put("Style", courseRepository.getCourseOfStyles());

        return new ResponseDTO(HttpStatus.FOUND, "Course found", features);
    }

    public ResponseDTO quickSearch(String search) {
        Pageable pageable = PageRequest.of(0, 2);
        boolean isFound = false;
        QuickSearchData quickSearchData = new QuickSearchData();
        //get list couse
        List<Courses> course = courseRepository.searchCourse(search);
        if(!course.isEmpty()) {
            quickSearchData.setCourse(MapCourse.mapListQuickCourseToDTO(course));
            quickSearchData.getCourse().forEach(quickCourse -> quickCourse.setFullName(userRepository.findById(quickCourse.getInstructorId()).orElseThrow().getFullName()));
            isFound = true;
        }
        //get category search
        Page<Category> category = categoryRepository.findTopByCategoryNameStartingWith(search, pageable);
        if(!category.isEmpty()) {
            quickSearchData.getTags().put("Category", category.getContent());
            isFound = true;
        }
        //get style search
        Page<Style> style = styleRepository.findTopByDrawingStyleNameStartingWith(search, pageable);
        if(!style.isEmpty()) {
            quickSearchData.getTags().put("Style", style.getContent());
            isFound = true;
        }
        //check is found
        if(isFound) {
            return new ResponseDTO(HttpStatus.FOUND, "Course found", quickSearchData);
        }
        return new ResponseDTO(HttpStatus.NOT_FOUND, "Course not found", quickSearchData);

    }

}
