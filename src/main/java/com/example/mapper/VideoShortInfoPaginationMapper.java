package com.example.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfoPaginationMapper {
    String getId();

    String getTitle();

    String getPreviewAttachId();

    LocalDateTime getPublishedDate();

    Long getViewCount();
    Long getDuration();
    Integer getChannelId();

    String getChannelName();

    String getPhotoId();

    Integer getProfileId();

    String getProfileName();

    String getProfileSurname();

    String getPlayListJson();
}

