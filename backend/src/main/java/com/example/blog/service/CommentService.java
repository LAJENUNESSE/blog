package com.example.blog.service;

import com.example.blog.dto.request.CommentRequest;
import com.example.blog.dto.response.CommentDTO;
import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.exception.BusinessException;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<CommentDTO> getApprovedCommentsByArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> BusinessException.notFound("Article not found"));

        return commentRepository.findByArticleAndParentIsNullAndStatus(article, Comment.Status.APPROVED)
                .stream()
                .map(CommentDTO::fromEntityWithReplies)
                .collect(Collectors.toList());
    }

    public Page<CommentDTO> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable).map(CommentDTO::fromEntity);
    }

    public Page<CommentDTO> getCommentsByStatus(Comment.Status status, Pageable pageable) {
        return commentRepository.findByStatus(status, pageable).map(CommentDTO::fromEntity);
    }

    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Comment not found"));
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public CommentDTO createComment(Long articleId, CommentRequest request, String username, String ipAddress, String userAgent) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> BusinessException.notFound("Article not found"));

        if (!article.getAllowComment()) {
            throw BusinessException.badRequest("Comments are not allowed for this article");
        }

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setArticle(article);
        comment.setIpAddress(ipAddress);
        comment.setUserAgent(userAgent);

        if (username != null) {
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                comment.setUser(user);
                comment.setStatus(Comment.Status.APPROVED);
            }
        }

        if (comment.getUser() == null) {
            comment.setAuthorName(request.getAuthorName());
            comment.setAuthorEmail(request.getAuthorEmail());
            comment.setAuthorUrl(request.getAuthorUrl());
            comment.setStatus(Comment.Status.PENDING);
        }

        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> BusinessException.notFound("Parent comment not found"));
            if (!parent.getArticle().getId().equals(articleId)) {
                throw BusinessException.badRequest("Parent comment does not belong to this article");
            }
            comment.setParent(parent);
        }

        commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public CommentDTO approveComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Comment not found"));
        comment.setStatus(Comment.Status.APPROVED);
        commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public CommentDTO rejectComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Comment not found"));
        comment.setStatus(Comment.Status.REJECTED);
        commentRepository.save(comment);
        return CommentDTO.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw BusinessException.notFound("Comment not found");
        }
        commentRepository.deleteById(id);
    }

    public long getPendingCommentCount() {
        return commentRepository.countByStatus(Comment.Status.PENDING);
    }
}
