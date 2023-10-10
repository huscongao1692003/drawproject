package com.drawproject.dev.repository;

import com.drawproject.dev.model.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends JpaRepository<Enroll,Integer> {
}
