package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VidePlayListInfoDTO {
    private String title;
    private String id;
    private PreviewAttachDTO previewAttach;
    private Long viewCount;
    private LocalDateTime publishedDate;
    private Long duration;
    private String playListJson;
}
