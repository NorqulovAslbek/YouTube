package com.example.controller;

import com.example.dto.CreateReportDTO;
import com.example.dto.ReportDTO;
import com.example.enums.AppLanguage;
import com.example.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/report")
@Tag(name = "This api Report for List", description = "This is used for api report data")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("")
    @Operation(summary = "This api report is create", description = "This api reports is create")
    public ResponseEntity<ReportDTO> create(@RequestBody @Valid CreateReportDTO dto,
                                            @RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("report create error {}", dto.getContent());
        return ResponseEntity.ok(reportService.create(dto, language));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api report is delete", description = "This api reports is delete")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id,
                                          @RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("report not found {}", id);
        return ResponseEntity.ok(reportService.delete(id, language));
    }

}
