package com.example.controller;

import com.example.dto.VideoPermissionDTO;
import com.example.enums.AppLanguage;
import com.example.service.VideoPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class VideoPermissionController {
    @Autowired
    private VideoPermissionService permissionService;
    @PostMapping("")
    public ResponseEntity<?> givePermission(@RequestBody VideoPermissionDTO dto,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                        AppLanguage language) {
      return ResponseEntity.ok(permissionService.permission(dto,language));
    }
}
