package com.example.controller;

import com.example.dto.CreatePlayListVideoDTO;
import com.example.dto.PlayListVideoDTO;
import com.example.dto.PlaylistVideoInfoDTO;
import com.example.enums.AppLanguage;
import com.example.service.PlayListVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    @Operation(summary = "This api playList video update", description = "This api is used to update a playlist video")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @Valid @RequestBody CreatePlayListVideoDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("playlist video  was en error updating {}", dto.getOrderNumber());
        return ResponseEntity.ok(playListVideoService.update(id, dto, language));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "This api playList video delete", description = "This api is used to delete a playlist video")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("playlist video  was en error deleting {}", id);
        return ResponseEntity.ok(playListVideoService.delete(id, language));
    }

    @GetMapping("/{id}")
    @Operation(summary = "This api playList video getVideoList", description = "This api is used to Get Video list by playListId ")
    public ResponseEntity<List<PlaylistVideoInfoDTO>> getVideoList(@PathVariable("id") Integer id,
                                                                   @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("playlist video  was en error get videoList {}", id);
        return ResponseEntity.ok(playListVideoService.getVideoList(id, language));
    }

}
