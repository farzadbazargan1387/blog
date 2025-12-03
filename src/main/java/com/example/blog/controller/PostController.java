package com.example.blog.controller;
import com.example.blog.model.*;
import com.example.blog.service.*;
import com.example.blog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @GetMapping("/{slug}")
    public String detail(@PathVariable String slug, Model model) {
        Post post = postService.findBySlug(slug);
        if(post == null) return "redirect:/";
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.findApprovedByPost(post));
        return "post_detail";
    }

    @PostMapping("/{slug}/comments")
    public String comment(@PathVariable String slug,
                          @RequestParam String authorName,
                          @RequestParam String authorEmail,
                          @RequestParam String content,
                          RedirectAttributes ra) {
        Post post = postService.findBySlug(slug);
        if(post == null) return "redirect:/";
        Comment c = new Comment();
        c.setAuthorName(authorName);
        c.setAuthorEmail(authorEmail);
        c.setContent(content);
        c.setPost(post);
        c.setApproved(false);
        commentService.save(c);
        ra.addFlashAttribute("message","Your comment was submitted and is awaiting approval.");
        return "redirect:/posts/" + slug;
    }
    @GetMapping("/posts/posts_form")
    public String newPostForm(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/post_form";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        var results = postService.search(q, 0, 20);
        model.addAttribute("posts", results);
        model.addAttribute("q", q);
        return "search";
    }
}
