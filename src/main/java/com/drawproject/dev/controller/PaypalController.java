package com.drawproject.dev.controller;

import com.drawproject.dev.config.PaypalPaymentIntent;
import com.drawproject.dev.config.PaypalPaymentMethod;
import com.drawproject.dev.dto.PaymentRequestDTO;
import com.drawproject.dev.model.*;
import com.drawproject.dev.repository.OrderDetailRepository;
import com.drawproject.dev.repository.OrderRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.OrderService;
import com.drawproject.dev.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;

@RestController
@Slf4j
@RequestMapping("/api/v1/pay")
public class PaypalController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaypalService paypalService;

    @Autowired
    OrderService orderService;


    public static final String SUCCESS_URL = "success";
    public static final String CANCEL_URL = "cancel";

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentRequest, HttpSession session) {
        double totalPrice = (double) session.getAttribute("totalPrice");
        try {
            Payment payment = paypalService.createPayment(
                    (int) totalPrice,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.order,
                    paymentRequest.getDescription(),
                    paymentRequest.getCancelUrl() +CANCEL_URL,
                    paymentRequest.getSuccessUrl() +SUCCESS_URL
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
            @RequestParam("PayerID") String payerId, HttpSession session,Authentication authentication) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            User user = (User) session.getAttribute("loggedInPerson");
            if (payment.getState().equals("approved")) {
                // Create an Orders entity
                double totalPrice = (double) session.getAttribute("totalPrice");
                Orders orders = new Orders();
                orders.setPrice((int) totalPrice);
                orders.setDescription("test");
                orders.setUser(user);
                orders.setMethod("Paypal");

                // Save the Orders entity to the database
                orderService.createOrder(orders);

                // Create OrderDetail entities and associate them with the order
                List<Item> cartItems = (List<Item>) session.getAttribute("cart");
                for (Item item : cartItems) {
                    OrderDetail orderDetail = new OrderDetail();
                    OrderDetailId orderDetailId = new OrderDetailId();
                    orderDetailId.setCourseId(item.getCourses().getCourseId());
                    orderDetailId.setOrderId(orders.getOrderId()); // Set the order ID
                    orderDetail.setId(orderDetailId);
                    orderDetail.setCourse(item.getCourses());
                    orderDetail.setOrder(orders);

                    // Save the OrderDetail entity to the database
                    orderService.createOrderDetail(orderDetail);
                }

                session.invalidate(); // Invalidate the session after successful payment


                return ResponseEntity.ok("Payment successful");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
    }
}
