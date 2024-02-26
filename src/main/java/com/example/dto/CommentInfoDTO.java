package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentInfoDTO {
    private Integer id;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    private ProfileDTO profile;
    private LocalDateTime createdDate;
}
