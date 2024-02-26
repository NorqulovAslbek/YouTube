package com.example.controller;

import com.example.dto.CreatePlaylistDTO;
import com.example.dto.PlaylistDTO;
import com.example.enums.AppLanguage;
import com.example.enums.PlaylistStatus;
import com.example.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> deletePlaylist(@PathVariable Integer id,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.deletePlaylist(id, language));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api get list by userId", description = "This api used to get list by userId")
    public ResponseEntity<?> getListByUserId(@PathVariable("id") Integer id,
                                             @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("userId not found {}", id);
        return ResponseEntity.ok(playlistService.getListByUserId(id,language));
    }
}
