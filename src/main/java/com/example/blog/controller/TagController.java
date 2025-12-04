package com.example.blog.controller;

import com.example.blog.model.Tag;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository repo;

    @GetMapping
    public String list(Model model){
        model.addAttribute("tags", repo.findAll());
        return "admin/tags";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tag_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Tag tag){
        repo.save(tag);
        return "redirect:/admin/tags";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("tag", repo.findById(id).orElseThrow());
        return "admin/tag_form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        repo.deleteById(id);
        return "redirect:/admin/tags";
    }
}
