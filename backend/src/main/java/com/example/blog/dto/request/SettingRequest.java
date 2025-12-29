package com.example.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SettingRequest {

    @NotBlank(message = "Key is required")
    @Size(max = 100, message = "Key must not exceed 100 characters")
    private String key;

    private String value;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}
