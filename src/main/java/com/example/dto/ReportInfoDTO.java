package com.example.dto;

import com.example.enums.ReportType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportInfoDTO {
    private Integer id;
    private ProfileDTO profile;
    private PreviewAttachDTO previewAttach;
    private String content;
    private Integer channelId;
    private ReportType type;
    private LocalDateTime createdDate;
}
