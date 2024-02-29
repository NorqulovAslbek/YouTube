package com.example.mapper;

import com.example.enums.SubscriptionNotificationType;

import java.time.LocalDateTime;

public interface SubscriptionInfoMapper {
    Integer getId();

    Integer getChannelId();

    String getName();

    String getPhotoId();

    String getUrl();

    SubscriptionNotificationType getNotificationType();
    LocalDateTime getCreatedDate();

}
