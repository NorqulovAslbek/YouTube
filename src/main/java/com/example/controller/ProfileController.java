package com.example.controller;

import com.example.dto.ChangeNameAndSurnameDTO;
import com.example.dto.ChangePasswordDTO;
import com.example.dto.CreateProfileDTO;
import com.example.dto.UpdateEmailDTO;
import com.example.enums.AppLanguage;
import com.example.service.ProfileService;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> updateEmail(@RequestBody UpdateEmailDTO dto,
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
    public ResponseEntity<?> createProfile(@Valid @RequestBody CreateProfileDTO dto) {
        return ResponseEntity.ok(profileService.createProfile(dto));
    }

    @Operation(summary = "Api for email update Verification", description = "User update email")
    @GetMapping("/verification/email/{jwt}")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        log.info("emailVerification {}", jwt);
        return ResponseEntity.ok(profileService.verification(jwt));
    }

    @Operation(summary = "Api for getProfile", description = "this api used for get profile")
    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                        AppLanguage appLanguage) {
        log.info("getProfile");
        return ResponseEntity.ok(profileService.getProfile(appLanguage));
    }
    @Operation(summary = "Api for update photo", description = "this api used for update photo")
    @PutMapping("/updatePhoto/{photoId}")
    public ResponseEntity<?> updatePhoto(@PathVariable("photoId") String photoId,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "UZ")
                                                 AppLanguage language){
        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
        log.info("update photo {} ",profileId);
        return ResponseEntity.ok(profileService.updateAttach(photoId,profileId,language));
    }


}
