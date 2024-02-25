package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.CommentDTO;
import com.example.dto.CreateCommentDTO;
import com.example.dto.UpdateCommentDTO;
import com.example.entity.ChannelEntity;
import com.example.entity.CommentEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.repository.CommentRepository;
import com.example.repository.VideoRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChannelService channelService;

    public CommentDTO create(CreateCommentDTO dto, AppLanguage language) {
        if (!videoRepository.existsById(dto.getVideoId())) {
            throw new AppBadException(resourceBundleService.getMessage("video.not.found", language));
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(dto.getContent());
        commentEntity.setProfileId(SpringSecurityUtil.getCurrentUser().getId());
        commentEntity.setVideoId(dto.getVideoId());
        if (dto.getReplyId() != null) {
            commentEntity.setReplyId(dto.getReplyId());
        }
        CommentEntity comment = commentRepository.save(commentEntity);
        return toDTO(comment);
    }

    public CommentDTO update(Integer commentId, UpdateCommentDTO dto, AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        CommentEntity commentEntity = get(commentId, language);
        if (!commentEntity.getProfileId().equals(profileId)) {
            throw new AppBadException(resourceBundleService.getMessage("this.comment.does.not", language));
        }
        commentEntity.setContent(dto.getContent());
        CommentEntity updateComment = commentRepository.save(commentEntity);
        return toDTO(updateComment);
    }

    public boolean delete(Integer commentId, AppLanguage language) {
        CommentEntity commentEntity = get(commentId, language);
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        String videoId = get(commentId, language).getVideoId();
        Integer channelId = videoService.get(videoId, language).getChannelId();
        ChannelEntity channelEntity = channelService.get(channelId, language);
        if (commentEntity.getProfileId().equals(currentUser.getId())
                || currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)
                || channelEntity.getProfileId().equals(currentUser.getId())) {
            commentRepository.deleteById(commentId);
            return true;
        }
        throw new AppBadException(resourceBundleService.getMessage("this.comment.does.not", language));
    }

    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setReplyId(entity.getReplyId());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setVideoId(entity.getVideoId());
        dto.setContent(entity.getContent());
        return dto;
    }

    public CommentEntity get(Integer commentId, AppLanguage language) {
        Optional<CommentEntity> optional = commentRepository.findById(commentId);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("comment.not.found", language));
        }
        return optional.get();
    }
}
