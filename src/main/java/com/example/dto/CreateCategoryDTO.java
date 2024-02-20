package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class CreateCategoryDTO {
    @NotNull
    private String nameUz;
    @NotNull
    private String nameRu;
    @NotNull
    private String nameEn;
}
