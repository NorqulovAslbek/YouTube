package com.example.service;

import com.example.dto.CreatePlaylistDTO;
import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.MidiFileFormat;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

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

    public PlaylistDTO getByChannelId(Integer id) {
        Optional<PlaylistEntity> optional = playlistRepository.getByChannelId(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Play list not found");
        }
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(optional.get().getId());
        dto.setName(optional.get().getName());
        return dto;
    }
}
