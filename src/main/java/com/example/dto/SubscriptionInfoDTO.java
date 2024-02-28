package com.example.dto;

import com.example.enums.SubscriptionNotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubscriptionInfoDTO {
    private Integer id;
    private ChannelDTO channel;
    private SubscriptionNotificationType notificationType;
    private LocalDateTime createdDate;
}
