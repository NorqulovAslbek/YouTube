package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.enums.AppLanguage;
import com.example.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

        log.info("file not found {}", file);
        return ResponseEntity.ok().body(attachService.save(file));
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

    @GetMapping("/any/download/{fineName}")
    @Operation(summary = "This api to download", description = "This api is used to download")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName,
                                             @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                             AppLanguage language) {
        log.info("file is not found {}", fileName);
        return attachService.download(fileName, language);
    }

    @GetMapping("/adm")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api get All", description = "This api shows all files in pagination view")
    public ResponseEntity<PageImpl<AttachDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "6") Integer size) {
        return ResponseEntity.ok(attachService.pagination(page, size));
    }

    @DeleteMapping("/adm/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api delete", description = "This api is used to open file by given id")
    public ResponseEntity<?> delete(@PathVariable("id") String id,
                                    @RequestHeader(value = "Accept-Language", defaultValue = "uz")
                                    AppLanguage language) {

        log.info("file not found {}", id);
        return ResponseEntity.ok(attachService.delete(id, language));
    }

    @GetMapping("/getUrl/{id}")
    public ResponseEntity<?> getUrl(@PathVariable("id") String id) {
        log.info("file not found {}", id);
        return ResponseEntity.ok(attachService.getURL(id));
    }

}
