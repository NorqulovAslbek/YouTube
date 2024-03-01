package com.example.mapper;

import com.example.enums.ReportType;

import java.time.LocalDateTime;

public interface ReportInfoMapper {
    Integer getReportId();
    Integer getProfileId();
    String getName();
    String getSurname();
    String getPhotoId();
    String getUrl();
    String getContent();
    Integer getChannelId();
    ReportType getType();
    LocalDateTime getCreatedDate();
}
