package com.example.dto;

import com.example.enums.SubscriptionStatus;
import lombok.Data;

@Data
public class UpdateSubscriptionDTO {
    private Integer channelId;
    private SubscriptionStatus status;
}
