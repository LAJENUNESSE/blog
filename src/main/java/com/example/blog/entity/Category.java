package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends BaseEntity {

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(length = 100)
    private String slug;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private Integer sortOrder = 0;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();
}
