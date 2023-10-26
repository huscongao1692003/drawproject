package com.drawproject.dev.repository;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

    List<Posts> findByStatus(String status);

    Posts findPostsByPostId(int postId);

    Page<Posts> findByStatus(String status, Pageable pageable);

    Page<Posts> findAll(Pageable pageable);

    List<Posts> findPostsByUserUserId(int userId);
    Page<Posts> findByUserUserIdAndStatus(int userId, String status, Pageable pageable);

    @Query("SELECT p FROM Posts p "
            + " WHERE (p.title LIKE %:title%"
            + " OR p.description LIKE %:description%"
            + " OR p.body LIKE %:body%)"
            + " AND (:categoryId = 0 OR p.category.categoryId = :categoryId)"
            + " AND p.status LIKE '" + DrawProjectConstaints.OPEN + "'"
            + " ORDER BY p.createdAt DESC ")
    Page<Posts> searchPosts(String title, String description, String body, int categoryId, Pageable pageable);


}
