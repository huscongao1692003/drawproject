package com.drawproject.dev.repository;

import com.drawproject.dev.model.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    List<Certificate> findByInstructorInstructorId(int instructionId);

    Certificate findByCertificateIdAndInstructorInstructorId(int certificateId, int instructorId);

    Page<Certificate> findByStatus(String status, Pageable pageable);

}