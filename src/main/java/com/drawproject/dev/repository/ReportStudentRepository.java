package com.drawproject.dev.repository;

import com.drawproject.dev.model.ReportStudent;
import com.drawproject.dev.model.ReportStudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReportStudentRepository extends JpaRepository<ReportStudent, ReportStudentId> {
    Page<ReportStudent> findById_StudentId(int studentId, Pageable pageable);
    Page<ReportStudent> findById_CourseId(int courseId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM ReportStudent rs WHERE rs.id.studentId = :studentId AND rs.id.courseId = :courseId")
    void deleteByIdStudentIdAndIdCourseId(int studentId, int courseId);
    ReportStudent findById_StudentIdAndId_CourseId(int studentId, int courseId);

    Boolean existsByStudentUserIdAndCourseCourseId(int studentId, int courseId);

}