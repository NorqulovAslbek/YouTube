package com.example.controller;

import com.example.dto.FilterEmailDTO;
import com.example.dto.UpdateEmailDTO;
import com.example.service.EmailSendHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emailHistory")
@Tag(name = "Api for email history", description = "an API written to display email history")
public class EmailHistoryController {
    @Autowired
    private EmailSendHistoryService sendEmailHistoryService;

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for getEmailPagination", description = "this api to get email history in paginated view")
    public ResponseEntity<?> getEmailPagination(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(sendEmailHistoryService.getEmailPagination(page, size));
    }


    @PostMapping("/adm/paginationByEmail")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for getBYEmailPagination", description = "this api to get email history by email paginated view")
    public ResponseEntity<?> getByEmailPagination(@RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestBody UpdateEmailDTO dto) {
        return ResponseEntity.ok(sendEmailHistoryService.getByEmailPagination(page, size, dto));
    }

    @PostMapping("/adm/filter")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for filter", description = "this api to get email history filter")
    public ResponseEntity<?> filter(@RequestParam Integer page,
                                    @RequestParam Integer size,
                                    @RequestBody FilterEmailDTO dto) {
        return ResponseEntity.ok(sendEmailHistoryService.filter(dto, page, size));
    }

}
