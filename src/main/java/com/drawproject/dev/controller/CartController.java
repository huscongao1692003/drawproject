package com.drawproject.dev.controller;

import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Item;
import com.drawproject.dev.repository.CourseRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    CourseRepository courseRepository;

    private int exists(int coursesId, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getCourses().getCourseId() == coursesId) {
                return i; // Return the index if the item exists
            }
        }
        return -1; // Return -1 if the item does not exist
    }
    @PostMapping("/addItemToCart")
    public ResponseEntity<String> addItemToCart(
            @RequestParam int courseId, HttpSession session) {

        Optional<Courses> optionalCourses = courseRepository.findById(courseId);

        if (optionalCourses.isPresent()) {
            if (session.getAttribute("cart") == null) {
                List<Item> cart = new ArrayList<Item>();
                cart.add(new Item(optionalCourses.get()));
                session.setAttribute("cart", cart);
            } else {
                List<Item> cart = (List<Item>) session.getAttribute("cart");
                int index = this.exists(courseId, cart);
                if (index == -1) {
                    cart.add(new Item(optionalCourses.get()));
                } else {
                    return new ResponseEntity<>("Course Already Added", HttpStatus.BAD_REQUEST);
                }
                session.setAttribute("cart", cart);
            }
        } else {
            return ResponseEntity.badRequest().body("No Product!!!!");
        }

        List<Item> cartItem = (List<Item>) session.getAttribute("cart");
        int totalItems = 0;

        // Calculate total cost of items in the cart
        List<Item> cartItems = (List<Item>) session.getAttribute("cart");
        double totalPrice = 0;

        if (cartItems != null) {
            totalPrice = cartItems.stream()
                    .mapToInt(item -> item.getCourses().getPrice())
                    .sum();
        }

        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("totalItems", totalItems);

        return ResponseEntity.ok("Added item to cart successfully");
    }



}
