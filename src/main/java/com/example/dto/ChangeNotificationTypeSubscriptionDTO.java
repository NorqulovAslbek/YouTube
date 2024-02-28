package com.example.dto;

import com.example.enums.SubscriptionNotificationType;
import lombok.Data;

@Data
public class ChangeNotificationTypeSubscriptionDTO {
    private Integer channelId;
    private SubscriptionNotificationType notificationType;
}
