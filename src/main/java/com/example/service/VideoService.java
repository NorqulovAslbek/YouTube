package com.example.service;

import com.example.dto.*;
import com.example.entity.AttachEntity;
import com.example.entity.ChannelEntity;
import com.example.entity.VideoEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.VideoRepository;
import com.example.repository.VideoSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public VideoDTO create(VideoCreateDTO dto, AppLanguage language) {
        VideoEntity entity = new VideoEntity();

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

        videoRepository.save(entity);
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

    public VideoEntity get(String id, AppLanguage language) {
        Optional<VideoEntity> optional = videoRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("Video not found {}", id);
            throw new AppBadException(bundleService.getMessage("video.not.found", language));
        }
        return optional.get();
    }


    public PageImpl<VideoShortInfoDTO> searchVideoByTitle(Integer page, Integer size, VideoFilterDTO dto) {
        PaginationResultDTO<VideoEntity> filter = videoSearchRepository.filter(dto, page, size);

        List<VideoShortInfoDTO> dtoList = new LinkedList<>();
        for (VideoEntity entity : filter.getList()) {
            dtoList.add(toDTO(entity));
        }
        Pageable paging = PageRequest.of(page - 1, size);
        return new PageImpl<>(dtoList, paging, filter.getTotalSize());
    }

    public VideoShortInfoDTO toDTO(VideoEntity entity) {
        VideoShortInfoDTO dto = new VideoShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        AttachEntity preview = entity.getPreview();
        AttachDTO attach = new AttachDTO();
        attach.setId(preview.getId());
        attach.setUrl(preview.getUrl());
        dto.setPreviewAttach(attach);

        ChannelEntity channelEntity = entity.getChannel();
        ChannelDTO channel = new ChannelDTO();
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setUrl(channelEntity.getPhoto().getUrl());

        channel.setId(channelEntity.getId());
        channel.setName(channelEntity.getName());
        channel.setPhoto(attachDTO);

        dto.setViewCount(entity.getViewCount());
        dto.setDuration(attachDTO.getDuration());
        return dto;
    }

}
