package com.example.blog.controller;

import com.example.blog.model.Category;
import com.example.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repo;

    @GetMapping
    public String list(Model model){
        model.addAttribute("categories", repo.findAll());
        return "admin/categories";
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("category", new Category());
        return "admin/category_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category category){
        repo.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("category", repo.findById(id).orElseThrow());
        return "admin/category_form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        repo.deleteById(id);
        return "redirect:/admin/categories";
    }
}
