package com.example.blog.service;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(Comment c) { return commentRepository.save(c); }
    public List<Comment> findApprovedByPost(Post post) { return commentRepository.findByPostAndApproved(post, true); }
}
