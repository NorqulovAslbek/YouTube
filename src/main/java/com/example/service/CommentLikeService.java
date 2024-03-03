package com.example.service;

import com.example.dto.CommentLikeDTO;
import com.example.dto.CreateCommentLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.CommentLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

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
        return "comment " + entity.getType() + " removed";
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

    public Object remove(Integer id, AppLanguage language) {
        CommentLikeEntity entity = get(id, language);
        commentLikeRepository.delete(entity);
        return true;
    }

    public CommentLikeEntity get(Integer commentLikeId, AppLanguage language) {
        Optional<CommentLikeEntity> optionalVideoEntity = commentLikeRepository.findById(commentLikeId);
        if (optionalVideoEntity.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("comment.like.not.found", language));
        }
        return optionalVideoEntity.get();
    }

    public List<CommentLikeDTO> getLikedList(AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        return getCommentLikeDTOS(profileId, language);
    }

    public List<CommentLikeDTO> getUserLikedCommentListByUserId(Integer profileId, AppLanguage language) {
        return getCommentLikeDTOS(profileId, language);
    }

    @NotNull
    private List<CommentLikeDTO> getCommentLikeDTOS(Integer profileId, AppLanguage language) {
        List<CommentLikeEntity> list = commentLikeRepository.getList(profileId);
        if (list.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("comment.like.not.found", language));
        }
        List<CommentLikeDTO> dtoList = new ArrayList<>();
        for (CommentLikeEntity entity : list) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }
}
