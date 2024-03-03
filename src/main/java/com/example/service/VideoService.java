package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.ChannelEntity;
import com.example.entity.VideoEntity;
import com.example.entity.VideoPermissionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.mapper.VideoFullInfoMapper;
import com.example.mapper.VideoPlayListInfoMapper;
import com.example.mapper.VideoShortInfoMapper;
import com.example.mapper.VideoShortInfoPaginationMapper;
import com.example.repository.*;
import com.example.util.SpringSecurityUtil;
import com.example.repository.VideoPermissionRepository;
import com.example.repository.VideoRepository;
import com.example.repository.VideoSearchRepository;
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
        if (!attachRepository.existsById(dto.getPreviewAttachId())) {
            throw new AppBadException(bundleService.getMessage("attach.not.found", language));
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

    public VideoEntity get(String videoId, AppLanguage language) {
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

    public PageImpl<VideoShortInfoDTO> getVideoByTagId(Integer page, Integer size, String tagId, AppLanguage language) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<VideoEntity> entityPage = videoRepository.findAll(pageable);
        List<VideoEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();
        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        for (VideoEntity entity : entityList) {
            dtoList.add(getVideoShortInfoDTO(entity, language));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    public PageImpl<VideoListPaginationDTO> getVideoList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoShortInfoPaginationMapper> entityPage = videoRepository.getVideoListForAdmin(pageable);
        List<VideoShortInfoPaginationMapper> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();
        List<VideoListPaginationDTO> dtoList = new LinkedList<>();
        VideoDTO videoDTO = new VideoDTO();
        for (VideoShortInfoPaginationMapper entity : entityList) {
            videoDTO.setId(entity.getId());
            videoDTO.setTitle(entity.getTitle());
            if (entity.getPreviewAttachId() != null) {
                AttachDTO attachDTO = new AttachDTO();
                attachDTO.setUrl(attachService.getURL(entity.getPreviewAttachId()).getUrl());
            }
            videoDTO.setPublishedDate(entity.getPublishedDate());
            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(entity.getChannelId());
            channelDTO.setName(entity.getChannelName());
            channelDTO.setPhotoId(entity.getPhotoId());
            videoDTO.setChannel(channelDTO);
            videoDTO.setViewCount(entity.getViewCount());
            videoDTO.setDuration(entity.getDuration());
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(entity.getProfileId());
            profileDTO.setName(entity.getProfileName());
            profileDTO.setSurname(entity.getProfileSurname());
            videoDTO.setOwner(profileDTO);
            videoDTO.setPlayListJson(entity.getPlayListJson());
            VideoListPaginationDTO dto = new VideoListPaginationDTO();
            dto.setVideo(videoDTO);
            dto.setChannel(channelDTO);
            dto.setProfile(profileDTO);
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
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
            dto.setDuration(attachDTO.getDuration());
        }
        dto.setViewCount(entity.getViewCount());
        dto.setChannel(channelDTO);
        return dto;
    }

    private VideoShortInfoDTO getVideoShortInfoToDTO(VideoShortInfoMapper entity) {
        VideoShortInfoDTO dto = new VideoShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        PreviewAttachDTO previewAttachDTO = new PreviewAttachDTO();
        if (entity.getPreviewAttachId() != null) {
            previewAttachDTO.setId(entity.getPreviewAttachId());
            previewAttachDTO.setUrl(attachService.getURL(entity.getPreviewAttachId()).getUrl());
        }
        dto.setPreviewDto(previewAttachDTO);
        dto.setPublishedDate(entity.getPublishedDate());
        ChannelDTO channelDTO = new ChannelDTO();
        if (entity.getChannelId() != null) {
            channelDTO.setId(entity.getChannelId());
            channelDTO.setName(entity.getChannelName());
            channelDTO.setPhotoId(entity.getPhotoId());
        }
        dto.setChannel(channelDTO);
        dto.setViewCount(entity.getViewCount());
        dto.setDuration(entity.getDuration());
        dto.setChannel(channelDTO);
        return dto;
    }

    public PageImpl<VidePlayListInfoDTO> getChannelVideoListPagination(Integer page, Integer size, AppLanguage language) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoPlayListInfoMapper> entityPage = videoRepository.getChannelVideoList(pageable);
        List<VideoPlayListInfoMapper> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();
        List<VidePlayListInfoDTO> dtoList = new LinkedList<>();
        for (VideoPlayListInfoMapper mapperEntity : entityList) {
            dtoList.add(getVideoPlayList(mapperEntity, language));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    private VidePlayListInfoDTO getVideoPlayList(VideoPlayListInfoMapper mapperEntity, AppLanguage language) {
        VidePlayListInfoDTO dto = new VidePlayListInfoDTO();
        dto.setId(mapperEntity.getVideoId());
        dto.setViewCount(mapperEntity.getViewCount());
        PreviewAttachDTO previewAttachDTO = new PreviewAttachDTO();
        if (mapperEntity.getPreviewAttachId() != null) {
            previewAttachDTO.setId(mapperEntity.getPreviewAttachId());
            previewAttachDTO.setUrl(attachService.getURL(previewAttachDTO.getId()).getUrl());
        }
        dto.setPreviewAttach(previewAttachDTO);
        dto.setTitle(mapperEntity.getTitle());
        dto.setPublishedDate(mapperEntity.publishedDate());
        dto.setDuration(mapperEntity.getDuration());
        return dto;
    }

    public VideoFullInfoDTO getVideoById(String id, AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        Optional<VideoFullInfoMapper> optional = videoRepository.getVideoFullInfo(id, profileId);
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("video.not.found", language));
        }
        return getVideoFullInfoDTO(optional.get());
    }

    private VideoFullInfoDTO getVideoFullInfoDTO(VideoFullInfoMapper video) {
        VideoFullInfoDTO dto = new VideoFullInfoDTO();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        PreviewAttachDTO previewAttachDTO = new PreviewAttachDTO();
        previewAttachDTO.setId(video.getPreviewAttachId());
        if (video.getPreviewAttachId() != null) {
            previewAttachDTO.setUrl(attachService.getURL(video.getPreviewAttachId()).getUrl());
        }
        dto.setPreviewAttach(previewAttachDTO);
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(video.getAttachId());
        attachDTO.setDuration(video.getAttachDuration());
        if (video.getAttachId() != null) {
            attachDTO.setUrl(attachService.getURL(video.getAttachId()).getUrl());
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(video.getCategoryId());
        categoryDTO.setName(video.getCategoryName());
        dto.setCategory(categoryDTO);
        dto.setTagList(video.getTagListJson());
        dto.setPublishedDate(video.getPublishedDate());
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(video.getChannelId());
        channelDTO.setName(video.getChannelName());
        if (video.getChannelId() != null) {
            channelDTO.setPhotoId(attachService.getURL(video.getPhotoId()).getUrl());
        }
        dto.setChannel(channelDTO);
        dto.setViewCount(video.getViewCount());
        dto.setSharedCount(video.getSharedCount());
        dto.setLikeCount(video.getLikeCount());
        dto.setDislikeCount(video.getDislikeCount());
        dto.setDuration(video.getDuration());
        return dto;
    }
}
