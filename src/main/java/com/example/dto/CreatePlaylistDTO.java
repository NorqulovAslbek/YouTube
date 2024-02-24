package com.example.dto;

import com.example.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatePlaylistDTO {
    private Integer channelId;
    private String name;
    private String description;
    private Integer orderNum;
    private PlaylistStatus status;
}
