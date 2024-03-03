package com.example.controller;

import com.example.dto.CreateCommentDTO;
import com.example.dto.UpdateCommentDTO;
import com.example.enums.AppLanguage;
import com.example.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
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

    @PutMapping("/{id}")
    @Operation(summary = "Api for comment update", description = "this api  update comment")
    public ResponseEntity<?> update(@PathVariable("id") Integer commentId,
                                    @RequestBody UpdateCommentDTO dto,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(commentService.update(commentId, dto, language));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Api for comment delete", description = "this api delete comment")
    public ResponseEntity<?> delete(@PathVariable("id") Integer commentId,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                    AppLanguage language) {
        return ResponseEntity.ok(commentService.delete(commentId, language));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for  commentListPagination", description = "this api comment list pagination")
    public ResponseEntity<?> commentListPagination(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(commentService.commentListPagination(page, size));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for  commentListByProfileId", description = "this api comment list By ProfileId")
    public ResponseEntity<?> commentListByProfileId(@PathVariable Integer id,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                    AppLanguage language) {
        return ResponseEntity.ok(commentService.commentListByProfileId(id, language));
    }

    @GetMapping("/byProfile") //yani ozi yozgan kommentlar ro'yhati.
    @Operation(summary = "Api for commentListByProfile", description = "this api comment list By Profile")
    public ResponseEntity<?> commentListByProfile(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                  AppLanguage language) {
        return ResponseEntity.ok(commentService.commentListByProfile(language));
    }

    @GetMapping("/byVideoId/{id}")
    @Operation(summary = "Api for commentListByProfile", description = "this api comment list By videoId")
    public ResponseEntity<?> commentListByVideoId(@PathVariable String id,
                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                  AppLanguage language) {
        return ResponseEntity.ok(commentService.commentListByVideoId(id, language));
    }

    @GetMapping("/commentId")//comment id beriladi shu kommentga yozilgan kommentlar listini chiqarib berish kerak
    @Operation(summary = "Api for getCommentReplied", description = "this api will output the comments written to the comment")
    public ResponseEntity<?> getCommentReplied(@RequestParam("commentId") Integer commentId,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                               AppLanguage language) {
        return ResponseEntity.ok(commentService.getCommentReplied(commentId, language));
    }


}
