package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoLikeInfoDTO {
    private Integer id;
    private  VideoDTO video;
    private  AttachDTO previewAttach;
}
