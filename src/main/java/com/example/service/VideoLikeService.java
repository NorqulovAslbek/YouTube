package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.AttachEntity;
import com.example.entity.ChannelEntity;
import com.example.entity.VideoEntity;
import com.example.entity.VideoLikeEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.*;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private AttachService attachService;


    public VideoLikeInfoDTO create(CreateVideoLikeDTO dto, AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        Optional<VideoLikeEntity> optional = videoLikeRepository.checkLike(dto.getVideoId(), profileId);
        if (optional.isPresent()) {
            VideoLikeEntity videoLikeEntity = optional.get();
            videoLikeEntity.setType(dto.getType());
            videoLikeEntity.setUpdatedDate(LocalDateTime.now());
            videoLikeRepository.save(videoLikeEntity);
            return getVideoLikeInfoDTO(videoLikeEntity, language);
        }
        getVideoById(dto.getVideoId(), language); //check qilish uchun vidio id bormi yoqmi
        VideoLikeEntity videoLikeEntity = videoLikeRepository.save(toEntity(dto, profileId));
        return getVideoLikeInfoDTO(videoLikeEntity, language);
    }


    public List<VideoLikeInfoDTO> getUserLikedVideoList(AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        List<VideoLikeEntity> likeEntityList = videoLikeRepository.getByProfileId(profileId);
        List<VideoLikeInfoDTO> list = new LinkedList<>();
        for (VideoLikeEntity videoLikeEntity : likeEntityList) {
            list.add(getVideoLikeInfoDTO(videoLikeEntity, language));
        }
        return list;
    }

    public List<VideoLikeInfoDTO> getGetUserLikedVideoListByUserId(Integer profileId, AppLanguage language) {
        List<VideoLikeEntity> likeEntityList = videoLikeRepository.getByProfileId(profileId);
        List<VideoLikeInfoDTO> list = new LinkedList<>();
        for (VideoLikeEntity videoLikeEntity : likeEntityList) {
            list.add(getVideoLikeInfoDTO(videoLikeEntity, language));
        }
        return list;
    }

    public boolean remove(String videoId, AppLanguage language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        Integer effectiveRow = videoLikeRepository.deleteByAttachIdAndProfileId(videoId, currentUser.getId());
        return effectiveRow != 0;
    }


    public VideoLikeEntity toEntity(CreateVideoLikeDTO dto, Integer profileId) {
        VideoLikeEntity entity = new VideoLikeEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setProfileId(profileId);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setType(dto.getType());
        return entity;
    }


    public VideoLikeInfoDTO getVideoLikeInfoDTO(VideoLikeEntity entity, AppLanguage language) {
        VideoLikeInfoDTO dto = new VideoLikeInfoDTO();
        dto.setId(entity.getId());
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(entity.getVideoId());
        VideoEntity videoEntity = getVideoById(entity.getVideoId(), language);
        videoDTO.setTitle(videoEntity.getTitle());
        ChannelEntity channelEntity = getChannelById(videoEntity.getChannelId(), language);
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(channelEntity.getId());
        channelDTO.setName(channelEntity.getName());
        videoDTO.setChannel(channelDTO);
        dto.setVideo(videoDTO);
        AttachDTO attachDTO = new AttachDTO();
        AttachEntity attachEntity = getAttachById(videoEntity.getAttachId(), language);
        attachDTO.setId(attachEntity.getId());
        attachDTO.setUrl(attachService.getURL(attachEntity.getId()).getUrl());
        dto.setPreviewAttach(attachDTO);
        return dto;
    }

    private VideoEntity getVideoById(String videoId, AppLanguage language) {
        Optional<VideoEntity> optionalVideo = videoRepository.findById(videoId);
        if (optionalVideo.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("video.not.found", language));
        }
        return optionalVideo.get();
    }

    private ChannelEntity getChannelById(Integer id, AppLanguage language) {
        Optional<ChannelEntity> optionalChannel = channelRepository.findById(id);
        if (optionalChannel.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("channel.not.found", language));
        }
        return optionalChannel.get();
    }

    private AttachEntity getAttachById(String id, AppLanguage language) {
        Optional<AttachEntity> optionalAttach = attachRepository.findById(id);
        if (optionalAttach.isEmpty()) {
            throw new AppBadException("attach not found");
        }
        return optionalAttach.get();
    }

}
