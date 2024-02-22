package com.example.service;

import com.example.dto.ChannelCrudDTO;
import com.example.dto.ChannelDTO;
import com.example.entity.ChannelEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ChannelRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public ChannelDTO create(ChannelCrudDTO dto) {
        ChannelEntity entity = new ChannelEntity();
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        entity.setName(dto.getName());
        entity.setBannerId(dto.getBannerId());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setProfileId(profileId);
        channelRepository.save(entity);
        return toDTO(entity);
    }

    private static ChannelDTO toDTO(ChannelEntity entity) {
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setName(entity.getName());
        channelDTO.setDescription(entity.getDescription());
        channelDTO.setBannerId(entity.getBannerId());
        channelDTO.setPhotoId(entity.getPhotoId());
        channelDTO.setProfileId(entity.getProfileId());
        return channelDTO;
    }

    public ChannelDTO update(Integer id, ChannelCrudDTO dto, AppLanguage appLanguage) {
        ChannelEntity entity = get(id, appLanguage);
        entity.setName(dto.getName() != null ? dto.getName() : entity.getName());
        entity.setDescription(dto.getDescription() != null ? dto.getDescription() : entity.getDescription());
        entity.setBannerId(dto.getBannerId() != null ? dto.getBannerId() : entity.getBannerId());
        entity.setPhotoId(dto.getPhotoId() != null ? dto.getPhotoId() : entity.getPhotoId());
        channelRepository.save(entity);
        return toDTO(entity);

    }

    private ChannelEntity get(Integer id, AppLanguage appLanguage) {
        Optional<ChannelEntity> optionalChannelEntity = channelRepository.findById(id);
        if (optionalChannelEntity.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("channel.not.found", appLanguage));
        }
        return optionalChannelEntity.get();
    }
}
