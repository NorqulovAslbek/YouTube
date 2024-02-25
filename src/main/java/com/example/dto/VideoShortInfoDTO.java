package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoShortInfoDTO {
    private String id;
    private String title;
    private AttachDTO previewAttach;
    private ChannelDTO channel;
    private Long duration;
    private Long viewCount;
    private LocalDateTime publishedDate;
}

