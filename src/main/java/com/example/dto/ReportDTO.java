package com.example.dto;

import com.example.enums.ReportType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {
    private Integer id;
    private ProfileDTO profile;
    private String content;
    private ChannelDTO channel;
    private ReportType type;
}
