package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.*;
import com.example.enums.AppLanguage;
import com.example.enums.PlaylistStatus;
import com.example.exp.AppBadException;
import com.example.exp.AppBadException;
import com.example.repository.PlaylistRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.sound.midi.MidiFileFormat;
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

    public PageImpl<PlayListInfoDTO> playlistPagination(int page, Integer size, AppLanguage language) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlaylistEntity> entityPage = playlistRepository.findAll(pageable);

        List<PlaylistEntity> entityList = entityPage.getContent();
        long totalElements = entityPage.getTotalElements();

        List<PlayListInfoDTO> dtoList = new LinkedList<>();
        for (PlaylistEntity entity : entityList) {
            dtoList.add(getVideoPlayList(entity, language));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    private PlayListInfoDTO getVideoPlayList(PlaylistEntity entity, AppLanguage language) {
        PlayListInfoDTO dto = new PlayListInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOrderNum(entity.getOrderNum());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());

        ChannelEntity channelEntity = entity.getChannel();
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(channelEntity.getId());
        channelDTO.setName(channelEntity.getName());

        AttachEntity photoEntity = channelEntity.getPhoto();
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(photoEntity.getId());
        attachDTO.setUrl(photoEntity.getUrl());

        channelDTO.setPhoto(attachDTO);

        dto.setChannel(channelDTO);

        ProfileEntity profile = channelEntity.getProfile();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        AttachEntity photo = profile.getPhoto();
        AttachDTO profileAttach = new AttachDTO();
        profileAttach.setId(photo.getId());
        profileAttach.setUrl(profileAttach.getUrl());
        profileDTO.setAttach(profileAttach);

        dto.setProfile(profileDTO);


        return dto;
    }


    public List<PlaylistDTO> getListByUserId(Integer id, AppLanguage language) {
        return null;
    }
}
