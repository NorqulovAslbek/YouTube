package com.example.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CreateTagDTO {
    private List<String> name;
}
