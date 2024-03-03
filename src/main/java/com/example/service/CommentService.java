package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.*;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadException;
import com.example.repository.CommentRepository;
import com.example.repository.VideoRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    public CommentDTO create(CreateCommentDTO dto, AppLanguage language) {
        if (!videoRepository.existsById(dto.getVideoId())) {
            throw new AppBadException(resourceBundleService.getMessage("video.not.found", language));
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(dto.getContent());
        commentEntity.setProfileId(SpringSecurityUtil.getCurrentUser().getId());
        commentEntity.setVideoId(dto.getVideoId());
        commentEntity.setCreatedDate(LocalDateTime.now());
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

        if (commentEntity.getProfileId().equals(currentUser.getId())
            || currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)
            || getOwner(commentId, currentUser.getId(), language)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        throw new AppBadException(resourceBundleService.getMessage("this.comment.does.not", language));
    }


    public PageImpl<CommentDTO> commentListPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> all = commentRepository.findAll(pageable);
        long totalElements = all.getTotalElements();
        List<CommentDTO> list = new LinkedList<>();
        for (CommentEntity commentEntity : all) {
            list.add(toDTO(commentEntity));
        }
        return new PageImpl<>(list, pageable, totalElements);
    }

    public List<CommentListDTO> commentListByProfileId(Integer id, AppLanguage language) {
        List<CommentEntity> list = commentRepository.getByProfileId(id);
        List<CommentListDTO> commentListDTOS = new LinkedList<>();
        for (CommentEntity commentEntity : list) {
            commentListDTOS.add(getCommentListDTO(commentEntity, language));
        }
        return commentListDTOS;
    }

    public List<CommentListDTO> commentListByProfile(AppLanguage language) {
        Integer id = SpringSecurityUtil.getCurrentUser().getId();
        List<CommentEntity> list = commentRepository.getByProfileId(id);
        List<CommentListDTO> commentListDTOS = new LinkedList<>();
        for (CommentEntity commentEntity : list) {
            commentListDTOS.add(getCommentListDTO(commentEntity, language));
        }
        return commentListDTOS;
    }

    private Boolean getOwner(Integer commentId, Integer profileId, AppLanguage language) {
        String videoId = get(commentId, language).getVideoId();
        Integer channelId = videoService.get(videoId, language).getChannelId();
        ChannelEntity channelEntity = channelService.get(channelId, language);
        return channelEntity.getProfileId().equals(profileId);
    }

    public CommentDTO toDTO(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setReplyId(entity.getReplyId());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setVideoId(entity.getVideoId());
        dto.setCreatedDate(entity.getCreatedDate());
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

    public CommentListDTO getCommentListDTO(CommentEntity entity, AppLanguage language) {
        CommentListDTO dto = new CommentListDTO();
        dto.setId(entity.getId());
        dto.setLikeCount(entity.getLikeCount());
        dto.setDislikeCount(entity.getDislikeCount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setContent(entity.getContent());
        VideoDTO videoDTO = new VideoDTO();
        VideoEntity videoEntity = videoService.get(entity.getVideoId(), language);
        videoDTO.setId(videoEntity.getId());
        videoDTO.setTitle(videoEntity.getTitle());
        videoDTO.setDuration(videoEntity.getDuration());
        videoDTO.setPreviewId(videoEntity.getPreviewId());
        dto.setVideo(videoDTO);
        return dto;
    }

    public List<CommentInfoDTO> commentListByVideoId(String id, AppLanguage language) {
        List<CommentEntity> byVideoIdList = commentRepository.getByVideoId(id);
        List<CommentInfoDTO> list = new LinkedList<>();
        for (CommentEntity commentEntity : byVideoIdList) {
            list.add(getCommentInfoDTO(commentEntity, language));
        }
        return list;
    }

    public List<CommentInfoDTO> getCommentReplied(Integer commentId, AppLanguage language) {
        List<CommentEntity> comment = commentRepository.commentWrittenOnAComment(commentId);
        List<CommentInfoDTO> list = new LinkedList<>();
        for (CommentEntity commentEntity : comment) {
            list.add(getCommentInfoDTO(commentEntity, language));
        }
        return list;
    }

    public CommentInfoDTO getCommentInfoDTO(CommentEntity entity, AppLanguage language) {
        CommentInfoDTO dto = new CommentInfoDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setLikeCount(entity.getLikeCount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDislikeCount(entity.getDislikeCount());
        ProfileEntity profileEntity = profileService.get(entity.getProfileId(), language);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());

        AttachDTO attachDTO = attachService.getURL(profileEntity.getPhotoId());
        attachDTO.setId(profileEntity.getPhotoId());
        profileDTO.setPhotos(attachDTO);

        dto.setProfile(profileDTO);
        return dto;
    }
}
