package com.example.blog.controller.admin;

import com.example.blog.common.PageResult;
import com.example.blog.common.Result;
import com.example.blog.dto.response.CommentDTO;
import com.example.blog.entity.Comment;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping
    public Result<PageResult<CommentDTO>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (status != null && !status.isEmpty()) {
            return Result.success(PageResult.of(commentService.getCommentsByStatus(Comment.Status.valueOf(status), pageable)));
        }
        return Result.success(PageResult.of(commentService.getAllComments(pageable)));
    }

    @GetMapping("/{id}")
    public Result<CommentDTO> getCommentById(@PathVariable Long id) {
        return Result.success(commentService.getCommentById(id));
    }

    @GetMapping("/pending/count")
    public Result<Long> getPendingCount() {
        return Result.success(commentService.getPendingCommentCount());
    }

    @PostMapping("/{id}/approve")
    public Result<CommentDTO> approveComment(@PathVariable Long id) {
        return Result.success(commentService.approveComment(id));
    }

    @PostMapping("/{id}/reject")
    public Result<CommentDTO> rejectComment(@PathVariable Long id) {
        return Result.success(commentService.rejectComment(id));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return Result.success();
    }
}
