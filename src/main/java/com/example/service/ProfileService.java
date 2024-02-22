package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MDUtil;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private AuthService authService;
    public String changePassword(ChangePasswordDTO dto, AppLanguage appLanguage) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        ProfileEntity entity = get(appLanguage, currentUser.getId());
        if (MDUtil.encode(dto.getOldPassword()).equals(entity.getPassword()) && dto.getNewPassword().equals(dto.getNewPasswordConfirm())) {
            entity.setPassword(MDUtil.encode(dto.getNewPassword()));
            profileRepository.save(entity);
            return resourceBundleService.getMessage("successful", appLanguage);
        }
        return resourceBundleService.getMessage("error", appLanguage);
    }

    private ProfileEntity get(AppLanguage appLanguage, Integer id) {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.getById(id);
        if (profileEntityOptional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.found", appLanguage));
        }
        return profileEntityOptional.get();
    }

    public String updateEmail(UpdateEmailDTO dto, AppLanguage appLanguage) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        ProfileEntity entity = get(appLanguage, currentUser.getId());
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setEmail(dto.getEmail());
        authService.sendEmailMessage(registrationDTO, entity, appLanguage);
        return "todo";
    }

    public boolean changeNameAndSurname(ChangeNameAndSurnameDTO dto, AppLanguage language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        ProfileEntity profileEntity = get(language, currentUser.getId());
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setUpdatedDate(LocalDateTime.now());
        profileRepository.save(profileEntity);
        return true;
    }


    public ProfileDTO createProfile(CreateProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.getByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("email exist");
        }
        ProfileEntity profileEntity = profileRepository.save(toEntity(dto));
        return toDTO(profileEntity);
    }


    public ProfileEntity toEntity(CreateProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getProfileRole());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        return entity;
    }

    public ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));
        return dto;

    }
}
