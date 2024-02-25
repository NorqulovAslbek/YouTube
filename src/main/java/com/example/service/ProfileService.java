package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.*;
import com.example.dto.CreateProfileDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ChannelEntity;
import com.example.entity.EmailSendHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.AttachRepository;
import com.example.repository.EmailSendHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MDUtil;
import com.example.util.SpringSecurityUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private AttachService attachService;


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
        entity.setTempEmail(dto.getEmail());
        profileRepository.save(entity);
        sendEmailMessage(entity, appLanguage);
        return resourceBundleService.getMessage("message.has.been.sent.to.email", appLanguage);
    }

    private void sendEmailMessage(ProfileEntity entity, AppLanguage language) {
        String jwt = JWTUtil.encode(entity.getEmail(), entity.getRole());
        String text = getButtonLink(entity, jwt);
        EmailSendHistoryEntity emailSendHistoryEntity = new EmailSendHistoryEntity();
        emailSendHistoryEntity.setEmail(entity.getTempEmail());
        emailSendHistoryEntity.setMessage(jwt);
        emailSendHistoryEntity.setStatus(ProfileStatus.ACTIVE);
        emailSendHistoryEntity.setCreatedDate(LocalDateTime.now());
        emailSendHistoryRepository.save(emailSendHistoryEntity);
        mailSender.sendEmail(entity.getTempEmail(), resourceBundleService.getMessage("complete.registration", language), text);
    }

    public String getButtonLink(ProfileEntity entity, String jwt) {
        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/profile/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        text = String.format(text, entity.getName(), jwt);
        return text;
    }

    public String verification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decodeForSpringSecurity(jwt);
            Optional<ProfileEntity> optional = profileRepository.findByEmail(jwtDTO.getEmail());
            if (optional.isEmpty()) {
                log.warn("Profile not found");
                throw new AppBadException(resourceBundleService.getMessage("profile.not.fount", AppLanguage.UZ));
            }
            ProfileEntity entity = optional.get();
            entity.setEmail(entity.getTempEmail());
            entity.setTempEmail(null);
            entity.setUpdatedDate(LocalDateTime.now());
            profileRepository.save(entity);
            return resourceBundleService.getMessage("successful", AppLanguage.UZ);
        } catch (JwtException e) {
            log.warn("Please tyre again.");
            throw new AppBadException(resourceBundleService.getMessage("please.tyre.again", AppLanguage.UZ));
        }
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

    public ProfileDTO getProfile(AppLanguage language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        ProfileEntity profileEntity = get(language, currentUser.getId());
        return getProfileDTO(profileEntity);
    }


    public ProfileEntity toEntity(CreateProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getProfileRole());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setPhotoId(dto.getPhoto());
        return entity;
    }

    public ProfileEntity get(Integer id, AppLanguage appLanguage) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("channel.not.found", appLanguage));
        }
        return optional.get();
    }

    public ProfileDTO getById(Integer id, AppLanguage appLanguage) {
        ProfileEntity profileEntity = get(id, appLanguage);
        return toDTO(profileEntity);
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

    public ProfileDTO getProfileDTO(ProfileEntity entity) {
        String photoId = entity.getPhotoId();
        ProfileDTO dto = new ProfileDTO();
        if (photoId != null) {
            AttachDTO attachDTO = attachService.getURL(photoId);
            dto.setPhoto(attachDTO.getUrl());
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public Object updateAttach(String photoId, Integer profileId, AppLanguage language) {
        ProfileEntity profile = get(language, profileId);
        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException(resourceBundleService.getMessage("not.allowed", language));
        }
        Optional<AttachEntity> attach = attachRepository.findById(photoId);
        if (attach.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("attach.not.found", language));
        }

        if (profile.getPhotoId() == null) {
            profileRepository.updatePhoto(profileId, LocalDateTime.now(), photoId);
            return true;
        } else {
            String oldPhotoId = profile.getPhotoId();
            if (oldPhotoId.equals(photoId)) {
                profileRepository.updatePhoto(profileId, LocalDateTime.now(), photoId);
                return true;
            } else {
                profileRepository.updatePhoto(profileId, LocalDateTime.now(), photoId);
                attachService.delete(oldPhotoId, language);
                attachRepository.deleteById(oldPhotoId);
                return true;
            }
        }

    }
}
