package com.example.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Category name must not exceed 50 characters")
    private String name;

    @Size(max = 100, message = "Slug must not exceed 100 characters")
    private String slug;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    private Integer sortOrder = 0;
}
