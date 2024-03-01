package com.example.dto;
import com.example.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateReportDTO {
    @NotNull
    private String content;
    @NotNull
    private Integer profileId;
    @NotNull
    private Integer channelId;
    @NotNull
    private ReportType type;
}
