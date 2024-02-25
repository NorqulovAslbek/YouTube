package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.ChannelEntity;
import com.example.entity.VideoEntity;
import com.example.entity.VideoPermissionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.*;
import com.example.util.SpringSecurityUtil;
import com.example.repository.VideoPermissionRepository;
import com.example.repository.VideoRepository;
import com.example.repository.VideoSearchRepository;
import com.example.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoSearchRepository videoSearchRepository;
    @Autowired
    private ResourceBundleService bundleService;
    @Autowired
    private VideoPermissionRepository permissionRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private ChannelRepository channelRepository;

    public VideoDTO create(VideoCreateDTO dto, AppLanguage language, Integer profileId) {
        VideoEntity entity = new VideoEntity();

        if (!channelRepository.existsById(dto.getChannelId())) {
            throw new AppBadException(bundleService.getMessage("problem.do.not.channel", language));
        }
        if (!attachRepository.existsById(dto.getAttachId())) {
            throw new AppBadException(bundleService.getMessage("video.could.not.found", language));
        }

        if (dto.getType() == null && dto.getDescription() == null && dto.getStatus() == null && dto.getTitle() == null &&
                dto.getAttachId() == null && dto.getCategoryId() == null && dto.getChannelId() == null && dto.getPreviewAttachId() == null) {
            log.info("There is an error in what you sent {}", dto);
            throw new AppBadException(bundleService.getMessage("there.error.in.what.you.sent", language));
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setAttachId(dto.getAttachId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setPreviewId(dto.getPreviewAttachId());
        entity.setChannelId(dto.getChannelId());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(LocalDateTime.now());

        VideoEntity save = videoRepository.save(entity);
        VideoPermissionEntity permissionEntity = new VideoPermissionEntity();
        permissionEntity.setProfileId(profileId);
        permissionEntity.setVideoId(save.getId());
        permissionRepository.save(permissionEntity);

        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(entity.getId());
        videoDTO.setCreatedDate(entity.getCreatedDate());
        return videoDTO;
    }

    public Boolean updateStatus(UpdateStatusVideoDTO dto, AppLanguage language) {

        VideoEntity entity = get(dto.getId(), language);
        entity.setStatus(dto.getStatus());
        videoRepository.save(entity);
        return true;
    }


    public Boolean updateDetail(VideoUpdateDetailDTO dto, String videoId, AppLanguage language) {
        VideoEntity entity = get(videoId, language);
        entity.setDescription(dto.getDescription() != null ? dto.getDescription() : entity.getDescription());
        entity.setTitle(dto.getTitle() != null ? dto.getTitle() : entity.getAttachId());
        entity.setCategoryId(dto.getCategoryId() != null ? dto.getCategoryId() : entity.getCategoryId());
        entity.setPreviewId(dto.getPreviewAttachId() != null ? dto.getPreviewAttachId() : entity.getPreviewId());
        entity.setChannelId(dto.getChannelId() != null ? dto.getChannelId() : entity.getChannelId());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : entity.getStatus());
        videoRepository.save(entity);
        return true;
    }

    private VideoEntity get(String videoId, AppLanguage language) {
        Optional<VideoEntity> optionalVideoEntity = videoRepository.findById(videoId);
        if (optionalVideoEntity.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("video.not.found", language));
        }
        return optionalVideoEntity.get();
    }


    public PageImpl<VideoShortInfoDTO> getVideoByCategoryId(Integer id, Integer page, Integer size, AppLanguage language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<VideoPermissionEntity> permission = permissionRepository.getPermission(currentUser.getId(), pageable);
        long totalElements = permission.getTotalElements();
        List<VideoShortInfoDTO> shortInfoDTOS = new LinkedList<>();
        for (VideoPermissionEntity videoPermissionEntity : permission) {
            shortInfoDTOS.add(get(videoPermissionEntity.getVideoId(), id, language));
        }

        List<VideoEntity> categoryIdList = videoRepository.getByCategoryId(id);
        for (VideoEntity videoEntity : categoryIdList) {
            shortInfoDTOS.add(get(videoEntity.getId(), id, language));
        }
        return new PageImpl<>(shortInfoDTOS, pageable, totalElements);
    }

    private VideoShortInfoDTO get(String videoId, Integer categoryId, AppLanguage language) {
        Optional<VideoEntity> optional = videoRepository.getVideoByVideoIdAndCategoryId(videoId, categoryId);
        if (optional.isEmpty()) {
            log.info("Video not found {}", videoId);
            throw new AppBadException(bundleService.getMessage("video.not.found", language));
        }
        return getVideoShortInfoDTO(optional.get(), language);
    }

    private VideoShortInfoDTO getVideoShortInfoDTO(VideoEntity entity, AppLanguage language) {
        VideoShortInfoDTO dto = new VideoShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPreviewAttach(attachService.getURL(entity.getPreviewId()));
        dto.setPublishedDate(entity.getPublishedDate());

        ChannelEntity channelEntity = channelService.get(entity.getChannelId(), language);
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(channelEntity.getId());
        channelDTO.setName(channelEntity.getName());
        if (channelEntity.getPhotoId() != null) {
            AttachDTO attachDTO = attachService.getURL(channelEntity.getPhotoId());
            channelDTO.setPhotoId(attachDTO.getUrl());
        }
        dto.setViewCount(entity.getViewCount());

        dto.setChannel(channelDTO);
        return dto;
    }

    public PageImpl<VideoShortInfoDTO> searchVideoByTitle(Integer page, Integer size, VideoFilterDTO dto, AppLanguage language) {
        PaginationResultDTO<VideoEntity> filter = videoSearchRepository.filter(dto, page, size);

        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        for (VideoEntity entity : filter.getList()) {
            dtoList.add(getVideoShortInfoDTO(entity, language));
        }
        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, filter.getTotalSize());
    }

    public Long increaseVideoViewCountById(String id, AppLanguage language) {
        VideoEntity video = get(id, language);
        video.setViewCount(video.getViewCount() + 1);
        videoRepository.save(video);

        return video.getViewCount();
    }
}
