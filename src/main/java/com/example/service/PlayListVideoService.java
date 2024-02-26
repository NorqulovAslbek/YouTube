package com.example.service;

import com.example.dto.CreatePlayListVideoDTO;
import com.example.dto.DeletePlayListVideoDTO;
import com.example.dto.PlayListVideoDTO;
import com.example.entity.PlayListVideoEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.PlayListVideoRepository;
import com.example.repository.PlaylistRepository;
import com.example.repository.VideoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PlayListVideoService {

    @Autowired
    private PlayListVideoRepository playListVideoRepository;
    @Autowired
    private ResourceBundleService bundleService;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private VideoRepository videoRepository;

    public PlayListVideoDTO create(CreatePlayListVideoDTO dto, AppLanguage language) {
        PlayListVideoEntity entity = new PlayListVideoEntity();

        if (!videoRepository.existsById(dto.getVideoId())) {
            throw new AppBadException(bundleService.getMessage("video.not.found", language));
        }
        if (!playlistRepository.existsById(dto.getPlaylistId())) {
            throw new AppBadException(bundleService.getMessage("playlist.not.found", language));
        }
        entity.setVideoId(dto.getVideoId());
        entity.setPlayListId(dto.getPlaylistId());
        entity.setOrderNum(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());

        playListVideoRepository.save(entity);

        PlayListVideoDTO playListVideoDTO = new PlayListVideoDTO();
        playListVideoDTO.setId(entity.getId());
        playListVideoDTO.setVideoId(entity.getVideoId());
        playListVideoDTO.setPlaylistId(entity.getPlayListId());
        playListVideoDTO.setOrderNum(entity.getOrderNum());
        playListVideoDTO.setCreatedDate(entity.getCreatedDate());

        return playListVideoDTO;
    }

    public Boolean update(Integer id, CreatePlayListVideoDTO dto, AppLanguage language) {
        PlayListVideoEntity entity = get(id, language);
        entity.setVideoId(dto.getVideoId());
        entity.setPlayListId(dto.getPlaylistId());
        entity.setOrderNum(entity.getOrderNum());
        entity.setUpdatedDate(LocalDateTime.now());
        playListVideoRepository.save(entity);
        return true;
    }

    private PlayListVideoEntity get(Integer id, AppLanguage language) {
        Optional<PlayListVideoEntity> optional = playListVideoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("playlist.not.found", language));
        }
        return optional.get();
    }

    public Boolean delete(Integer id, DeletePlayListVideoDTO dto, AppLanguage language) {
        PlayListVideoEntity entity = get(id, language);
        playListVideoRepository.deleteByVideoIdAndPlayListId(dto.getVideoId(), dto.getPlaylistId());
        return true;
    }

}
