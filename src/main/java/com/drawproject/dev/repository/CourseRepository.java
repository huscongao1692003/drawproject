package com.drawproject.dev.repository;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "AND c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' GROUP BY c.category.categoryId, c.courseId " +
            "ORDER BY COUNT(o.orderId) DESC " +
            "LIMIT :limit")
    List<Courses> findTopCourseByCategory(int categoryId, int limit);

    @Query("SELECT c " +
            "FROM Courses c LEFT JOIN c.feedback f " +
            "ON c.courseId = f.courses.courseId " +
            "WHERE c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' AND (c.category.categoryId IN :categories) " +
            "AND (c.skill.skillId IN :skills) " +
            "AND (c.courseTitle LIKE %:search% " +
            "OR c.description LIKE %:search% " +
            "OR c.information LIKE %:search%) " +
            "GROUP BY c.courseId HAVING COALESCE(AVG(f.star), 0) >= :star")
    Page<Courses> searchCourse(List<Integer> categories, List<Integer> skills, String search, int star, Pageable pageable);

    Page<Courses> findByUsersUserId(int userId, Pageable pageable);

    Courses findCoursesByCourseId(int courseId);

    Page<Courses> findByInstructorUserId(int userId, Pageable pageable);
}
