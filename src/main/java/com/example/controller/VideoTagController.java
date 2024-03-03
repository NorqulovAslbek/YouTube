package com.example.controller;

import com.example.dto.VideoTagCreateDTO;
import com.example.dto.VideoTagDTO;
import com.example.enums.AppLanguage;
import com.example.service.VideoTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/video_tag")
@Tag(name = "Video Tag for list api", description = "this api all the information about the video tag")
public class VideoTagController {
    @Autowired
    private VideoTagService videoTagService;

    @PostMapping("")
    @Operation(summary = "This api for create", description = "This api used to create a tag for the video")
    public ResponseEntity<VideoTagDTO> create(@RequestBody VideoTagCreateDTO dto,
                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("video tag not found {}", dto);
        return ResponseEntity.ok(videoTagService.create(dto, language));
    }

    @DeleteMapping("")
    @Operation(summary = "This api for delete", description = "This api used to delete a tag from video")
    public ResponseEntity<Boolean> delete(@RequestBody VideoTagCreateDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(videoTagService.delete(dto, language));
    }

    @GetMapping("/{videoId}")
    @Operation(summary = "This api for get all", description = "This api used to get video Tag List by videoId")
    public ResponseEntity<List<VideoTagDTO>> getVideoTagList(@PathVariable("videoId") String videoId,
                                                             @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(videoTagService.getVideoTagList(videoId, language));
    }


}
