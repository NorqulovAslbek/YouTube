package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlayListShortInfoDTO {
    private Integer id;
    private String name;
    private ChannelDTO channel;
    private Integer videoCount;
    private List<VideoDTO> videoList;
    private LocalDateTime createdDate;
}
