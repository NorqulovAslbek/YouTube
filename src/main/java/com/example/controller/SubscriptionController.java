package com.example.controller;

import com.example.dto.CreateSubscriptionDTO;
import com.example.dto.UpdateSubscriptionDTO;
import com.example.enums.AppLanguage;
import com.example.service.SubscriptionService;
import com.example.util.SpringSecurityUtil;
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
@RequestMapping("/subscription")
@Tag(name = "This api Subscription for list", description = "This will extract all the information about the subscription class")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Subscription create", description = "This is api Subscription create used")
    public ResponseEntity<?> create(@RequestBody @Valid CreateSubscriptionDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        log.info("Subscription create error {}", dto);
        return ResponseEntity.ok(subscriptionService.create(dto, profileId, language));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Subscription update", description = "This is api Subscription update used")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody UpdateSubscriptionDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("Subscription not found {}", id);
        return ResponseEntity.ok(subscriptionService.update(id, dto, language));
    }

}
