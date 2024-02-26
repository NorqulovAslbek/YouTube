package com.example.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PlaylistVideoInfoDTO {
    private Integer playlistId;
    private VideoDTO video;
    private ChannelDTO channel;
    private Integer orderNum;
    private LocalDateTime createdDate;

}
