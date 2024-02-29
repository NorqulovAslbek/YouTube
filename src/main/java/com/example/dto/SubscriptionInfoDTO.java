package com.example.dto;

import com.example.enums.SubscriptionNotificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionInfoDTO {
    private Integer id;
    private ChannelDTO channel;
    private SubscriptionNotificationType notificationType;
    private LocalDateTime createdDate;
}
