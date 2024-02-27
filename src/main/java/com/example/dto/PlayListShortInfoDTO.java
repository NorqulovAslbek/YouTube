package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListShortInfoDTO {
    private Integer id;
    private String name;
    private ChannelDTO channel;
    private Integer videoCount;
    private List<VideoDTO> videoList;
    private LocalDateTime createdDate;
}
