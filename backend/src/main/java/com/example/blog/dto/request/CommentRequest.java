package com.example.blog.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Content is required")
    private String content;

    @Size(max = 50, message = "Author name must not exceed 50 characters")
    private String authorName;

    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String authorEmail;

    @Size(max = 255, message = "URL must not exceed 255 characters")
    private String authorUrl;

    private Long parentId;
}
