package blog.be.service.impl;

import blog.be.entity.Comment;
import blog.be.repository.CommentRepository;
import blog.be.repository.PostRepository;
import blog.be.service.CommentService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public Page<Comment> getAllCommentsByPostId(int postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @Override
    @Transactional
    public Comment createComment(int postId, Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            comment.setCreatedAt(new Date());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
