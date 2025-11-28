package com.example.blog.controller;
import com.example.blog.model.Post;
import com.example.blog.model.Comment;
import com.example.blog.repository.*;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public String adminIndex() {
        return "admin/dashboard";
    }

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
        return "admin/post_form";
    }

    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute Post post) {
        if(post.getStatus() == Post.Status.PUBLISHED && post.getPublishedAt() == null){
            post.setPublishedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        return "redirect:/admin/posts";
    }

    // comments admin
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
