package com.drawproject.dev.repository;

import com.drawproject.dev.dto.OrderAdminDTO;
import com.drawproject.dev.dto.OrderInstructorDTO;
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


    // In your repository interface
    @Query("SELECT new com.drawproject.dev.dto.OrderInstructorDTO(u.username, u.fullName, o.course.courseTitle, o.status, STRING(o.price)) " +
            "FROM Orders o " +
            "JOIN o.user u " +
            "WHERE o.course.instructor.instructorId = :instructorId")
    List<OrderInstructorDTO> findOrdersWithUserDetailsByInstructorId(@Param("instructorId") Integer instructorId);



    @Query("SELECT o FROM Orders o JOIN FETCH o.user JOIN FETCH o.course WHERE o.user = :user")
    List<Orders> findOrdersWithUserAndCourse(@Param("user") User user);

}
