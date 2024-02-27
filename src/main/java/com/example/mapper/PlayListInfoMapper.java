package com.example.mapper;

import com.example.enums.PlaylistStatus;

public interface PlayListInfoMapper {
    Integer getId();

    String getName();

    String getDescription();

    PlaylistStatus getStatus();

    Integer getOrderNum();

    Integer getChannelId();

    String getChannelName();

    String getChannelPhotoId();

    String getChannelPhotoUrl();

    Integer getProfileId();

    String getProfileName();

    String getProfileSurname();

    String getProfileAttachId();

    String getProfileAttachUrl();
}
