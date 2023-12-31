package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.*;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.dto.instructor.InstructorDTO;
import com.drawproject.dev.dto.instructor.InstructorDetailDTO;
import com.drawproject.dev.dto.instructor.InstructorProfile;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.model.Orders;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.OrderRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/instructor")
public class InstructorController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    CertificateService certificateService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    UserAssignmentService userAssignmentService;

    @Autowired
    ArtWorkService artWorkService;

    @Autowired
    InstructorRepository instructorRepository;

    @GetMapping("")
    public ResponseEntity<List<InstructorDTO>> showInstructor() {
        List<User> users = profileService.findInstructor();

        if (!users.isEmpty()) {
            List<InstructorDTO> instructorDTOS = users.stream()
                    .map(instructor -> {
                        InstructorDTO dto = modelMapper.map(instructor, InstructorDTO.class);
                        dto.setNumberOfCourse(instructorService.getNumOfCourses(instructor.getUserId())); // Set the number of courses
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(instructorDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<InstructorDetailDTO> showInstructorDetail(@PathVariable int userId) {
        User user = profileService.findInstructorById(userId);
        Instructor instructor = instructorRepository.findById(userId).orElseThrow();

        if (user != null) {
            InstructorDetailDTO instructorDetailDTO = modelMapper.map(user, InstructorDetailDTO.class);
            instructorDetailDTO.setBio(instructor.getBio());
            instructorDetailDTO.setEducation(instructor.getEducation());
            instructorDetailDTO.setPayment(instructor.getPayment());
            return ResponseEntity.ok(instructorDetailDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDTO> updateInstructor(@PathVariable("userId") int userId,
                                                        @RequestBody InstructorProfile instructorProfile) {
        instructorProfile.setInstructorId(userId);
        return ResponseEntity.ok().body(instructorService.updateInstructor(instructorProfile));
    }

    @GetMapping("/{userId}/experiences")
    public ResponseEntity<ResponseDTO> getExperiences(@PathVariable("userId") int userId) {
        return ResponseEntity.ok().body(instructorService.getExperiences(userId));
    }

    @GetMapping("/{userId}/certificates")
    public ResponseEntity<ResponseDTO> getCertificates(@PathVariable("userId") int userId,
                                                       @RequestParam(value = "status", defaultValue = "", required = false) String status) {
        return ResponseEntity.ok().body(certificateService.getCertificates(userId, status));
    }

    @PostMapping("/certificates")
    public ResponseEntity<ResponseDTO> createCertificates(Authentication authentication, List<MultipartFile> listImages) {
        String username = authentication.getName();
        User instructor = userRepository.findByUsername(username).orElse(null);

        return ResponseEntity.ok().body(certificateService.createCertificate(instructor.getUserId(), listImages));
    }

    @DeleteMapping("/certificates/{certificateId}")
    public ResponseEntity<ResponseDTO> deleteCertificates(@PathVariable("certificateId") int certificateId) {

        return ResponseEntity.ok().body(certificateService.deleteCertificate(certificateId));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderInstructorDTO>> getOrderHistory(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getRoles().getName().equals(DrawProjectConstaints.INSTRUCTOR)) {
            List<OrderInstructorDTO> orderInstructorDTOS = orderService.getOrderDetailsByInstructor(user.getUserId()); // Retrieve user's orders

            return ResponseEntity.ok(orderInstructorDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Object> getEnrollCourse(@PathVariable("userId") int instructorId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                  @RequestParam(value = "status", defaultValue = "") String status) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId, page, eachPage, status));
    }

    @GetMapping("/{userId}/submissions")
    public ResponseEntity<ResponsePagingDTO> getSubmissions(@PathVariable("userId") int instructorId,
                                                            @RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                            @RequestParam(value = "status") String status) {
        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(userAssignmentService.getSubmissions(page, eachPage, status, instructorId));
    }

    @PutMapping("/submissions/{taskId}")
    public ResponseEntity<ResponseDTO> gradeStudentWork(@PathVariable("taskId") int taskId,
                                                        @RequestParam(value = "grade") int grade,
                                                        @RequestParam(value = "comment") String comment) {

        return ResponseEntity.ok().body(userAssignmentService.gradeHomeWork(taskId, grade, comment));

    }

    @GetMapping("/{userId}/artworks")
    public ResponseEntity<Object> getArtWorks(@PathVariable("userId") int instructorId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                  @RequestParam(value = "categoryId", defaultValue = "0") int categoryId,
                                                  @RequestParam(value = "status", defaultValue = "", required = false) String status) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok(artWorkService.getArtworks(page, eachPage, instructorId, categoryId, status));
    }

    @PostMapping("/artworks")
    public ResponseEntity<Object> createArtwork(MultipartFile requestImage, ArtWorkDTO artWorkDTO, Authentication authentication) {


        return ResponseEntity.ok(artWorkService.createArtwork(requestImage, artWorkDTO, authentication));
    }

    @DeleteMapping("/artworks/{artworkId}")
    public ResponseEntity<Object> deleteArtWork(@PathVariable("artworkId") int artworkId) {


        return ResponseEntity.ok(artWorkService.deleteArtWork(artworkId));
    }

    @GetMapping("/top-instructors")
    public ResponseEntity<ResponseDTO> getTopInstructors() {
        return ResponseEntity.ok().body(instructorService.topInstructor());
    }

    @GetMapping("/{userId}/all-courses")
    public ResponseEntity<ResponseDTO> getAllCourse(@PathVariable("userId") int userId) {
        return ResponseEntity.ok().body(courseService.getAllCourseOfInstructor(userId));
    }

}
