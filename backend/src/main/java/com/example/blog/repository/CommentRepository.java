package com.example.blog.repository;

import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByArticle(Article article, Pageable pageable);

    Page<Comment> findByArticleAndStatus(Article article, Comment.Status status, Pageable pageable);

    List<Comment> findByArticleAndParentIsNullAndStatus(Article article, Comment.Status status);

    Page<Comment> findByStatus(Comment.Status status, Pageable pageable);

    long countByArticle(Article article);

    long countByArticleAndStatus(Article article, Comment.Status status);

    long countByStatus(Comment.Status status);
}
