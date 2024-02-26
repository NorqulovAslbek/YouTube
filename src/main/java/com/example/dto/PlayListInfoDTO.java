package com.example.dto;

import com.example.enums.PlaylistStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListInfoDTO {
    private Integer id;
    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNum;
    private ChannelDTO channel;
    private ProfileDTO profile;
}
