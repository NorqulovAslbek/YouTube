package com.example.controller;

import com.example.dto.*;
import com.example.enums.AppLanguage;
import com.example.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/video")
@Tag(name = "Video Api for List", description = "This api will extract the information about the video")
public class VideoController {

    @Autowired
    private VideoService videoService;


    @PostMapping("/any")
    @Operation(summary = "This api Video Create", description = "This api is used to create video")
    public ResponseEntity<VideoDTO> create(@RequestBody VideoCreateDTO dto,
                                           @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                           AppLanguage language) {
        log.info("There is an error in what you sent {}", dto);
        return ResponseEntity.ok(videoService.create(dto, language));
    }

    @PutMapping("/any/updateStatus")
    @Operation(summary = "This api Video updateStatus", description = "This api is used to updateStatus video")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusVideoDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Video not found {}", dto.getId());
        return ResponseEntity.ok(videoService.updateStatus(dto, language));
    }
//    @GetMapping("/byCategoryId")
//    public ResponseEntity<?> getVideoByCategoryId(@RequestParam Integer id,
//                                                  @RequestParam Integer page,
//                                                  @RequestParam Integer size,
//                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
//                                                  AppLanguage language) {
//        return ResponseEntity.ok(videoService.getVideoByCategoryId(id, page, size, language));
//    }

    @PostMapping("/searchByTitle")
    @Operation(summary = "This api Video search video by title", description = "This api is used to Search video by Title video")
    public ResponseEntity<PageImpl<VideoShortInfoDTO>> searchVideoByTitle(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                          @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                                          @RequestBody VideoFilterDTO dto) {

        log.info("Title not found {}", dto.getTitle());
        return ResponseEntity.ok(videoService.searchVideoByTitle(page, size, dto));
    }



}
