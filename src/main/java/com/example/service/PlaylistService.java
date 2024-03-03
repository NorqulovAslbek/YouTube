package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.*;
import com.example.enums.AppLanguage;
import com.example.enums.PlaylistStatus;
import com.example.exp.AppBadException;
import com.example.mapper.PlayListInfoMapper;
import com.example.mapper.PlayListShortInfoMapper;
import com.example.mapper.PlayListShortMapper;
import com.example.repository.PlaylistRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public PlaylistDTO create(CreatePlaylistDTO dto, AppLanguage language) {
        PlaylistEntity entity = new PlaylistEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setOrderNum(dto.getOrderNum());
        entity.setStatus(dto.getStatus());
        playlistRepository.save(entity);
        return toDTO(entity);
    }

    private static PlaylistDTO toDTO(PlaylistEntity entity) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName(entity.getName());
        playlistDTO.setId(entity.getId());
        playlistDTO.setCreatedDate(entity.getCreatedDate());
        playlistDTO.setStatus(entity.getStatus());
        playlistDTO.setOrderNum(entity.getOrderNum());
        playlistDTO.setChannelId(entity.getChannelId());
        playlistDTO.setDescription(entity.getDescription());
        return playlistDTO;
    }

    public List<PlaylistDTO> getByChannelId(Integer id, AppLanguage language) {
        List<PlaylistEntity> optional = playlistRepository.getByChannelId(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("playlist.not.found", language));
        }
        List<PlaylistDTO> dtoList = new LinkedList<>();
        PlaylistDTO dto = new PlaylistDTO();
        for (PlaylistEntity entity : optional) {
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Boolean changeStatus(Integer id, PlaylistStatus status, AppLanguage language) {
        PlaylistEntity playlistEntity = get(id, language);
        checkUser(language, playlistEntity);
        playlistRepository.changeStatus(id, status);
        return true;
    }

    private void checkUser(AppLanguage language, PlaylistEntity playlistEntity) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        if (!playlistEntity.getChannel().getProfileId().equals(currentUser.getId())) {
            throw new AppBadException(resourceBundleService.getMessage("this.playlist.does.not.belong.to.you", language));
        }
    }

    public PlaylistEntity get(Integer id, AppLanguage language) {
        Optional<PlaylistEntity> optionalPlaylistEntity = playlistRepository.findByIdAndVisible(id, true);
        if (optionalPlaylistEntity.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("playlist.not.found", language));
        }
        return optionalPlaylistEntity.get();
    }

    public Boolean deletePlaylist(Integer id, AppLanguage language) {
        PlaylistEntity playlistEntity = get(id, language);
        checkUser(language, playlistEntity);
        playlistRepository.delete(playlistEntity.getId());
        return null;
    }

    public List<PlaylistDTO> getPlayList(String id, AppLanguage language) {
        List<PlaylistEntity> playList = playlistRepository.getPlayList(id);
        if (playList.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("playlist.not.found", language));
        }
        List<PlaylistDTO> playlistDTO = new LinkedList<>();
        for (PlaylistEntity entity : playList) {
            playlistDTO.add(toDTO2(entity));
        }
        return playlistDTO;
    }

    public PlaylistDTO toDTO2(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public PageImpl<PlayListInfoDTO> playlistPagination(Integer page, Integer size, AppLanguage language) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlayListInfoMapper> mapperList = playlistRepository.pagination(pageable);
        List<PlayListInfoDTO> dtoList = new LinkedList<>();
        for (PlayListInfoMapper infoMapper : mapperList) {
            dtoList.add(playlistInfoMapperToPlaylistDTO(infoMapper));
        }
        Long totalElements = playlistRepository.totalElements();
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    private PlayListInfoDTO playlistInfoMapperToPlaylistDTO(PlayListInfoMapper infoMapper) {
        PlayListInfoDTO dto = new PlayListInfoDTO();
        dto.setId(infoMapper.getId());
        dto.setName(infoMapper.getName());
        dto.setDescription(infoMapper.getDescription());
        dto.setStatus(infoMapper.getStatus());
        dto.setOrderNum(infoMapper.getOrderNum());
        dto.setChannelId(infoMapper.getChannelId());
        dto.setChannelName(infoMapper.getChannelName());
        dto.setChannelPhotoId(infoMapper.getChannelPhotoId());
        dto.setChannelPhotoUrl(infoMapper.getChannelPhotoUrl());
        dto.setProfileId(infoMapper.getProfileId());
        dto.setProfileName(infoMapper.getProfileName());
        dto.setProfileAttachId(infoMapper.getProfileAttachId());
        dto.setProfileAttachUrl(infoMapper.getProfileAttachUrl());
        return dto;
    }

    public List<PlayListInfoDTO> getListByUserId(Integer userId) {
        List<PlayListInfoMapper> mapperList = playlistRepository.getListByUserId(userId);
        List<PlayListInfoDTO> dtoList = new LinkedList<>();
        for (PlayListInfoMapper infoMapper : mapperList) {
            dtoList.add(playlistInfoMapperToPlaylistDTO(infoMapper));
        }
        return dtoList;
    }

    public List<PlayListShortInfoDTO> getAll(Integer profileId) {
        List<PlayListShortInfoMapper> listShortInfoMappers = playlistRepository.getAll(profileId);
        List<PlayListShortInfoDTO> dtoList = new LinkedList<>();
        for (PlayListShortInfoMapper infoMapper : listShortInfoMappers) {
            PlayListShortInfoDTO dto = mapperToPlayListShortInfoDTO(infoMapper);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private PlayListShortInfoDTO mapperToPlayListShortInfoDTO(PlayListShortInfoMapper infoMapper) {
        PlayListShortInfoDTO dto = new PlayListShortInfoDTO();
        dto.setId(infoMapper.getId());
        dto.setName(infoMapper.getName());
        dto.setCreatedDate(infoMapper.getCreatedDate());
        dto.setChannelId(infoMapper.getChannelId());
        dto.setChannelName(infoMapper.getChannelName());
        dto.setVideoId(infoMapper.getVideoId());
        dto.setVideoTitle(infoMapper.getVideoTitle());
        dto.setDuration(infoMapper.getDuration());
        return dto;
    }

    public Object getById(Integer id, AppLanguage language) {
        Optional<PlayListShortMapper> mapper = playlistRepository.getById(id);
        if (mapper.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("playListShortMapper.not.found", language));
        }
        PlayListShortMapper playListShortMapper = mapper.get();
        PlayListShortInfoDTO dto = new PlayListShortInfoDTO();
        dto.setId(playListShortMapper.getId());
        dto.setName(playListShortMapper.getName());
        dto.setViewCount(playListShortMapper.getViewCount());
        dto.setTotalViewCount(playListShortMapper.getTotalViewCount());
        dto.setUpdatedDate(playListShortMapper.getUpdatedDate());
        return dto;
    }
}
