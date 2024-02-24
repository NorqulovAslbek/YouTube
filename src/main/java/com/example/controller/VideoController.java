package com.example.controller;

import com.example.dto.UpdateStatusVideoDTO;
import com.example.dto.VideoCreateDTO;
import com.example.dto.VideoDTO;
import com.example.enums.AppLanguage;
import com.example.enums.VideoStatus;
import com.example.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/any")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "This api Video Create", description = "This api is used to create video")
    public ResponseEntity<VideoCreateDTO> create(@RequestBody VideoCreateDTO dto,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                 AppLanguage language) {

        log.info("There is an error in what you sent {}", dto);
        return ResponseEntity.ok(videoService.create(dto, language));
    }

//    @GetMapping("/byCategoryId")
//    public ResponseEntity<?> getVideoByCategoryId(@RequestParam Integer id,
//                                                  @RequestParam Integer page,
//                                                  @RequestParam Integer size,
//                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
//                                                  AppLanguage language) {
//        return ResponseEntity.ok(videoService.getVideoByCategoryId(id, page, size, language));
//    }

    @PutMapping("/any/updateStatus")
    @Operation(summary = "This api Video Create", description = "This api is used to create video")
    public ResponseEntity<?> updateStatus(@RequestBody UpdateStatusVideoDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {

        log.info("Video not found {}", dto.getId());
        return ResponseEntity.ok(videoService.updateStatus(dto, language));
    }


}
