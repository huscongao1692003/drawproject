package com.drawproject.dev.repository;

import com.drawproject.dev.model.Courses;
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
            "GROUP BY c.category.categoryId, c.courseId " +
            "ORDER BY COUNT(o.orderId) DESC " +
            "LIMIT :limit")
    List<Courses> findTopCourseByCategory(int categoryId, int limit);

    @Query("SELECT c FROM Courses c " +
            "WHERE (c.category.categoryId IN :categories) " +
            "AND (c.skill.skillId IN :skills)" +
            "AND (c.courseTitle LIKE %:search% " +
            "OR c.description LIKE %:search% " +
            "OR c.information LIKE %:search%) " +
            "AND c.courseId IN (" +
            " SELECT f.courses.courseId FROM Feedback f " +
            "GROUP BY f.courses.courseId HAVING SUM(f.star)/COUNT(f.star) > :star)" )
    List<Courses> searchCourse(List<Integer> categories, List<Integer> skills, String search, int star, Pageable pageable);

    @Query("SELECT count(*) FROM Courses c " +
            "WHERE (c.category.categoryId IN :categories) " +
            "AND (c.skill.skillId IN :skills)" +
            "AND (c.courseTitle LIKE %:search% " +
            "OR c.description LIKE %:search% " +
            "OR c.information LIKE %:search%) " +
            "AND c.courseId IN (" +
            " SELECT f.courses.courseId FROM Feedback f " +
            "GROUP BY f.courses.courseId HAVING SUM(f.star)/COUNT(f.star) > :star)" )
    int countSearchCourse(List<Integer> categories, List<Integer> skills, int star, String search);


}
