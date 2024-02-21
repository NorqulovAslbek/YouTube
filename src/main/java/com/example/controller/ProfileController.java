package com.example.controller;

import com.example.dto.ChangeNameAndSurnameDTO;
import com.example.dto.ChangePasswordDTO;
import com.example.dto.CreateProfileDTO;
import com.example.dto.UpdateEmailDTO;
import com.example.enums.AppLanguage;
import com.example.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.image.RescaleOp;

@Tag(name = "Profile Api list", description = "Api list for Profile")
@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Operation(summary = "Api for changePassword ", description = "this api used for change password")
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage appLanguage) {
        return ResponseEntity.ok(profileService.changePassword(dto, appLanguage));
    }

    @Operation(summary = "Api for updateEmail ", description = "this api used for change email")
    @PutMapping("/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailDTO dto,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage appLanguage) {
        return ResponseEntity.ok(profileService.updateEmail(dto, appLanguage));
    }

    @Operation(summary = "Api for changeNameAndSurname", description = "this api used for change name and surname")
    @PutMapping("/changeNameAndSurname")
    public ResponseEntity<?> changeNameAndSurname(@RequestBody ChangeNameAndSurnameDTO dto,
                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage appLanguage) {

        return ResponseEntity.ok(profileService.changeNameAndSurname(dto, appLanguage));
    }

    @Operation(summary = "Api for changeNameAndSurname", description = "this api used for change name and surname")
    @PostMapping("/adm/createProfile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileDTO dto) {
        return ResponseEntity.ok(profileService.createProfile(dto));
    }


}
