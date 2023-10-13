package com.drawproject.dev.repository;

import com.drawproject.dev.model.Orders;
import com.drawproject.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findOrdersByCourse_InstructorInstructorId(int instructorId);

    @Query("SELECT o FROM Orders o JOIN FETCH o.user JOIN FETCH o.course WHERE o.user = :user")
    List<Orders> findOrdersWithUserAndCourse(@Param("user") User user);

}
