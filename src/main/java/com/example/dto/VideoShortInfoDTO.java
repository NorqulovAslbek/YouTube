package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoShortInfoDTO {
    private String id;
    private String title;
    private AttachDTO previewAttach;
    private PreviewAttachDTO previewDto;
    private ChannelDTO channel;
    private Long duration;
    private Long viewCount;
    private LocalDateTime publishedDate;
}

