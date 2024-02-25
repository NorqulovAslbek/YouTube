package com.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayListVideoDTO {
    private Integer id;
    private Integer playlistId;
    private String videoId;
    private Integer orderNum;
    private LocalDateTime createdDate;
}
