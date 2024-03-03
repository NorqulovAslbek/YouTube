package com.example.service;

import com.example.dto.*;
import com.example.entity.SubscriptionEntity;
import com.example.enums.AppLanguage;
import com.example.enums.SubscriptionStatus;
import com.example.exp.AppBadException;
import com.example.mapper.SubscriptionInfoMapper;
import com.example.repository.ChannelRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ResourceBundleService bundleService;

    public SubscriptionDTO create(CreateSubscriptionDTO dto, Integer profileId, AppLanguage language) {
        if (!channelRepository.existsById(dto.getChannelId())) {
            throw new AppBadException(bundleService.getMessage("channel.not.found", language));
        }
        if (!profileRepository.existsById(profileId)) {
            throw new AppBadException(bundleService.getMessage("playlist.not.found", language));
        }
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setChannelId(dto.getChannelId());
        entity.setProfileId(profileId);
        entity.setNotificationType(dto.getNotificationType());
        entity.setStatus(SubscriptionStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());
        subscriptionRepository.save(entity);
        SubscriptionDTO subscription = new SubscriptionDTO();
        subscription.setId(entity.getId());
        subscription.setCreatedDate(entity.getCreatedDate());
        subscription.setNotificationType(entity.getNotificationType());
        return subscription;
    }

    public Boolean unsubscribe(ChangeNotificationTypeSubscriptionDTO dto, Integer profileId, AppLanguage language) {
        if (!channelRepository.existsById(dto.getChannelId())) {
            throw new AppBadException(bundleService.getMessage("channel.not.found", language));
        }
        if (!profileRepository.existsById(profileId)) {
            throw new AppBadException(bundleService.getMessage("playlist.not.found", language));
        }
        Optional<SubscriptionEntity> optional = subscriptionRepository.getByChannelId(dto.getChannelId());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("subscription.not.found", language));
        }
        SubscriptionEntity entity = optional.get();
        return true;
    }

    public Boolean update(Integer id, UpdateSubscriptionDTO dto, AppLanguage language) {
        SubscriptionEntity entity = get(id, language);
        if (!channelRepository.existsById(dto.getChannelId())) {
            throw new AppBadException(bundleService.getMessage("channel.not.found", language));
        }
        entity.setChannelId(dto.getChannelId());
        entity.setStatus(dto.getStatus());
        subscriptionRepository.save(entity);
        return true;
    }

    private SubscriptionEntity get(Integer id, AppLanguage language) {
        Optional<SubscriptionEntity> optional = subscriptionRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("subscription.not.found", language));
        }
        return optional.get();
    }


    public Boolean changeStatus(ChangeNotificationTypeSubscriptionDTO dto, AppLanguage language) {
        if (!channelRepository.existsById(dto.getChannelId())) {
            throw new AppBadException(bundleService.getMessage("channel.not.found", language));
        }
        subscriptionRepository.getByIdChangeStatus(dto.getChannelId(), dto.getNotificationType());
        return true;
    }

    public List<SubscriptionInfoDTO> getSubscriptionList(AppLanguage language) {
        List<SubscriptionInfoMapper> userSubscriptionList = subscriptionRepository.getSubscriptionList();
        if (userSubscriptionList.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("subscription.not.found", language));
        }
        List<SubscriptionInfoDTO> dtoList = new LinkedList<>();
        for (SubscriptionInfoMapper entity : userSubscriptionList) {
            SubscriptionInfoDTO dto = new SubscriptionInfoDTO();
            dto.setId(entity.getId());
            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(entity.getChannelId());
            channelDTO.setName(entity.getName());
            channelDTO.setPhotoId(entity.getPhotoId());
            channelDTO.setUrl(entity.getUrl());
            dto.setChannel(channelDTO); //channel(id,name,photo(id,url))
            dto.setNotificationType(entity.getNotificationType());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<SubscriptionInfoDTO> getByUserIdSubscriptionList(Integer profileId, AppLanguage language) {
        List<SubscriptionInfoMapper> userSubscriptionList = subscriptionRepository.getByUserIdSubscriptionList(profileId);
        if (userSubscriptionList.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("subscription.not.found", language));
        }
        List<SubscriptionInfoDTO> dtoList = new LinkedList<>();
        for (SubscriptionInfoMapper entity : userSubscriptionList) {
            SubscriptionInfoDTO dto = new SubscriptionInfoDTO();
            dto.setId(entity.getId());
            ChannelDTO channelDTO = new ChannelDTO();
            channelDTO.setId(entity.getChannelId());
            channelDTO.setName(entity.getName());
            channelDTO.setPhotoId(entity.getPhotoId());
            channelDTO.setUrl(entity.getUrl());
            dto.setChannel(channelDTO); //channel(id,name,photo(id,url))
            dto.setNotificationType(entity.getNotificationType());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
