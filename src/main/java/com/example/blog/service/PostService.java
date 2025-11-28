package com.example.blog.service;
import com.example.blog.model.Post;
import com.example.blog.model.Post.Status;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> listPublished(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByStatusOrderByPublishedAtDesc(Status.PUBLISHED, pageable);
    }

    public Post save(Post p) {
        return postRepository.save(p);
    }

    public Post findBySlug(String slug) {
        return postRepository.findBySlug(slug).orElse(null);
    }

    public Page<Post> search(String q, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatus(q, q, Status.PUBLISHED, pageable);
    }
}
