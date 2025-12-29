package com.example.blog.dto.response;

import com.example.blog.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommentDTO {

    private Long id;
    private String content;
    private String authorName;
    private String authorEmail;
    private String authorUrl;
    private String status;
    private Long articleId;
    private String articleTitle;
    private Long parentId;
    private List<CommentDTO> replies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDTO fromEntity(Comment comment) {
        CommentDTOBuilder builder = CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getUser() != null ? comment.getUser().getNickname() : comment.getAuthorName())
                .authorEmail(comment.getAuthorEmail())
                .authorUrl(comment.getAuthorUrl())
                .status(comment.getStatus().name())
                .articleId(comment.getArticle().getId())
                .articleTitle(comment.getArticle().getTitle())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt());

        if (comment.getParent() != null) {
            builder.parentId(comment.getParent().getId());
        }

        return builder.build();
    }

    public static CommentDTO fromEntityWithReplies(Comment comment) {
        CommentDTOBuilder builder = CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getUser() != null ? comment.getUser().getNickname() : comment.getAuthorName())
                .authorEmail(comment.getAuthorEmail())
                .authorUrl(comment.getAuthorUrl())
                .status(comment.getStatus().name())
                .articleId(comment.getArticle().getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt());

        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            builder.replies(comment.getReplies().stream()
                    .filter(reply -> reply.getStatus() == Comment.Status.APPROVED)
                    .map(CommentDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }
}
