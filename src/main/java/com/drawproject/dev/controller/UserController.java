package com.drawproject.dev.controller;

import com.drawproject.dev.dto.OrderAdminDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.dto.user_assignment.StudentWork;
import com.drawproject.dev.model.Orders;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.CourseService;
import com.drawproject.dev.service.OrderService;
import com.drawproject.dev.service.PostService;
import com.drawproject.dev.service.UserAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    CourseService courseService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    UserAssignmentService userAssignmentService;

    @Autowired
    PostService postService;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<Object> getEnrollCourse(@PathVariable("userId") int userId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "eachPage", defaultValue = "4") int eachPage) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok(courseService.getCoursesByUser(userId, page, eachPage));
    }

    @GetMapping("/studentwork")
    public ResponseEntity<ResponseDTO> getStudentWork(@RequestParam("taskId") int taskId) {
        return ResponseEntity.ok().body(userAssignmentService.getStudentWork(taskId));
    }

    @PostMapping("/studentwork")
    public ResponseEntity<ResponseDTO> createStudentWork(MultipartFile requestImage, StudentWork studentWork) {
        return ResponseEntity.ok().body(userAssignmentService.createStudentWork(requestImage, studentWork));
    }

    @PutMapping("/studentwork")
    public ResponseEntity<ResponseDTO> updateStudentWork(@PathVariable("taskID") int taskId,
                                                        MultipartFile requestImage, StudentWork studentWork) {

        return ResponseEntity.ok().body(userAssignmentService.updateStudentWork(requestImage, studentWork));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderAdminDTO>> getOrderHistory(Authentication authentication){
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            List<Orders> userOrders = orderService.getUserOrders(user); // Retrieve user's orders

            List<OrderAdminDTO> orderAdminDTOs = new ArrayList<>();
            for (Orders order : userOrders) {
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

    @GetMapping("/id")
    public ResponseEntity<Integer> getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(user.getUserId());
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponsePagingDTO> getPostsOfUser(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                            Authentication authentication) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(postService.getPostByUserId(page, eachPage, authentication));
    }

}
