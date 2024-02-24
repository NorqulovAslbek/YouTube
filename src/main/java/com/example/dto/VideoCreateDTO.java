package com.example.dto;

import com.example.enums.VideoStatus;
import com.example.enums.VideoType;
import lombok.Data;

@Data
public class VideoCreateDTO {
    private String title;
    private String description;
    private Integer categoryId;
    private String attachId;
    private Integer channelId;
    private String previewAttachId;
    private VideoType type;
    private VideoStatus status;
}
