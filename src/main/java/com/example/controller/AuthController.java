package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.RegistrationDTO;
import com.example.enums.AppLanguage;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    @Operation(summary = "Api for registration", description = "this api used for authorization")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage appLanguage) {
        log.info("registration {}", dto.getEmail());
        return ResponseEntity.ok(authService.registration(dto, appLanguage));
    }

    @Operation(summary = "Api for emailVerification", description = "this api used for authorization")
    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        log.info("emailVerification {}", jwt);
        return ResponseEntity.ok(authService.verification(jwt));
    }

    @Operation(summary = "Api for login", description = "this api used for login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO dto,
                                   @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        log.info("login {}", dto.getEmail());
        return ResponseEntity.ok(authService.login(dto, language));
    }

}
