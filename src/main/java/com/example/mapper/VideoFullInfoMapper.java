package com.example.mapper;


import java.time.LocalDateTime;

public interface VideoFullInfoMapper {

    String getId();

    String getTitle();

    String getDescription();

    String getPreviewAttachId();

    LocalDateTime getPublishedDate();

    Long getViewCount();

    Long getSharedCount();

    Integer getLikeCount();

    Integer getDislikeCount();

    Long getDuration();

    Integer getCategoryId();

    String getCategoryName();

    String getAttachId();

    String getUrl();

    Long getAttachDuration();

    Integer getChannelId();

    String getChannelName();

    String getName();

    String getPhotoId();

    String getTagListJson();


}
