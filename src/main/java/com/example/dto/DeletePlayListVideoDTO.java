package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeletePlayListVideoDTO {
    @NotNull
    private String videoId;
    @NotNull
    private Integer playlistId;
}
