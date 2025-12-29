package com.example.blog.dto.response;

import com.example.blog.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer sortOrder;
    private Long articleCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryDTO fromEntity(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .sortOrder(category.getSortOrder())
                .articleCount((long) category.getArticles().size())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public static CategoryDTO fromEntitySimple(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .sortOrder(category.getSortOrder())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
