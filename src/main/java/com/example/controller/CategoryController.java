package com.example.controller;

import com.example.dto.CreateCategoryDTO;
import com.example.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Category Api list", description = "Api list for Category")
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    @Operation(summary = "Api for Category", description = "this api used for Category create")
    public ResponseEntity<?> create(@Valid  @RequestBody CreateCategoryDTO dto){
        return ResponseEntity.ok(categoryService.create(dto));
    }
}
