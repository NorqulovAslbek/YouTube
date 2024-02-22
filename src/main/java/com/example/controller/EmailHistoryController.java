package com.example.controller;

import com.example.service.EmailSendHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailHistory")
@Tag(name = "Api for email history", description = "an API written to display email history")
public class EmailHistoryController {
    @Autowired
    private EmailSendHistoryService sendEmailHistoryService;

    @GetMapping("/adm/pagination")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for getEmailPagination", description = "this api to get email history in paginated view")
    public ResponseEntity<?> getEmailPagination(@RequestParam Integer page,@RequestParam Integer size) {
        return ResponseEntity.ok(sendEmailHistoryService.getEmailPagination(page,size));
    }

}
