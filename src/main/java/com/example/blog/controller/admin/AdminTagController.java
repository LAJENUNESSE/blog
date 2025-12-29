package com.example.blog.controller.admin;

import com.example.blog.common.PageResult;
import com.example.blog.common.Result;
import com.example.blog.dto.request.TagRequest;
import com.example.blog.dto.response.TagDTO;
import com.example.blog.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tags")
@RequiredArgsConstructor
public class AdminTagController {

    private final TagService tagService;

    @GetMapping
    public Result<List<TagDTO>> getAllTags() {
        return Result.success(tagService.getAllTags());
    }

    @GetMapping("/paged")
    public Result<PageResult<TagDTO>> getAllTagsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return Result.success(PageResult.of(tagService.getAllTagsPaged(pageable)));
    }

    @GetMapping("/{id}")
    public Result<TagDTO> getTagById(@PathVariable Long id) {
        return Result.success(tagService.getTagById(id));
    }

    @PostMapping
    public Result<TagDTO> createTag(@Valid @RequestBody TagRequest request) {
        return Result.success(tagService.createTag(request));
    }

    @PutMapping("/{id}")
    public Result<TagDTO> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request) {
        return Result.success(tagService.updateTag(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return Result.success();
    }
}
