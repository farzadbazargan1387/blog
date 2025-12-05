package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserRepository userRepository;



    @PostMapping("/{slug}/comments")
    public String comment(@PathVariable String slug,
                          @RequestParam String content,
                          Principal principal,
                          RedirectAttributes ra) {

        Post post = postService.findBySlug(slug);
        if (post == null) return "redirect:/";


        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        comment.setApproved(false);

        commentService.save(comment);

        ra.addFlashAttribute("message", "Your comment was submitted and is awaiting approval.");
        return "redirect:/posts/" + slug;
    }
}
