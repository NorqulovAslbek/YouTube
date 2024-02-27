package com.example.dto;

import lombok.Data;

@Data
public class VideoListPaginationDTO {
    private VideoDTO video;
    private ChannelDTO channel;
    private ProfileDTO profile;

}
