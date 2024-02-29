package com.example.service;

import com.example.dto.CommentLikeDTO;
import com.example.dto.CreateCommentLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.repository.CommentLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public Object create(CreateCommentLikeDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        Optional<CommentLikeEntity> optional = commentLikeRepository.checkLike(dto.getCommentId(), profileId);
        if (optional.isEmpty()) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(dto.getCommentId());
            entity.setType(dto.getType());
            entity.setProfileId(profileId);
            commentLikeRepository.save(entity);
            return toDTO(entity);

        }
        CommentLikeEntity entity = optional.get();
        if (!dto.getType().equals(entity.getType())) {
            entity.setType(dto.getType());
            entity.setUpdatedDate(LocalDateTime.now());
            commentLikeRepository.updateType(dto.getType(), dto.getCommentId(), profileId);
            return toDTO(entity);
        }
        commentLikeRepository.delete(entity);
        return "comment " + entity.getType() +" removed";
    }

    private CommentLikeDTO toDTO(CommentLikeEntity entity) {
        CommentLikeDTO dto = new CommentLikeDTO();
        dto.setId(entity.getId());
        dto.setCommentId(entity.getCommentId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setType(entity.getType());
        dto.setProfileId(entity.getProfileId());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}
