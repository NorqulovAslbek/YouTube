package com.example.dto;

import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class EmailSendHistoryDTO {
    private Integer id;
    private String message;
    private String email;
    private ProfileStatus status;
    private LocalDateTime createdDate;
}
