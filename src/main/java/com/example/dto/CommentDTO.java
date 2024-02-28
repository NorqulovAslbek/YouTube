package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    @NotNull
    private Integer profileId;
    @NotNull
    private String videoId;
    @NotNull
    private String content;
    private Integer replyId;
    private Integer likeCount;
    private Integer dislikeCount;
    private LocalDateTime createdDate;
}
