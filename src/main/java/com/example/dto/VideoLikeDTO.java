package com.example.dto;

import com.example.enums.VideoLikeType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoLikeDTO {
    protected Integer id;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
    private ProfileDTO profile;
    private Integer profileId;
    private VideoDTO video;
    private String videoId;
    private VideoLikeType type;
    private Boolean visible;
}
