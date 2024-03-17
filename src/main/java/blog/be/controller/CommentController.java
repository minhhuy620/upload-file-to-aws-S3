package blog.be.controller;


import blog.be.dtos.ApiResponse;
import blog.be.dtos.CommentDto;
import blog.be.entity.Comment;
import blog.be.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/posts/{post_id}/comments")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Page<Comment>> getAllCommentsByPostId(@PathVariable (value = "post_id") int postId,
                                                Pageable pageable) {
        Page<Comment> data = commentService.getAllCommentsByPostId(postId,pageable);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/posts/{post_id}/comments")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "post_id") int postId,
                                                @Valid @RequestBody Comment comment) {
        Comment data = commentService.createComment(postId, comment);
        return ResponseEntity.ok(data);
    }
}
