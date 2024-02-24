package com.example.service;

import com.example.dto.VideoCreateDTO;
import com.example.dto.VideoUpdateDetailDTO;
import com.example.entity.VideoEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ResourceBundleService bundleService;

    public VideoCreateDTO create(VideoCreateDTO dto, AppLanguage language) {
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
        return dto;
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
}
