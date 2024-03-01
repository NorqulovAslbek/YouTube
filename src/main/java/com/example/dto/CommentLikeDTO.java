package com.example.dto;

import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.CommentLikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDTO {
    private Integer id;
    private Integer profileId;
    private Integer commentId;
    private LocalDateTime createdDate;
    private CommentLikeType type;
    private LocalDateTime updatedDate;
}
