package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class VidePlayListInfoDTO {
    private String id;
    private String title;
    private PreviewAttachDTO previewAttach;
    private Long viewCount;
    private LocalDateTime publishedDate;
    private Long duration;
}
