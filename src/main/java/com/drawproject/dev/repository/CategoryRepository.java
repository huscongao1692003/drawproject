package com.drawproject.dev.repository;

import com.drawproject.dev.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryId(int id);
    Page<Category> findTopByCategoryNameStartingWith(String name, Pageable pageable);
}
