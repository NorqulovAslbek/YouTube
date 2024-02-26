package com.example.dto;

import com.example.entity.VideoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentListDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private Integer likeCount;
    private Integer dislikeCount;
    private VideoDTO video;
}
