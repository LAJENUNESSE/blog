package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.dto.response.TagDTO;
import com.example.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public Result<List<TagDTO>> getAllTags() {
        return Result.success(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public Result<TagDTO> getTagById(@PathVariable Long id) {
        return Result.success(tagService.getTagById(id));
    }

    @GetMapping("/slug/{slug}")
    public Result<TagDTO> getTagBySlug(@PathVariable String slug) {
        return Result.success(tagService.getTagBySlug(slug));
    }
}
