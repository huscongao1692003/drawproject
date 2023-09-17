package com.drawproject.dev.repository;

import com.drawproject.dev.model.Contact;
import com.drawproject.dev.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

    List<Posts> findByStatus(String status);

    List<Posts> findPostsByUserUserId(int userId);
}
