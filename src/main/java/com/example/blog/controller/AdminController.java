package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.Tag;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    // -------------------- DASHBOARD --------------------
    @GetMapping
    public String adminIndex() {
        return "admin/dashboard";
    }

    // -------------------- POSTS --------------------
    @GetMapping("/posts")
    public String listPosts(Model model){
        model.addAttribute("posts", postRepository.findAll());
        return "admin/posts";
    }

    @GetMapping("/posts/new")
    public String newPostForm(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/posts_form";
    }

    @PostMapping("/posts/save")
    public String savePost(
            @ModelAttribute Post post,
            @RequestParam(required = false) List<Long> tagIds
    ) {
        // Set published date if published
        if (post.getStatus() == Post.Status.PUBLISHED && post.getPublishedAt() == null) {
            post.setPublishedAt(LocalDateTime.now());
        }

        post.setUpdatedAt(LocalDateTime.now());

        // Set category
        if (post.getCategory() != null && post.getCategory().getId() != null) {
            categoryRepository.findById(post.getCategory().getId())
                    .ifPresent(post::setCategory);
        } else {
            post.setCategory(null);
        }

        // Set tags
        if (tagIds != null && !tagIds.isEmpty()) {
            HashSet<Tag> tags = new HashSet<>();
            for (Long id : tagIds) {
                tagRepository.findById(id).ifPresent(tags::add);
            }
            post.setTags(tags);
        } else {
            post.setTags(new HashSet<>());
        }

        postRepository.save(post);
        return "redirect:/admin/posts";
    }

    // -------------------- COMMENTS --------------------
    @GetMapping("/comments")
    public String listComments(Model model) {
        model.addAttribute("comments", commentRepository.findAll());
        return "admin/comments";
    }

    @PostMapping("/comments/{id}/approve")
    public String approveComment(@PathVariable Long id) {
        Comment c = commentRepository.findById(id).orElseThrow();
        c.setApproved(true);
        commentRepository.save(c);
        return "redirect:/admin/comments";
    }

    @PostMapping("/comments/{id}/reject")
    public String rejectComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return "redirect:/admin/comments";
    }
}
