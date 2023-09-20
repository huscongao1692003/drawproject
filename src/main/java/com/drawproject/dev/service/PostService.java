package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.model.Posts;
import com.drawproject.dev.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Posts> findPostWithOpenStatus(){
        List<Posts> posts = postRepository.findByStatus(DrawProjectConstaints.OPEN);
        return posts;
    }

    public boolean updatePostStatus(int postId){
        boolean isUpdated = false;
        Optional<Posts> posts = postRepository.findById(postId);
        posts.ifPresent(contact1 -> {
            contact1.setStatus(DrawProjectConstaints.CLOSE);
        });
        Posts updatedPost = postRepository.save(posts.get());
        if(null != updatedPost) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
