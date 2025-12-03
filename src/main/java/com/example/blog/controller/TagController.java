package com.example.blog.controller;

import com.example.blog.model.Tag;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/tags")
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tags", tagRepository.findAll());
        return "admin/tags";
    }

    @GetMapping("/new")
    public String newTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag_form";
    }

    @PostMapping("/save")
    public String saveTag(@ModelAttribute Tag tag) {
        tagRepository.save(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/edit/{id}")
    public String editTag(@PathVariable Long id, Model model) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        model.addAttribute("tag", tag);
        return "admin/tag_form";
    }

    @PostMapping("/delete/{id}")
    public String deleteTag(@PathVariable Long id) {
        tagRepository.deleteById(id);
        return "redirect:/admin/tags";
    }
}
