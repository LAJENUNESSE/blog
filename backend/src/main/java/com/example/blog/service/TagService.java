package com.example.blog.service;

import com.example.blog.dto.request.TagRequest;
import com.example.blog.dto.response.TagDTO;
import com.example.blog.entity.Tag;
import com.example.blog.exception.BusinessException;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<TagDTO> getAllTagsPaged(Pageable pageable) {
        return tagRepository.findAll(pageable).map(TagDTO::fromEntity);
    }

    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Tag not found"));
        return TagDTO.fromEntity(tag);
    }

    public TagDTO getTagBySlug(String slug) {
        Tag tag = tagRepository.findBySlug(slug)
                .orElseThrow(() -> BusinessException.notFound("Tag not found"));
        return TagDTO.fromEntity(tag);
    }

    @Transactional
    public TagDTO createTag(TagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw BusinessException.badRequest("Tag name already exists");
        }

        Tag tag = new Tag();
        tag.setName(request.getName());
        tag.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getName()));

        tagRepository.save(tag);
        return TagDTO.fromEntitySimple(tag);
    }

    @Transactional
    public TagDTO updateTag(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Tag not found"));

        if (!tag.getName().equals(request.getName()) && tagRepository.existsByName(request.getName())) {
            throw BusinessException.badRequest("Tag name already exists");
        }

        tag.setName(request.getName());
        tag.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getName()));

        tagRepository.save(tag);
        return TagDTO.fromEntitySimple(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Tag not found"));

        if (!tag.getArticles().isEmpty()) {
            throw BusinessException.badRequest("Cannot delete tag with articles");
        }

        tagRepository.delete(tag);
    }

    private String generateSlug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }
}
