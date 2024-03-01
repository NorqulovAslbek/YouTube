package com.example.mapper;

import java.time.LocalDateTime;

public interface PlayListShortInfoMapper {
    Integer getId();

    String getName();

    LocalDateTime getCreatedDate();

    Integer getChannelId();

    String getChannelName();

    String getVideoId();

    String getVideoTitle();

    Long getDuration();
}
