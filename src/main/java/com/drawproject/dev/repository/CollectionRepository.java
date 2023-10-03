package com.drawproject.dev.repository;

import com.drawproject.dev.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Collection findByUserUserId(int userId);
}
