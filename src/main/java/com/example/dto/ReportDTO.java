package com.example.dto;

import com.example.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {
    private Integer id;
    private ProfileDTO profile;
    private String content;
    private ChannelDTO channel;
    private ReportType type;
    private LocalDateTime createdDate;
}
