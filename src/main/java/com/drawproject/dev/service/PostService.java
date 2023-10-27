package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.PostDTO;
import com.drawproject.dev.dto.PostResponseDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.model.Posts;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.PostRepository;
import com.drawproject.dev.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepository  userRepository;

    public List<Posts> findPostWithOpenStatus(){
        List<Posts> posts = postRepository.findByStatus(DrawProjectConstaints.OPEN);
        return posts;
    }


    public PostResponseDTO<PostDTO> getPosts(int page, int perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage); // Page numbers are 0-based
        Page<Posts> userPage = postRepository.findAll(pageable);

        List<PostDTO> postDTOList = userPage.getContent().stream()
                .map(user -> modelMapper.map(user, PostDTO.class))
                .collect(Collectors.toList());

        PostResponseDTO<PostDTO> response = new PostResponseDTO<>();
        response.setPage(page);
        response.setPer_page(perPage);
        response.setTotal((int) userPage.getTotalElements());
        response.setTotal_pages(userPage.getTotalPages());
        response.setData(postDTOList);

        return response;
    }

    public boolean updatePostStatus(int postId){
        boolean isUpdated = false;
        Optional<Posts> posts = postRepository.findById(postId);
        posts.ifPresent(posts1 -> {
            posts1.setStatus(DrawProjectConstaints.CLOSE);
        });
        Posts updatedPost = postRepository.save(posts.get());
        if(null != updatedPost && updatedPost.getUser() !=null) {
            isUpdated = true;
        }
        return isUpdated;
    }

    public ResponsePagingDTO getPostByUserId(int page, int eachPage, Authentication authentication){
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElse(null);
        Page<Posts> posts = postRepository.findByUserUserIdAndStatus(user.getUserId(), DrawProjectConstaints.OPEN, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Course not found",
                posts.getTotalElements(), page, posts.getTotalPages(), eachPage, null);

        if(!posts.isEmpty()) {
            List<PostDTO> postDTOList = posts.getContent().stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .toList();
            responsePagingDTO.setData(postDTOList);
            responsePagingDTO.setMessage("Found posts of you");
            responsePagingDTO.setStatus(HttpStatus.FOUND);
        }

        return responsePagingDTO;
    }

    public ResponsePagingDTO searchPosts(int page, int eachPage, String search, int categoryId){
        Pageable pageable = PageRequest.of(page - 1, eachPage);

        Page<Posts> posts = postRepository.searchPosts(search, search, search, categoryId, pageable);

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.NOT_FOUND, "Posts not found",
                posts.getTotalElements(), page, posts.getTotalPages(), eachPage, "");

        if(!posts.isEmpty()) {
            List<PostDTO> postDTOList = posts.getContent().stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .toList();
            responsePagingDTO.setData(postDTOList);
            responsePagingDTO.setMessage("Found posts of you");
            responsePagingDTO.setStatus(HttpStatus.FOUND);
        }

        return responsePagingDTO;

    }

}
