package com.example.controller;

import com.example.dto.CreateCategoryDTO;
import com.example.enums.AppLanguage;
import com.example.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> create(@Valid @RequestBody CreateCategoryDTO dto) {
        log.info("Create category {}", dto.getNameEn());
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @Operation(summary = "Api for Category", description = "this api used for Category update")
    public ResponseEntity<?> update(@Valid @RequestBody CreateCategoryDTO dto,
                                    @PathVariable("id") Integer id) {
        log.info("Update category {}", dto.getNameEn());
        return ResponseEntity.ok(categoryService.update(dto, id));
    }

    @GetMapping("/any")
    @Operation(summary = "Api for Category", description = "this api used for Category list")
    public ResponseEntity<?> getList(@RequestParam(value = "lan",defaultValue = "UZ") AppLanguage lan){
        return ResponseEntity.ok(categoryService.getList(lan));
    }
     @Operation(summary = "Api for delete",description = " this api for category delete")
     @DeleteMapping("/{id}")
     @PreAuthorize(value = "hasRole('ADMIN')")
     public ResponseEntity<?> delete(@PathVariable("id") Integer id){
      log.info("delete category {}",id);
      return ResponseEntity.ok(categoryService.delete(id));
     }
}
