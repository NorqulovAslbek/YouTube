package com.example.dto;

import com.example.enums.VideoStatus;
import lombok.Data;

@Data
public class UpdateStatusVideoDTO {
    private String id;
    private VideoStatus status;
}
