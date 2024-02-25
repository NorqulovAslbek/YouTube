package com.example.dto;

import com.example.enums.VideoLikeType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVideoLikeDTO {
    @NotNull
    private String videoId;
    @NotNull
    private VideoLikeType type;
}
