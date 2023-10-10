//package com.drawproject.dev.controller;
//
//import com.drawproject.dev.constrains.DrawProjectConstaints;
//import com.drawproject.dev.dto.CommentDTO;
//import com.drawproject.dev.dto.CommentRequestDTO;
//import com.drawproject.dev.model.Comment;
//import com.drawproject.dev.model.Courses;
//import com.drawproject.dev.model.Posts;
//import com.drawproject.dev.model.User;
//import com.drawproject.dev.repository.CommentRepository;
//import com.drawproject.dev.repository.CourseRepository;
//import com.drawproject.dev.repository.PostRepository;
//import com.drawproject.dev.service.CommentService;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController()
//@Slf4j
//@RequestMapping("/api/v1/comment")
//public class CommentController {
//
//    @Autowired
//    CommentRepository commentRepository;
//
//    @Autowired
//    CommentService commentService;
//
//    @Autowired
//    PostRepository postRepository;
//
//    @Autowired
//    CourseRepository courseRepository;
//
//    @GetMapping("/post/{postId}")
//    public ResponseEntity<List<CommentDTO>> getAllCommentPost(@PathVariable int postId, HttpSession session) {
//            List<CommentDTO> commentDTO = commentService.getAllCommentPost(postId);
//            session.setAttribute("postId", postId);
//        return ResponseEntity.ok(commentDTO);
//    }
//
//    @GetMapping("course/{courseId}")
//    public ResponseEntity<List<CommentDTO>> getAllCommentCourse(@PathVariable int courseId) {
//        List<CommentDTO> commentDTO = commentService.getAllCommentCourse(courseId);
//
//        return ResponseEntity.ok(commentDTO);
//    }
//
//    @PostMapping("post")
//    public ResponseEntity<String> commentForPost(@RequestBody CommentRequestDTO commentRequestDTO, HttpSession session){
//        User user = (User) session.getAttribute("loggedInPerson");
//        if(commentRequestDTO.getPostId() != 0 && user != null) {
//            Posts posts = postRepository.findPostsByPostId(commentRequestDTO.getPostId());
//            Comment comment = new Comment();
//            comment.setCommentValue(commentRequestDTO.getCommentValue());
//            comment.setUser(user);
//            comment.setPosts(posts);
//            comment.setStatus(DrawProjectConstaints.OPEN);
//            commentRepository.save(comment);
//            return new ResponseEntity<>("Comment Successful", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Some things has error", HttpStatus.BAD_REQUEST);
//    }
//
//    @PostMapping("course")
//    public ResponseEntity<String> commentForCourse(@RequestBody CommentRequestDTO commentRequestDTO,HttpSession session){
//        User user = (User) session.getAttribute("loggedInPerson");
//        if(commentRequestDTO.getCourseId() != 0 && user != null) {
//            Courses courses = courseRepository.findCoursesByCourseId(commentRequestDTO.getCourseId());
//            Comment comment = new Comment();
//            comment.setCommentValue(commentRequestDTO.getCommentValue());
//            comment.setUser(user);
//            comment.setCourses(courses);
//            comment.setStatus(DrawProjectConstaints.OPEN);
//            commentRepository.save(comment);
//            return new ResponseEntity<>("Comment Successful", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Some things has error", HttpStatus.BAD_REQUEST);
//    }
//
//    @DeleteMapping("{commentId}")
//    public ResponseEntity<String> deleteCommentForPost(@PathVariable int commentId,HttpSession session){
//        Optional<Comment> comment = commentRepository.findById(commentId);
//        User user = (User) session.getAttribute("loggedInPerson");
//        if(comment.get().getCommentId() != 0 && user != null){
//            if(comment.get().getUser().getUserId() == user.getUserId()){
//                comment.get().setCourses(null);
//                comment.get().setPosts(null);
//                comment.get().setUser(null);
//                commentRepository.save(comment.get());
//                commentRepository.deleteById(commentId);
//                return new ResponseEntity<>("Delete Success", HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>("Fail to delete",HttpStatus.BAD_REQUEST);
//    }
//
//    @PutMapping("{commentId}")
//    public ResponseEntity<String> editComment(@PathVariable int commentId,@RequestBody CommentRequestDTO commentRequestDTO,HttpSession session){
//        Optional<Comment> comment = commentRepository.findById(commentId);
//        comment.get().setCommentValue(commentRequestDTO.getCommentValue());
//        commentRepository.save(comment.get());
//        return new ResponseEntity<>("Edit Comment Success", HttpStatus.OK);
//
//    }
//
//
//}
