package com.example.controller;

import com.example.dto.CreateVideoLikeDTO;
import com.example.enums.AppLanguage;
import com.example.service.VideoLikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("videoLike")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CreateVideoLikeDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(videoLikeService.create(dto, language));
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<?> remove(@PathVariable("videoId") String id,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
     return ResponseEntity.ok(videoLikeService.remove(id,language));
    }
}
