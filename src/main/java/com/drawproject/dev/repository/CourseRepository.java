package com.drawproject.dev.repository;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.course.CourseFeature;
import com.drawproject.dev.model.Courses;
import com.drawproject.dev.model.Style;
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
            "WHERE c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' GROUP BY c.courseId " +
            "ORDER BY COUNT(o.orderId) DESC " +
            "LIMIT :limit")
    List<Courses> findTopCourse(int limit);

    @Query("SELECT c " +
            "FROM Courses c LEFT JOIN c.feedback f " +
            "ON c.courseId = f.courses.courseId " +
            "WHERE c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' AND (c.category.categoryId IN :categories) " +
            "AND (c.skill.skillId IN :skills) " +
            "AND (c.style.drawingStyleId IN :styles) " +
            "AND (c.courseTitle LIKE %:search% " +
            "OR c.description LIKE %:search% " +
            "OR c.information LIKE %:search%) " +
            "GROUP BY c.courseId HAVING COALESCE(AVG(f.star), 0) >= :star")
    Page<Courses> searchCourse(List<Integer> categories, List<Integer> skills, List<Integer> styles, String search, int star, Pageable pageable);

    Page<Courses> findByInstructorInstructorId(int instructionId, Pageable pageable);

    Page<Courses> findByInstructorInstructorIdAndStatus(int instructionId, String status, Pageable pageable);

    Courses findCoursesByCourseId(int courseId);

    Page<Courses> findByEnrollsUserUserId(int userId, Pageable pageable);

    int countByInstructorInstructorIdAndStatus(int instructorId, String status);

    @Query("SELECT new com.drawproject.dev.dto.course.CourseFeature(s.skillId, s.skillName, count(c.courseId)) " +
            "FROM Courses c " +
            "RIGHT JOIN Skill s " +
            "ON c.skill.skillId = s.skillId " +
            "WHERE c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' Or c.status IS null " +
            "GROUP BY s.skillId ")
    List<CourseFeature> getCourseOfSkills();

    @Query("SELECT new com.drawproject.dev.dto.course.CourseFeature(ca.categoryId, ca.categoryName, count(c.courseId)) " +
            "FROM Courses c " +
            "JOIN Category ca " +
            "ON ca.categoryId = c.category.categoryId " +
            "WHERE c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' Or c.status IS null " +
            "GROUP BY c.category ")
    List<CourseFeature> getCourseOfCategories();

    @Query("SELECT new com.drawproject.dev.dto.course.CourseFeature(s.drawingStyleId, s.drawingStyleName, count(c.courseId)) " +
            "FROM Style s " +
            "LEFT JOIN Courses c " +
            "ON c.style.drawingStyleId = s.drawingStyleId " +
            "WHERE c.status LIKE '" + DrawProjectConstaints.OPEN +
            "' Or c.status IS null " +
            "GROUP BY s.drawingStyleId ")
    List<CourseFeature> getCourseOfStyles();

    @Query(value = "SELECT c.* " +
            "FROM courses c Join orders o " +
            "ON c.course_id = o.course_id " +
            "JOIN feedback f " +
            "ON c.course_id = f.course_id " +
            "WHERE c.course_title LIKE :courseTitle% " +
            "AND c.status LIKE 'Open' " +
            "GROUP BY c.course_id " +
            "ORDER BY COUNT(o.order_id) DESC , COALESCE(AVG(f.star), 0) DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Courses> searchCourse(String courseTitle);

    Boolean existsByInstructorInstructorIdAndCourseId(int instructorId, int courseId);

}
