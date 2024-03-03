package com.example.service;

import com.example.dto.*;
import com.example.entity.*;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.PlayListVideoRepository;
import com.example.repository.PlaylistRepository;
import com.example.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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
    @Autowired
    private ChannelService channelService;


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
        entity.setOrderNum(dto.getOrderNumber());
        entity.setUpdatedDate(LocalDateTime.now());
        playListVideoRepository.save(entity);
        return true;
    }

    private PlayListVideoEntity get(Integer id, AppLanguage language) {
        Optional<PlayListVideoEntity> optional = playListVideoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("playlist.video.not.found", language));
        }
        return optional.get();
    }

    public Boolean delete(Integer id, AppLanguage language) {
        PlayListVideoEntity entity = get(id, language);
        playListVideoRepository.delete(entity);
        return true;
    }

    public List<PlaylistVideoInfoDTO> getVideoList(Integer id, AppLanguage language) {
        List<PlayListVideoEntity> allByPlayListId = playListVideoRepository.getAllByPlayListId(id);
        if (allByPlayListId.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("playlist.video.not.found", language));
        }
        List<PlaylistVideoInfoDTO> list = new LinkedList<>();
        for (PlayListVideoEntity entity : allByPlayListId) {
            list.add(getPlayListInfo(entity, language));
        }
        return list;

    }

    private PlaylistVideoInfoDTO getPlayListInfo(PlayListVideoEntity entity, AppLanguage language) {
        PlaylistVideoInfoDTO dto = new PlaylistVideoInfoDTO();
        dto.setPlaylistId(entity.getPlayListId());
        VideoEntity video = entity.getVideo();
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(video.getId()); //video(id,(preview_attach(id,url),title,duration)
        AttachEntity preview = video.getPreview();
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(preview.getId());
        attachDTO.setUrl(preview.getUrl());
        videoDTO.setAttach(attachDTO);
        videoDTO.setTitle(video.getTitle());
        videoDTO.setDuration(video.getDuration());
        ChannelEntity channel = video.getChannel();
        ChannelDTO channelDTO = channelService.getById(channel.getId(), language);
        ChannelDTO channelDto = new ChannelDTO();
        channelDto.setId(channelDTO.getId());
        channelDto.setName(channelDTO.getName()); //chanel(id,name)
        dto.setVideo(videoDTO);
        dto.setChannel(channelDto);
        dto.setOrderNum(entity.getOrderNum());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
