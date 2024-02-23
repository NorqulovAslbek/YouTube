package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Integer id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean visible;
}
