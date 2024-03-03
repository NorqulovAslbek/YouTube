package com.example.controller;

import com.example.dto.CreateVideoLikeDTO;
import com.example.enums.AppLanguage;
import com.example.service.VideoLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("videoLike")
@Tag(name = "VideoLike Api for List", description = "This api is for liking and disliking videos")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;

    @PostMapping()
    @Operation(summary = "Api for create VideoLike ", description = "this api is for liking video")
    public ResponseEntity<?> create(@Valid @RequestBody CreateVideoLikeDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(videoLikeService.create(dto, language));
    }

    @DeleteMapping("/{videoId}")
    @Operation(summary = "Api for remove VideoLike", description = "this api to remove like from video")
    public ResponseEntity<?> remove(@PathVariable("videoId") String id,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(videoLikeService.remove(id, language));
    }

    @GetMapping("")
    @Operation(summary = "Api for getUserLikedVideoList VideoLike", description = "This api is for viewing the list of liked videos")
    public ResponseEntity<?> getUserLikedVideoList(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                   AppLanguage language) {
        return ResponseEntity.ok(videoLikeService.getUserLikedVideoList(language));
    }

    @GetMapping("/adm/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for getGetUserLikedVideoListByUserId VideoLike", description = "this api is intended for Admin, it will list all users who have clicked like")
    public ResponseEntity<?> getGetUserLikedVideoListByUserId(@PathVariable("id") Integer profileId,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                              AppLanguage language) {
        return ResponseEntity.ok(videoLikeService.getGetUserLikedVideoListByUserId(profileId, language));
    }
}
