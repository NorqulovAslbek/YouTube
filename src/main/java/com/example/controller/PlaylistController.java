package com.example.controller;

import com.example.dto.CreatePlaylistDTO;
import com.example.dto.PlayListInfoDTO;
import com.example.dto.PlayListShortInfoDTO;
import com.example.dto.PlaylistDTO;
import com.example.enums.AppLanguage;
import com.example.enums.PlaylistStatus;
import com.example.service.PlaylistService;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Playlist Api list", description = "Api list for Playlist")
@RestController
@RequestMapping("/playlist")
@Slf4j
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("")
    @Operation(summary = "Api for create playlist ", description = "this api used for create playlist ")
    public ResponseEntity<PlaylistDTO> create(@RequestBody CreatePlaylistDTO dto,
                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.create(dto, language));
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<Boolean> changeStatus(@PathVariable Integer id,
                                                @RequestParam(name = "status") PlaylistStatus status,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.changeStatus(id, status, language));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.deletePlaylist(id, language));
    }

    @Operation(summary = "This api get playlist pagination ", description = "This api get playlist pagination ")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/playlistPagination")
    public ResponseEntity<PageImpl<PlayListInfoDTO>> playlistPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                                                                        @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.playlistPagination(page - 1, size, language));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api get list by userId", description = "This api used to get list by userId")
    public ResponseEntity<List<PlayListInfoDTO>> getListByUserId(@PathVariable("userId") Integer userId) {
        log.info("Playlist List By UserId {}", userId);
        return ResponseEntity.ok(playlistService.getListByUserId(userId));
    }

    @GetMapping("/getAll")
    @Operation(summary = "This api  Get User Playlist", description = "This api Get User Playlist")
    public ResponseEntity<List<PlayListShortInfoDTO>> getAll() {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        log.info("Playlist List By UserId {}", profileId);
        return ResponseEntity.ok(playlistService.getAll(profileId));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id,
                                     @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.getById(id, language));
    }


}
