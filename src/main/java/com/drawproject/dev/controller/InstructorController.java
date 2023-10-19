package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.*;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.model.Orders;
import com.drawproject.dev.model.User;
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

    @GetMapping("")
    public ResponseEntity<List<InstructorDTO>> showInstructor() {
        List<User> users = profileService.findInstructor();

        if (!users.isEmpty()) {
            List<InstructorDTO> instructorDTOS = users.stream()
                    .map(instructor -> {
                        InstructorDTO dto = modelMapper.map(instructor, InstructorDTO.class);
                        dto.setNumberOfCourse(instructor.getEnrolls().size()); // Set the number of courses
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
        User instructor = profileService.findInstructorById(userId);

        if (instructor != null) {
            InstructorDetailDTO instructorDetailDTO = modelMapper.map(instructor, InstructorDetailDTO.class);
            return ResponseEntity.ok(instructorDetailDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/certificates")
    public ResponseEntity<ResponseDTO> getCertificates(@PathVariable("userId") int userId) {
        return ResponseEntity.ok().body(certificateService.getCertificates(userId));
    }

    @PostMapping("certificates")
    public ResponseEntity<ResponseDTO> createCertificates(HttpSession session, List<MultipartFile> image) {
        User instructor = (User) session.getAttribute("loggedInPerson");

        return ResponseEntity.ok().body(certificateService.createCertificate(instructor.getUserId(), image));
    }

    @PutMapping("/certificates/{certificateId}")
    public ResponseEntity<ResponseDTO> updateCertificates(@PathVariable("certificateId") int certificateId,
                                                     MultipartFile image, HttpSession session) {
        User instructor = (User) session.getAttribute("loggedInPerson");
        return ResponseEntity.ok().body(certificateService.updateCertificate(certificateId, image, instructor.getUserId()));
    }

    @DeleteMapping("/certificates/{certificateId}")
    public ResponseEntity<ResponseDTO> deleteCertificates(@PathVariable("certificateId") int certificateId) {

        return ResponseEntity.ok().body(certificateService.deleteCertificate(certificateId));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderAdminDTO>> getOrderHistory(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.getRoles().equals(DrawProjectConstaints.INSTRUCTOR)) {
            List<Orders> instructorOrders = orderRepository.findOrdersByCourse_InstructorInstructorId(user.getUserId()); // Retrieve user's orders

            List<OrderAdminDTO> orderAdminDTOs = new ArrayList<>();

            for (Orders order : instructorOrders) {
                OrderAdminDTO orderAdminDTO = new OrderAdminDTO();
                orderAdminDTO.setUsername(user.getUsername());
                orderAdminDTO.setFullName(user.getFullName());
                orderAdminDTO.setCourseName(order.getCourse().getCourseTitle());
                orderAdminDTO.setStatus(order.getStatus());
                orderAdminDTO.setPrice(String.valueOf(order.getPrice())); // Convert price to string or format it as needed

                orderAdminDTOs.add(orderAdminDTO);
            }

            return ResponseEntity.ok(orderAdminDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Object> getEnrollCourse(@PathVariable("userId") int instructorId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "eachPage", defaultValue = "4") int eachPage) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok(courseService.getCoursesByInstructor(instructorId, page, eachPage));
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

}
