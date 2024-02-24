package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private AttachDTO previewAttach;
    private AttachDTO attach;
    private CategoryDTO category;
    private List<TagDTO> tagList;
    private LocalDateTime publishedDate;
    private ChannelDTO channel;
    private Long viewCount;
    private Long sharedCount;
    private VideoLikeDTO like;
    private Long duration;
}
