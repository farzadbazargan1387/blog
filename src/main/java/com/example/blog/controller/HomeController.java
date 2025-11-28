package com.example.blog.controller;
import com.example.blog.service.PostService;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @GetMapping("/")
    public String index(Model model) {
        var posts = postService.listPublished(0, 6);
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());
        return "index";
    }
}
