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
    private Integer channelId;
    private String channelName;
    private String channelPhotoId;
    private String channelPhotoUrl;
    private Integer profileId;
    private String profileName;
    private String profileSurname;
    private String profileAttachId;
    private String profileAttachUrl;
}
