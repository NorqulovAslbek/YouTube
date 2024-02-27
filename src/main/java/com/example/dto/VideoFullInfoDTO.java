package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoFullInfoDTO {
    private String id;
    private String title;
    private String description;
    private PreviewAttachDTO previewAttach;
    private AttachDTO attach;
    private CategoryDTO category;
    private String tagList;
    private LocalDateTime publishedDate;
    private ChannelDTO channel;
    private Long viewCount;
    private Long sharedCount;
    private VideoLikeDTO like;
    private Long duration;
    private Integer likeCount;
    private Integer dislikeCount;
}
