package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String originalName;
    private Long size;
    private String type;
    private String path;
    private Long duration;
    private LocalDateTime createdData;

}
