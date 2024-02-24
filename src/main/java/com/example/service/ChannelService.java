package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.ChangeChannelStatusDTO;
import com.example.dto.ChannelCrudDTO;
import com.example.dto.ChannelDTO;
import com.example.dto.ChannelUpdatePhotoDTO;
import com.example.entity.ChannelEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ChannelStatus;
import com.example.exp.AppBadException;
import com.example.repository.ChannelRepository;
import com.example.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private AttachService attachService;

    public ChannelDTO create(ChannelCrudDTO dto) {
        ChannelEntity entity = new ChannelEntity();
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        entity.setName(dto.getName());
        entity.setBannerId(dto.getBannerId());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setProfileId(profileId);
        entity.setStatus(ChannelStatus.ACTIVE);
        channelRepository.save(entity);
        return toDTO(entity);
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

    public PageImpl<ChannelDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ChannelEntity> all = channelRepository.findAll(pageable);
        List<ChannelEntity> content = all.getContent();
        long totalElements = all.getTotalElements();
        List<ChannelDTO> channelDTOS = new LinkedList<>();
        for (ChannelEntity channelEntity : content) {
            channelDTOS.add(toDTO(channelEntity));
        }
        return new PageImpl<>(channelDTOS, pageable, totalElements);
    }

    public ChannelEntity get(Integer id, AppLanguage appLanguage) {
        Optional<ChannelEntity> optionalChannelEntity = channelRepository.findById(id);
        if (optionalChannelEntity.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("channel.not.found", appLanguage));
        }
        return optionalChannelEntity.get();
    }

    private static ChannelDTO toDTO(ChannelEntity entity) {
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(entity.getId());
        channelDTO.setName(entity.getName());
        channelDTO.setDescription(entity.getDescription());
        channelDTO.setBannerId(entity.getBannerId());
        channelDTO.setPhotoId(entity.getPhotoId());
        channelDTO.setProfileId(entity.getProfileId());
        channelDTO.setStatus(entity.getStatus());
        return channelDTO;
    }

    public ChannelDTO getById(Integer id, AppLanguage appLanguage) {
        ChannelEntity channelEntity = get(id, appLanguage);
        return toDTO(channelEntity);
    }

    public Boolean changeChannelStatus(Integer id, ChangeChannelStatusDTO dto, AppLanguage language) {
        ChannelEntity entity = get(id, language);
        Integer currentUserId = SpringSecurityUtil.getCurrentUser().getId();

        if (entity.getVisible() && entity.getProfileId().equals(currentUserId)) {
            entity.setStatus(dto.getStatus());
            entity.setUpdatedDate(LocalDateTime.now());
            channelRepository.save(entity);
        }
        return true;
    }

    public Boolean updatePhoto(ChannelUpdatePhotoDTO dto, AppLanguage language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        Optional<ChannelEntity> byIdAndProfileIdAndVisibleAndStatus = channelRepository.findByIdAndProfileIdAndVisibleAndStatus(dto.getChannelId(), currentUser.getId(), true, ChannelStatus.ACTIVE);
        if (byIdAndProfileIdAndVisibleAndStatus.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("channel.not.found", language));
        }
        ChannelEntity entity = byIdAndProfileIdAndVisibleAndStatus.get();
        if (entity.getPhotoId() != null) {
            attachService.delete(entity.getPhotoId(), language);
        }
        entity.setPhotoId(dto.getPhotoId());
        channelRepository.save(entity);
        return true;
    }

    public List<ChannelDTO> getChannelList() {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        List<ChannelEntity> channelEntities = channelRepository.byUserIdGetChannelList(currentUser.getId());
        List<ChannelDTO> list = new LinkedList<>();
        for (ChannelEntity channelEntity : channelEntities) {
            list.add(toDTO(channelEntity));
        }
        return list;
    }
}
