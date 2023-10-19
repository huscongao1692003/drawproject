package com.drawproject.dev.repository;

import com.drawproject.dev.model.UserAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAssignmentRepository extends JpaRepository<UserAssignment, Integer> {

    Page<UserAssignment> findByStatusAndEnrollCourseInstructorInstructorIdOrderByCreatedAt(String status, int instructorId, Pageable pageable);

}