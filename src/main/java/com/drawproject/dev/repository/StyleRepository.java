package com.drawproject.dev.repository;

import com.drawproject.dev.model.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
    Style findByRollingStyleId(int id);
}
