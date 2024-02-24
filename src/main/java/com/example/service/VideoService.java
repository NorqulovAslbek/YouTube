package com.example.service;

import com.example.dto.VideoCreateDTO;
import com.example.dto.VideoUpdateDetailDTO;
import com.example.entity.VideoEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
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

        if (!(dto.getAttachId().equals(attachService.get(dto.getAttachId())) && dto.getChannelId().equals(channelService.get(dto.getChannelId(), language)))) {
            log.info("There is an error in what you sent {}", dto.getAttachId());
            throw new AppBadException(bundleService.getMessage("there.error.in.what.you.sent", language));
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

        videoRepository.save(entity);
        return dto;
    }


    public Object updateDetail(VideoUpdateDetailDTO dto, String videoId, AppLanguage language) {
        Optional<VideoEntity> optionalVideoEntity = videoRepository.findById(videoId);
        if (optionalVideoEntity.isEmpty()) {
        }
        return null;
    }
}
