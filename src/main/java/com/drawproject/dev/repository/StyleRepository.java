package com.drawproject.dev.repository;

import com.drawproject.dev.model.Style;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
    Style findByDrawingStyleId(int id);

    List<Style> findByDrawingStyleIdIn(List<Integer> ids);

    Page<Style> findTopByDrawingStyleNameStartingWith(String name, Pageable pageable);
}
