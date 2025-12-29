package com.example.blog.controller;

import com.example.blog.common.Result;
import com.example.blog.dto.request.CommentRequest;
import com.example.blog.dto.response.CommentDTO;
import com.example.blog.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/article/{articleId}")
    public Result<List<CommentDTO>> getCommentsByArticle(@PathVariable Long articleId) {
        return Result.success(commentService.getApprovedCommentsByArticle(articleId));
    }

    @PostMapping("/article/{articleId}")
    public Result<CommentDTO> createComment(
            @PathVariable Long articleId,
            @Valid @RequestBody CommentRequest request,
            Authentication authentication,
            HttpServletRequest httpRequest) {
        String username = authentication != null ? authentication.getName() : null;
        String ipAddress = getClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        return Result.success(commentService.createComment(articleId, request, username, ipAddress, userAgent));
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        return request.getRemoteAddr();
    }
}
