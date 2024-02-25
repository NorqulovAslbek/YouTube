package com.example.controller;

import com.example.dto.CreateCommentDTO;
import com.example.enums.AppLanguage;
import com.example.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Tag(name = "Comment Api list", description = "Api list for Comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping()
    @Operation(summary = "Api for comment create", description = "this api  create comment")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCommentDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(commentService.create(dto, language));
    }

}
