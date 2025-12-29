package com.example.blog.controller.admin;

import com.example.blog.common.Result;
import com.example.blog.dto.request.CategoryRequest;
import com.example.blog.dto.response.CategoryDTO;
import com.example.blog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Result<List<CategoryDTO>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public Result<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @PostMapping
    public Result<CategoryDTO> createCategory(@Valid @RequestBody CategoryRequest request) {
        return Result.success(categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public Result<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        return Result.success(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
