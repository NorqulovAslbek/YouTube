package com.example.dto;

import com.example.entity.ChannelEntity;
import com.example.enums.VideoStatus;
import com.example.enums.VideoType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private String id;
    private String title;
    private String description;
    private Long duration;
    private CategoryDTO category;
    private AttachDTO attach;
    private ChannelDTO channel;
    private VideoStatus status;
    private VideoType type;
    private Long viewCount;
    private Long sharedCount;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
}
