package com.example.mapper;

import java.time.LocalDateTime;

public interface VideoShortInfoMapper {
     String getId();

     String getTitle();

     String getPreviewAttachId();

     LocalDateTime getPublishedDate();

     Long getViewCount();

     Integer getChannelId();

     String getChannelName();

     String getPhotoId();
     Long getDuration();


}

