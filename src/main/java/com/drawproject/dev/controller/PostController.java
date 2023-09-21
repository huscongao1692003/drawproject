package com.drawproject.dev.controller;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.PostDTO;
import com.drawproject.dev.dto.PostResponseDTO;
import com.drawproject.dev.model.Category;
import com.drawproject.dev.model.Posts;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.PostRepository;
import com.drawproject.dev.repository.UserRepository;
import com.drawproject.dev.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;



    @PostMapping("/savePost")
    public ResponseEntity<String> savePost(@Valid @RequestBody PostDTO postDTO, Errors errors, HttpSession session){
        User user = (User) session.getAttribute("loggedInPerson");
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);

        }
        Optional<Category> optionalCategory = categoryRepository.findById(postDTO.getCategoryId());
        if (optionalCategory.isPresent())
        {
        Category category = new Category();
        User user1 = new User();
        user1.setUserId(user.getUserId());
        category.setCategoryId(postDTO.getCategoryId());
        Posts posts = new Posts();
        posts.setCategory(category);
        posts.setImage(postDTO.getImage());
        posts.setDescription(postDTO.getDescription());
        posts.setReadingTime(postDTO.getReadingTime());
        posts.setStatus(DrawProjectConstaints.OPEN);
        posts.setTitle(postDTO.getTitle());
        posts.setBody(postDTO.getBody());
        posts.setUser(user1);
        postRepository.save(posts);
        return new ResponseEntity<>("Create post success", HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Invalid category ID");
        }

    }
    @GetMapping("/showPosts")
    public ResponseEntity<PostResponseDTO<PostDTO>> getPosts(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "perPage", defaultValue = "10") int perPage
    ) {
        Pageable pageable = PageRequest.of(page - 1, perPage); // Page numbers are 0-based
        Page<Posts> postPage = postRepository.findByStatus(DrawProjectConstaints.OPEN, pageable);

        List<PostDTO> postDTOList = postPage.getContent().stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponseDTO<PostDTO> response = new PostResponseDTO<>();
        response.setPage(page);
        response.setPer_page(perPage);
        response.setTotal((int) postPage.getTotalElements());
        response.setTotal_pages(postPage.getTotalPages());
        response.setData(postDTOList);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/showPostUser")
    public ResponseEntity<List<PostDTO>> showPostUser(HttpSession session) {
        User user = (User) session.getAttribute("loggedInPerson");

        // Retrieve posts by user ID from the database
        List<Posts> userPosts = postRepository.findPostsByUserUserId(user.getUserId());

        if (!userPosts.isEmpty()) {
            // Convert the list of Post entities to a list of PostDTOs
            List<PostDTO> postDTOs = userPosts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(postDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/closePost")
    public ResponseEntity<String> closePost(@RequestParam int id, HttpSession session){
        User user = (User) session.getAttribute("loggedInPerson");
        Optional<Posts> posts = postRepository.findById(id);
        if (posts != null && posts.get().getUser().getUserId() == user.getUserId() || user.getRoles().getName().equals("ADMIN") || user.getRoles().getName().equals("STAFF")) {
            postService.updatePostStatus(id);
            return new ResponseEntity<>("Close post Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }


}
