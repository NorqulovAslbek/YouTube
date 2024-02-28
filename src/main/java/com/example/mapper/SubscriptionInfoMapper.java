package com.example.mapper;

import com.example.enums.SubscriptionNotificationType;

public interface SubscriptionInfoMapper {
    Integer getId();

    Integer getChannelId();

    String getName();

    String getPhotoId();

    String getUrl();

    String getNotificationType();

}
