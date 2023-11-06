package com.drawproject.dev.repository;

import com.drawproject.dev.dto.instructor.BestInstructor;
import com.drawproject.dev.model.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findInstructorByInstructorId(int userId);

    @Query(value = "SELECT t1.instructor_id, t3.full_name, t3.avatar, t1.numOfStudent, t2.numOfStar " +
            "FROM (" +
            "   SELECT courses.instructor_id, COUNT(enroll.enroll_id) AS numOfStudent " +
            "   FROM courses JOIN enroll " +
            "   ON courses.course_id = enroll.course_id " +
            "   GROUP BY courses.instructor_id) AS t1\n " +
            "JOIN (" +
            "    SELECT courses.instructor_id, COALESCE(AVG(feedback.star), 0) AS numOfStar " +
            "    FROM courses " +
            "    LEFT JOIN feedback ON courses.course_id = feedback.course_id " +
            "    GROUP BY courses.instructor_id " +
            ") AS t2 ON t1.instructor_id = t2.instructor_id " +
            "JOIN users AS t3 " +
            "ON t3.user_id = t1.instructor_id " +
            "ORDER BY t1.numOfStudent, t2.numOfStar DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findTopInstructors();

    @Query(value = "SELECT COUNT(e.enrollId) AS numOfStudent " +
            "FROM Courses c JOIN c.enrolls e " +
            "WHERE c.instructor.instructorId = :instructorId " +
            "GROUP BY c.instructor.instructorId")
    Integer countStudentOfInstructor(int instructorId);

    @Query(value = "SELECT AVG(f.star) " +
            "FROM Courses c " +
            "LEFT JOIN c.feedback f " +
            "WHERE c.instructor.instructorId = :instructorId " +
            "GROUP BY c.instructor.instructorId")
    Double getStarOfInstructor(int instructorId);

    @Query("SELECT SUM(o.price) " +
            "FROM Courses c " +
            "JOIN c.orders o " +
            "WHERE c.instructor.instructorId = :instructorId " +
            "GROUP BY c.instructor.instructorId")
    Float getTotalIncome(int instructorId);

    @Query(value = "SELECT ds.drawing_style_name, IFNULL(course_count, 0) AS course_count " +
            "FROM drawing_style ds " +
            "LEFT JOIN (" +
            "SELECT c.drawing_style_id, COUNT(e.course_id) AS course_count " +
            "FROM courses c " +
            "LEFT JOIN enroll e ON c.course_id = e.course_id " +
            "where c.instructor_id = :instructorId " +
            "GROUP BY c.drawing_style_id " +
            ") subquery ON ds.drawing_style_id = subquery.drawing_style_id", nativeQuery = true)
    List<Object[]> getNumOfCourseByStyle(int instructorId);

    @Query(value = "SELECT ds.category_name, IFNULL(course_count, 0) AS course_count " +
            "FROM category ds " +
            "LEFT JOIN (" +
            "SELECT c.category_id, COUNT(e.course_id) AS course_count " +
            "FROM courses c " +
            "LEFT JOIN enroll e ON c.course_id = e.course_id " +
            "where c.instructor_id = :instructorId " +
            "GROUP BY c.category_id " +
            ") subquery ON ds.category_id = subquery.category_id", nativeQuery = true)
    List<Object[]> getNumOfCourseByCategory(int instructorId);

    @Query("SELECT month(o.createdAt), sum(o.price) " +
            "FROM Courses c JOIN c.orders o " +
            "WHERE c.instructor.instructorId = :instructorId " +
            "AND year(o.createdAt) = :year " +
            "GROUP BY c.instructor.instructorId, month(o.createdAt) " +
            "ORDER BY month(o.createdAt)")
    List<Object[]> getIncomeFollowMonth(int instructorId, int year);

}
