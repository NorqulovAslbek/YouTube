package com.example.service;

import com.example.dto.TagDTO;
import com.example.dto.VideoTagCreateDTO;
import com.example.dto.VideoTagDTO;
import com.example.entity.TagEntity;
import com.example.entity.VideoTagEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.TagRepository;
import com.example.repository.VideoRepository;
import com.example.repository.VideoTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VideoTagService {
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private ResourceBundleService bundleService;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private TagRepository tagRepository;

    public VideoTagDTO create(VideoTagCreateDTO dto, AppLanguage language) {
        VideoTagEntity videoTagEntity = new VideoTagEntity();

        if (!videoRepository.existsById(dto.getVideoId())) {
            throw new AppBadException(bundleService.getMessage("video.not.found", language));
        }
        if (!tagRepository.existsById(dto.getTagId())) {
            throw new AppBadException(bundleService.getMessage("tag.not.found", language));
        }
        if (dto.getTagId() == null && dto.getVideoId() == null) {
            log.info("video tag not found {}", dto);
            throw new AppBadException(bundleService.getMessage("video.tag.not.found", language));
        }
        videoTagEntity.setVideoId(dto.getVideoId());
        videoTagEntity.setTagId(dto.getTagId());
        videoTagEntity.setCreatedDate(LocalDateTime.now());
        videoTagRepository.save(videoTagEntity);
        VideoTagDTO videoTagDTO = new VideoTagDTO();
        videoTagDTO.setId(videoTagEntity.getId());
        videoTagDTO.setVideoId(videoTagEntity.getVideoId());
        videoTagDTO.setTagId(videoTagEntity.getTagId());
        videoTagDTO.setCreatedDate(videoTagEntity.getCreatedDate());
        return videoTagDTO;
    }

    public Boolean delete(VideoTagCreateDTO dto, AppLanguage language) {
        VideoTagEntity entity = get(dto, language);
        videoTagRepository.delete(entity);
        return true;
    }

    public VideoTagEntity get(VideoTagCreateDTO dto, AppLanguage language) {
        Optional<VideoTagEntity> optional = videoTagRepository.getVideoById(dto.getVideoId());
        if (optional.isEmpty()) {
            log.info("video tag not found {}", dto);
            throw new AppBadException(bundleService.getMessage("video.tag.not.found", language));
        }
        return optional.get();
    }

    public List<VideoTagDTO> getVideoTagList(String videoId, AppLanguage language) {
        List<VideoTagEntity> entityList = videoTagRepository.findVideoById(videoId);
        if (entityList.isEmpty()) {
            log.info("video tag not found {}", videoId);
            throw new AppBadException(bundleService.getMessage("video.tag.not.found", language));
        }
        List<VideoTagDTO> tagList = new LinkedList<>();

        for (VideoTagEntity entity : entityList) {
            tagList.add(toDTO(entity));
        }
        return tagList;
    }

    public VideoTagDTO toDTO(VideoTagEntity entity) {
        VideoTagDTO dto = new VideoTagDTO();
        dto.setId(entity.getId());
        dto.setVideoId(entity.getVideoId());
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(entity.getTag().getId());
        tagDTO.setName(entity.getTag().getName());
        dto.setTag(tagDTO);
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}


