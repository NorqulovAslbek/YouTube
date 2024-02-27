package com.example.mapper;

import java.time.LocalDateTime;

public interface VideoPlayListInfoMapper {
    String getVideoId();

    String getTitle();

    String getPreviewAttachId();

    Long getViewCount();

    LocalDateTime publishedDate();

    Long getDuration();
    String getPlayListJson();


}
