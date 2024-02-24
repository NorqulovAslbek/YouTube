package com.example.dto;

import com.example.entity.ChannelEntity;
import com.example.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {
    protected Integer id;
    private ChannelEntity channel;
    private Integer channelId;
    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNum;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;
}
