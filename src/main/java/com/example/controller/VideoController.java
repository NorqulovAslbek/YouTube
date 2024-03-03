package com.example.controller;

import com.example.dto.*;
import com.example.enums.AppLanguage;
import com.example.service.VideoService;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/video")
@Tag(name = "Video Api for List", description = "This api will extract the information about the video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("")
    @Operation(summary = "This api Video Create", description = "This api is used to create video")
    public ResponseEntity<VideoDTO> create(@RequestBody VideoCreateDTO dto,
                                           @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                           AppLanguage language) {
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        log.info("There is an error in what you sent {}", dto);
        return ResponseEntity.ok(videoService.create(dto, language, profileId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "This api Video updateStatus", description = "This api is used to updateStatus video")
    public ResponseEntity<?> updateVideoDetail(@PathVariable("id") String videoId, @RequestBody VideoUpdateDetailDTO dto,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Video not found {}", dto.getTitle());
        return ResponseEntity.ok(videoService.updateDetail(dto, videoId, language));
    }

    @PutMapping("/updateStatus")
    @Operation(summary = "This api Video updateStatus", description = "This api is used to updateStatus video")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusVideoDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Video not found {}", dto.getId());
        return ResponseEntity.ok(videoService.updateStatus(dto, language));
    }

    @GetMapping("/getCategoryId")
    @Operation(summary = "This api getVideoByCategoryId", description = "This api is used to get Video By Category Id")
    public ResponseEntity<?> getVideoByCategoryId(@RequestParam Integer id,
                                                  @RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                  AppLanguage language) {
        return ResponseEntity.ok(videoService.getVideoByCategoryId(id, page, size, language));
    }

    @Operation(summary = "This api increase video view count", description = "This api is used to increase video view count")
    @PutMapping("/increaseVideoViewCount/{id}")
    public ResponseEntity<?> increaseVideoViewCountById(@PathVariable("id") String id,
                                                        @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                        AppLanguage language) {
        return ResponseEntity.ok(videoService.increaseVideoViewCountById(id, language));
    }

    @PostMapping("/searchByTitle")
    @Operation(summary = "This api Video search video by title", description = "This api is used to Search video by Title video")
    public ResponseEntity<PageImpl<VideoShortInfoDTO>> searchVideoByTitle(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                          @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language,
                                                                          @RequestBody VideoFilterDTO dto) {
        log.info("Title not found {}", dto.getTitle());
        return ResponseEntity.ok(videoService.searchVideoByTitle(page, size, dto, language));
    }

    @GetMapping("/{tagId}")
    @Operation(summary = "This api Video by tagId", description = "This api Get video by tag_id with pagination")
    public ResponseEntity<PageImpl<VideoShortInfoDTO>> getVideoByTagId(@PathVariable("tagId") String tagId,
                                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                                       @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("Title not found {}", tagId);
        return ResponseEntity.ok(videoService.getVideoByTagId(page, size, tagId, language));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api Video by list pagination", description = "This api Get video by list pagination")
    public ResponseEntity<PageImpl<VideoListPaginationDTO>> getVideoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                         @RequestParam(value = "size", defaultValue = "6") Integer size) {
        return ResponseEntity.ok(videoService.getVideoList(page, size));
    }

    @GetMapping("/getChannelVideoListPagination")
    @Operation(summary = "This api get channel video list pagination ", description = "This api get channel video list pagination ")
    public ResponseEntity<PageImpl<VidePlayListInfoDTO>> getChannelVideoListPagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                                       @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                                                       @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(videoService.getChannelVideoListPagination(page, size, language));
    }

    @GetMapping("/getVideoById/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable String id,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(videoService.getVideoById(id, language));
    }

}
