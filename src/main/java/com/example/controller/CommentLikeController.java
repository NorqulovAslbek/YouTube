package com.example.controller;

import com.example.dto.CreateCommentLikeDTO;
import com.example.enums.AppLanguage;
import com.example.service.CommentLikeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;


    @PostMapping("")
    @Operation(summary = "Api for comment like create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCommentLikeDTO dto){
        return ResponseEntity.ok(commentLikeService.create(dto));
    }


}
