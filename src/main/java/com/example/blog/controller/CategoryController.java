package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.dto.response.CategoryDTO;
import com.example.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Result<List<CategoryDTO>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public Result<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @GetMapping("/slug/{slug}")
    public Result<CategoryDTO> getCategoryBySlug(@PathVariable String slug) {
        return Result.success(categoryService.getCategoryBySlug(slug));
    }
}
