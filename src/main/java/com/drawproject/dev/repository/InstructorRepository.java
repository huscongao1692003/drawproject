package com.drawproject.dev.repository;

import com.drawproject.dev.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findInstructorByInstructorId(int userId);
}
