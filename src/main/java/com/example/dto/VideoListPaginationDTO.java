package com.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class VideoListPaginationDTO {
    private VideoShortInfoDTO shortInfo;
    private ProfileDTO profile;
    private List<PlaylistDTO> playlist;

}
