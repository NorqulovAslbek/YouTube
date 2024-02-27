package com.example.service;

import com.example.dto.CreateSubscriptionDTO;
import com.example.dto.SubscriptionDTO;
import com.example.entity.SubscriptionEntity;
import com.example.enums.AppLanguage;
import com.example.enums.SubscriptionStatus;
import com.example.exp.AppBadException;
import com.example.repository.ChannelRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        entity.setChannelId(entity.getChannelId());
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
}
