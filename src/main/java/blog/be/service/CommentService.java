package blog.be.service;

import blog.be.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<Comment> getAllCommentsByPostId(int postId, Pageable pageable);
    Comment createComment(int postId, Comment dto);
}
