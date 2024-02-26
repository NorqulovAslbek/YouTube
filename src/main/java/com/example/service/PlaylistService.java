package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.CreatePlaylistDTO;
import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.enums.AppLanguage;
import com.example.enums.PlaylistStatus;
import com.example.exp.AppBadException;
import com.example.repository.PlaylistRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

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

    private PlaylistEntity get(Integer id, AppLanguage language) {
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

    public List<PlaylistDTO> getListByUserId(Integer id, AppLanguage language) {

        playlistRepository.
    }
}
