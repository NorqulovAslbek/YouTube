package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoTagDTO {
    private String id;
    private String videoId;
    private Integer tagId;
    private TagDTO tag;
    private LocalDateTime createdDate;
}
