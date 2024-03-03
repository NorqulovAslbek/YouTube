package com.example.controller;

import com.example.dto.ChangeNotificationTypeSubscriptionDTO;
import com.example.dto.CreateSubscriptionDTO;
import com.example.dto.SubscriptionInfoDTO;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscription")
@Tag(name = "This api Subscription for list", description = "This will extract all the information about the subscription class")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("")
    @Operation(summary = "Subscription create", description = "This is api Subscription create used")
    public ResponseEntity<?> create(@RequestBody @Valid CreateSubscriptionDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        log.info("Subscription create error {}", dto);
        return ResponseEntity.ok(subscriptionService.create(dto, profileId, language));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Subscription update", description = "This is api Subscription update used")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody UpdateSubscriptionDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Subscription update error {}", id);
        return ResponseEntity.ok(subscriptionService.update(id, dto, language));
    }

    @PutMapping("")
    @Operation(summary = "Subscription get change Status", description = "Change Subscription Notification type Subscriptions")
    public ResponseEntity<?> changeStatus(@RequestBody ChangeNotificationTypeSubscriptionDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Subscription change status error {}", dto.getNotificationType());
        return ResponseEntity.ok(subscriptionService.changeStatus(dto, language));
    }

    @GetMapping("")
    @Operation(summary = "Subscription get List", description = "Get all Subscriptions")
    public ResponseEntity<List<SubscriptionInfoDTO>> getSubscriptionList(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionList(language));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Subscription get List", description = "Get User Subscription List By UserId")
    public ResponseEntity<List<SubscriptionInfoDTO>> getByUserIdSubscriptionList(@PathVariable("id") Integer profileId,
                                                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(subscriptionService.getByUserIdSubscriptionList(profileId, language));
    }
}
