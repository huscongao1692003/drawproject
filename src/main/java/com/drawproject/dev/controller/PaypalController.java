package com.drawproject.dev.controller;

import com.drawproject.dev.config.PaypalPaymentIntent;
import com.drawproject.dev.config.PaypalPaymentMethod;
import com.drawproject.dev.dto.PaymentRequestDTO;
import com.drawproject.dev.repository.OrderRepository;
import com.drawproject.dev.repository.UserRepository;
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

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            Payment payment = paypalService.createPayment(
                    paymentRequest.getPrice(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.order,
                    paymentRequest.getDescription(),
                    paymentRequest.getCancelUrl(),
                    paymentRequest.getSuccessUrl()
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

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment() {
        return ResponseEntity.ok("Payment canceled");
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // do later
                return ResponseEntity.ok("Payment successful");
            }
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment execution failed");
    }
}
