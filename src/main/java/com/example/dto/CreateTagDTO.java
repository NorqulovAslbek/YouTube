package com.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateTagDTO {
    @NonNull
    private String name;
}
