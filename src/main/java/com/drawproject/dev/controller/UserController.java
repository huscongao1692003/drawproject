package com.drawproject.dev.controller;

import com.drawproject.dev.service.CourseService;
import com.drawproject.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    CourseService courseService;

    @GetMapping("/users/{id}/courses")
    public ResponseEntity<Object> getEnrollCourse(@PathVariable("userId") int userId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "eachPage", defaultValue = "4") int eachPage) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok(courseService.getCoursesByUser(userId, page, eachPage));
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

}
