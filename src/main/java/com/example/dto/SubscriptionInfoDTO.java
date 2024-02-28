package com.example.dto;

import com.example.enums.SubscriptionNotificationType;
import lombok.Data;

@Data
public class SubscriptionInfoDTO {
    private Integer id;
    private ChannelDTO channel;
    private String notificationType;
}
