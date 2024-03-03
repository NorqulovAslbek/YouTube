package com.example.controller;

import com.example.dto.CommentLikeDTO;
import com.example.dto.CreateCommentLikeDTO;
import com.example.enums.AppLanguage;
import com.example.service.CommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;


    @PostMapping("")
    @Operation(summary = "Api for comment like create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCommentLikeDTO dto,
                                    @RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(commentLikeService.create(dto, language));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Api for remove comment like ")
    public ResponseEntity<?> remove(@PathVariable("id") Integer id,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(commentLikeService.remove(id, language));

    }

    @GetMapping("")
    @Operation(summary = "Api for get  user liked comment list ")
    public ResponseEntity<List<CommentLikeDTO>> getLikedList(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                             AppLanguage language) {
        return ResponseEntity.ok(commentLikeService.getLikedList(language));
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Api for get get user likedComment list by userId for ADMIN")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<CommentLikeDTO>> getUserLikedCommentListByUserId(@PathVariable("profileId") Integer profileId,
                                                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                                                AppLanguage language) {
        return ResponseEntity.ok(commentLikeService.getUserLikedCommentListByUserId(profileId, language));
    }


}
