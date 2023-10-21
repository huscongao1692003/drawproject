package com.drawproject.dev.controller;

import com.drawproject.dev.config.PaypalPaymentIntent;
import com.drawproject.dev.config.PaypalPaymentMethod;
import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.PaymentRequestDTO;
import com.drawproject.dev.model.*;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.OrderRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.OrderService;
import com.drawproject.dev.service.PaypalService;
import com.drawproject.dev.service.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
@RequestMapping("/api/v1/pay")
public class PaypalController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaypalService paypalService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    public static final String SUCCESS_URL = "success";
    public static final String CANCEL_URL = "cancel";

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentRequest, HttpSession session) {
        try {
            Payment payment = paypalService.createPayment(
                    paymentRequest.getTotalPrice(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.order,
                    paymentRequest.getDescription(),
                    paymentRequest.getUrl() +CANCEL_URL,
                    paymentRequest.getUrl() +SUCCESS_URL
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed");
    }

    @GetMapping(CANCEL_URL)
    public ResponseEntity<String> cancelPayment() {
        return ResponseEntity.ok("Payment canceled");
    }

    @GetMapping(SUCCESS_URL)
    public ResponseEntity<String> successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,Authentication authentication, PaymentRequestDTO paymentRequestDTO) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            String username = authentication.getName();
            Optional<Courses> courses = courseRepository.findById(paymentRequestDTO.getCourseId());
            User user = userRepository.findByUsername(username).orElse(null);
            Enroll enroll = new Enroll();
            if (payment.getState().equals("approved")) {
                    Orders orders = new Orders();
                    orders.setPrice(paymentRequestDTO.getTotalPrice());
                    orders.setDescription("test");
                    orders.setUser(user);
                    orders.setMethod("Paypal");
                    orders.setCourse(courses.get());
                    orders.setStatus("Pay Success");
                    enroll.setCourse(courses.get());
                    enroll.setStatus(DrawProjectConstaints.ENROLL);
                    enroll.setUser(user);
                    orderService.createEnroll(enroll);
                    orderService.createOrder(orders);



                return ResponseEntity.ok("Payment successful");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
    }
}
