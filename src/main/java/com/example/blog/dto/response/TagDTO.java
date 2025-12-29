package com.example.blog.dto.response;

import com.example.blog.entity.Tag;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TagDTO {

    private Long id;
    private String name;
    private String slug;
    private Long articleCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TagDTO fromEntity(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .articleCount((long) tag.getArticles().size())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .build();
    }

    public static TagDTO fromEntitySimple(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .createdAt(tag.getCreatedAt())
                .updatedAt(tag.getUpdatedAt())
                .build();
    }
}
