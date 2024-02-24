package com.example.dto;

import com.example.enums.VideoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoUpdateDetailDTO {
    private String title;
    private String description;
    private Integer categoryId;
    private String previewAttachId;
    private Integer channelId;
    private VideoStatus status;
}
