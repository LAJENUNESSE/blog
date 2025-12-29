package com.example.blog.service;

import com.example.blog.dto.request.CategoryRequest;
import com.example.blog.dto.response.CategoryDTO;
import com.example.blog.entity.Category;
import com.example.blog.exception.BusinessException;
import com.example.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllByOrderBySortOrderAsc()
                .stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Category not found"));
        return CategoryDTO.fromEntity(category);
    }

    public CategoryDTO getCategoryBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> BusinessException.notFound("Category not found"));
        return CategoryDTO.fromEntity(category);
    }

    @Transactional
    public CategoryDTO createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw BusinessException.badRequest("Category name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getName()));
        category.setDescription(request.getDescription());
        category.setSortOrder(request.getSortOrder());

        categoryRepository.save(category);
        return CategoryDTO.fromEntitySimple(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Category not found"));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw BusinessException.badRequest("Category name already exists");
        }

        category.setName(request.getName());
        category.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getName()));
        category.setDescription(request.getDescription());
        category.setSortOrder(request.getSortOrder());

        categoryRepository.save(category);
        return CategoryDTO.fromEntitySimple(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Category not found"));

        if (!category.getArticles().isEmpty()) {
            throw BusinessException.badRequest("Cannot delete category with articles");
        }

        categoryRepository.delete(category);
    }

    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }
}
