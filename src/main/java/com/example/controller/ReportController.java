package com.example.controller;

import com.example.dto.CreateReportDTO;
import com.example.dto.ReportDTO;
import com.example.dto.ReportInfoDTO;
import com.example.enums.AppLanguage;
import com.example.service.ReportService;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api get reportList", description = "This api get reports in ADMIN")
    public ResponseEntity<PageImpl<ReportInfoDTO>> reportList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "size", defaultValue = "6") Integer size) {

        return ResponseEntity.ok(reportService.reportList(page, size));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api report is delete", description = "This api reports is delete")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id,
                                          @RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("report not found {}", id);
        return ResponseEntity.ok(reportService.delete(id, language));
    }

    @GetMapping("/getUserId/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api get reportList by UserId", description = "This api get reports get User id in ADMIN")
    public ResponseEntity<List<ReportInfoDTO>> reportListByUserId(@PathVariable("id") Integer profileId,
                                                                  @RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("report not found {}", profileId);
        return ResponseEntity.ok(reportService.reportListByUserId(profileId, language));
    }

}
