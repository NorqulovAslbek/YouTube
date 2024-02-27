package com.example.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfoMapper {
    String getId();

    String geTitle();

    String getPreviewAttachId();

    LocalDateTime getPublishedDate();

    Long getViewCount();

    Integer getChannel();

    String getChannelName();

    public String getPhotoId();
}
