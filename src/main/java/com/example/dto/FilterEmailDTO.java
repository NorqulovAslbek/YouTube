package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FilterEmailDTO {
    private String email;
    private LocalDate fromDate;
    private LocalDate toDate;
}
