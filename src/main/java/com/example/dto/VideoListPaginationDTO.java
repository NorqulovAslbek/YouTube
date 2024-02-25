package com.example.dto;

import lombok.Data;

@Data
public class VideoListPaginationDTO {
    private VideoShortInfoDTO shortInfo;
    private ProfileDTO profile;
    private PlaylistDTO playlist;

}
