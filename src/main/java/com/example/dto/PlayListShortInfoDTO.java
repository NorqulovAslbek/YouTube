package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListShortInfoDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer channelId;
    private String channelName;
    private String videoId;
    private String videoTitle;
    private Long duration;
    private Long viewCount;
    private Integer totalViewCount;

}
