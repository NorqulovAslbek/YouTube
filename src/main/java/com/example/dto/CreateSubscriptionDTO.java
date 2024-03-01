package com.example.dto;

import com.example.enums.SubscriptionNotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSubscriptionDTO {
    @NotNull
    private Integer channelId;
    @NotNull
    private SubscriptionNotificationType notificationType;

}
