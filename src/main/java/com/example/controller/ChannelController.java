package com.example.controller;

import com.example.dto.ChangeChannelStatusDTO;
import com.example.dto.ChannelCrudDTO;
import com.example.dto.ChannelDTO;
import com.example.dto.ChannelUpdatePhotoDTO;
import com.example.enums.AppLanguage;
import com.example.service.ChannelService;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Channel Api list", description = "Api list for Channel")
@RestController
@RequestMapping("/channel")
@Slf4j
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @Operation(summary = "Api for channel create", description = "this api channel create")
    @PostMapping("")
    public ResponseEntity<ChannelDTO> create(@RequestBody ChannelCrudDTO dto) {
        log.warn("Channel create {}", SpringSecurityUtil.getCurrentUser().getId());
        return ResponseEntity.ok(channelService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Api for channel update", description = "this api channel update")
    public ResponseEntity<ChannelDTO> update(@PathVariable Integer id,
                                             @RequestBody ChannelCrudDTO dto,
                                             @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage appLanguage) {
        return ResponseEntity.ok(channelService.update(id, dto, appLanguage));
    }

    @GetMapping("/adm/pagination")
    @Operation(summary = "Api for channel pagination", description = "this api  get channel pagination")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> pagination(@RequestParam Integer page,
                                        @RequestParam Integer size) {
        return ResponseEntity.ok(channelService.pagination(page, size));
    }

    @GetMapping("/adm/{id}")
    @Operation(summary = "Api for channel getById", description = "this api  get channel by id")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Integer id,
                                     @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                     AppLanguage appLanguage) {
        log.info("channel not found {}", id);
        return ResponseEntity.ok(channelService.getById(id, appLanguage));
    }

    @GetMapping("/channelList")
    @Operation(summary = "Api for channel getChannelList", description = "this api  get channel list")
    public ResponseEntity<?> getChannelList() {
        return ResponseEntity.ok(channelService.getChannelList());
    }

    @PutMapping("/adm/{id}")
    @Operation(summary = "Api for channel change channel status", description = "this api change channel status")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR','USER')")
    public ResponseEntity<?> changeChannelStatus(@PathVariable("id") Integer id,
                                                 @RequestBody ChangeChannelStatusDTO dto,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(channelService.changeChannelStatus(id, dto, language));
    }

    @PutMapping("/updatePhoto")
    public ResponseEntity<?> updateChannelPhoto(@RequestBody ChannelUpdatePhotoDTO dto,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(channelService.updatePhoto(dto, language));
    }


}
