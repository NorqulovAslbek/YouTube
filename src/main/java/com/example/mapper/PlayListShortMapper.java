package com.example.mapper;

import java.time.LocalDateTime;

public interface PlayListShortMapper {
    Integer getId();

    String getName();

    Long getViewCount();

    Integer getTotalViewCount();

    LocalDateTime getUpdatedDate();

}
