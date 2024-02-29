package com.example.dto;

import com.example.enums.CommentLikeType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentLikeDTO {
    @NotNull
    private Integer commentId;
    @NotNull
    private CommentLikeType type;
}
