package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/attach")
@Tag(name = "Api for Attach(photo,media) List", description = "This api is used to work with images, videos and media")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAttaches(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(fileName);
    }

    @PostMapping("/any/upload")
    @Operation(summary = "This api to create", description = "This api is for file upload")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        AttachDTO dto = attachService.save(file);

        log.info("file not found {}", file);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/any/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "This api to by Id", description = "This api is used to get by get by id")
    public byte[] getById(@PathVariable("fileName") String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                log.info("file is not {}", fileName);
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping(value = "/any/general/{fileName}", produces = MediaType.ALL_VALUE)
    @Operation(summary = "This api to by Id", description = "This api is used to get by get by id")
    public byte[] getByIdGeneral(@PathVariable("fileName") String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            try {
                return attachService.open_general(fileName);
            } catch (Exception e) {
                log.info("file is not {}", fileName);
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }


}
