package com.example.dto;

import com.example.enums.ChannelStatus;
import lombok.Data;

@Data
public class ChangeChannelStatusDTO {
    private ChannelStatus status;
}
