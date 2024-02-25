package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePlayListVideoDTO {
    @NotNull
    private Integer orderNumber;
    @NotNull
    private Integer playlistId;
    @NotNull
    private String videoId;

}
