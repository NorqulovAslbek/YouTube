package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChannelCrudDTO {
    private String name;
    private String description;
    private String bannerId;
    private String photoId;
}
