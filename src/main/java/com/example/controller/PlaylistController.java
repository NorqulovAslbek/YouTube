package com.example.controller;

import com.example.dto.CreatePlaylistDTO;
import com.example.enums.AppLanguage;
import com.example.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(@RequestBody CreatePlaylistDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(playlistService.create(dto, language));
    }

}
