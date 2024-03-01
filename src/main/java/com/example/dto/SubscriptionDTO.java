package com.example.dto;

import com.example.enums.SubscriptionNotificationType;
import com.example.enums.SubscriptionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDTO {
    private Integer id;
    private ProfileDTO profile;
    private ChannelDTO channel;
    private SubscriptionStatus status;
    private SubscriptionNotificationType notificationType;
    private LocalDateTime createdDate;
    private LocalDateTime unsubscribeDate;

}
