package com.example.service;

import com.example.dto.AuthDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.EmailSendHistoryEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.AppLanguage;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MDUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public boolean registration(RegistrationDTO dto, AppLanguage language) {
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (emailSendHistoryRepository.countSendEmail(dto.getEmail(), from, to) >= 3) {
            throw new AppBadException(resourceBundleService.getMessage("to.many.attempt", language));
        }
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException(resourceBundleService.getMessage("email.exists", language));
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(entity);
        sendEmailMessage(dto, entity, language);     //  ==============> email jonaish uchun
        return true;
    }

    private void sendEmailMessage(RegistrationDTO dto, ProfileEntity entity, AppLanguage language) {
        String jwt = JWTUtil.encodeForEmail(entity.getId());
        String text = getButtonLink(entity, jwt);
        EmailSendHistoryEntity emailSendHistoryEntity = new EmailSendHistoryEntity();
        emailSendHistoryEntity.setEmail(dto.getEmail());
        emailSendHistoryEntity.setMessage(jwt);
        emailSendHistoryEntity.setStatus(ProfileStatus.REGISTRATION);
        emailSendHistoryEntity.setCreatedDate(LocalDateTime.now());
        emailSendHistoryRepository.save(emailSendHistoryEntity);
        mailSender.sendEmail(dto.getEmail(), resourceBundleService.getMessage("complete.registration", language), text);
    }

    public String getButtonLink(ProfileEntity entity, String jwt) {
        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                      "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                      "<a style=\" background-color: #f44336;\n" +
                      "  color: white;\n" +
                      "  padding: 14px 25px;\n" +
                      "  text-align: center;\n" +
                      "  text-decoration: none;\n" +
                      "  display: inline-block;\" href=\"http://localhost:8080/auth/verification/email/%s\n" +
                      "\">Click</a>\n" +
                      "<br>\n";
        text = String.format(text, entity.getName(), jwt);
        return text;
    }

    public String verification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decode(jwt);
            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (optional.isEmpty()) {
                log.warn("Profile not found");
                throw new AppBadException(resourceBundleService.getMessage("profile.not.fount", AppLanguage.UZ));
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                log.warn("Profile in wrong status");
                throw new AppBadException(resourceBundleService.getMessage("profile.in.wrong.status", AppLanguage.UZ));
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
            EmailSendHistoryEntity emailSendHistoryEntity = new EmailSendHistoryEntity();
            emailSendHistoryEntity.setStatus(ProfileStatus.ACTIVE);
            emailSendHistoryEntity.setEmail(optional.get().getEmail());
            emailSendHistoryEntity.setMessage(jwt);
            emailSendHistoryRepository.save(emailSendHistoryEntity);
        } catch (JwtException e) {
            log.warn("Please tyre again.");
            throw new AppBadException(resourceBundleService.getMessage("please.tyre.again", AppLanguage.UZ));
        }
        return "Success";
    }


    public ProfileDTO login(AuthDTO dto, AppLanguage language) {
        Optional<ProfileEntity> optional = profileRepository.getProfile(dto.getEmail(), MDUtil.encode(dto.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.fount", language));
        }
        ProfileEntity profileEntity = optional.get();
        if (!profileEntity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.fount", language));
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setRole(profileEntity.getRole());
        profileDTO.setJwt(JWTUtil.encode(profileEntity.getEmail(), profileEntity.getRole()));
        return profileDTO;
    }
}
