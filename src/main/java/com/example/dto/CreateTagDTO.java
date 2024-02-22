package com.example.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTagDTO {
    @NonNull
    private String name;
}
