package com.example.controller;

import com.example.dto.CreatePlayListVideoDTO;
import com.example.dto.PlayListVideoDTO;
import com.example.enums.AppLanguage;
import com.example.service.PlayListVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/playList_video")
@Tag(name = "Play List for api", description = "This api shows all method about play list video")
public class PlayListVideoController {

    @Autowired
    private PlayListVideoService playListVideoService;

    @PostMapping("")
    @Operation(summary = "This api playList video create", description = "This api is used to create a playlist video")
    public ResponseEntity<PlayListVideoDTO> create(@Valid @RequestBody CreatePlayListVideoDTO dto,
                                                   @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("playlist video  was en error creating {}", dto.getOrderNumber());
        return ResponseEntity.ok(playListVideoService.create(dto, language));
    }

}
