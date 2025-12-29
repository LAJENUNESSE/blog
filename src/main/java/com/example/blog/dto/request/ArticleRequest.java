package com.example.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class ArticleRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 255, message = "Slug must not exceed 255 characters")
    private String slug;

    private String summary;

    @NotBlank(message = "Content is required")
    private String content;

    @Size(max = 255, message = "Cover image URL must not exceed 255 characters")
    private String coverImage;

    private String status = "DRAFT";

    private Boolean isTop = false;

    private Boolean allowComment = true;

    private Long categoryId;

    private Set<Long> tagIds;
}
