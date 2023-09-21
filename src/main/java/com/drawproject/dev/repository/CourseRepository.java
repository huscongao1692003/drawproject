package com.drawproject.dev.repository;

import com.drawproject.dev.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer> {

    @Query("SELECT c " +
            "FROM Courses c " +
            "JOIN c.orders o " +
            "WHERE c.category.categoryId = :categoryId " +
            "GROUP BY c.category.categoryId, c.courseId " +
            "ORDER BY COUNT(o.orderId) DESC " +
            "LIMIT :limit")
    public List<Courses> findTopCourseByCategory(int categoryId, int limit);
}
