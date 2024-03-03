package com.example.controller;

import com.example.dto.TagDTO;
import com.example.dto.CreateTagDTO;
import com.example.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping()
    @Operation(summary = "Api for Tag", description = "this api used for Tag create")
    public ResponseEntity<List<TagDTO>> create(@Valid @RequestBody CreateTagDTO dto) {
        log.info("Create tag {}", dto.getName());
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @Operation(summary = "Update tag", description = "this api used for tag update")
    public ResponseEntity<Boolean> update(@Valid @RequestBody TagDTO dto, @PathVariable Integer id) {
        log.info("Update tag");
        return ResponseEntity.ok(tagService.update(dto, id));
    }

    @Operation(summary = "Delete tag", description = "this api used for delete tag")
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        log.info("Delete tag {}", id);
        return ResponseEntity.ok(tagService.delete(id));
    }

    @GetMapping("")
    @Operation(summary = "Api for Tag", description = "this api used for get List tag")
    public ResponseEntity<List<TagDTO>> getList() {
        log.info("Get list tag ");
        return ResponseEntity.ok(tagService.getList());
    }
}
