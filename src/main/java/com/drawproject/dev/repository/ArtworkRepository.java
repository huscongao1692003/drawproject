package com.drawproject.dev.repository;

import com.drawproject.dev.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Integer> {

    Page<Artwork> findByInstructorInstructorIdAndStatus(int instructorId, String status, Pageable pageable);
    Page<Artwork> findByInstructorInstructorIdAndCategoryCategoryIdAndStatus(int instructorId, int categoryId, String status, Pageable pageable);
    Page<Artwork> findByStatus(String status, Pageable pageable);
    Page<Artwork> findByInstructorInstructorId(int instructorId, Pageable pageable);


}