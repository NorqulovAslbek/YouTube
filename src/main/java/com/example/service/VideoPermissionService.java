package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.VideoPermissionDTO;
import com.example.entity.ChannelEntity;
import com.example.entity.VideoEntity;
import com.example.entity.VideoPermissionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ChannelRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.VideoPermissionRepository;
import com.example.repository.VideoRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoPermissionService {
    @Autowired
    private VideoPermissionRepository permissionRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private ChannelRepository channelRepository;

    public Boolean permission(VideoPermissionDTO dto, AppLanguage language) {
        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentUser();
        String videoId = dto.getVideoId();
        Optional<VideoEntity> optionalVideo = videoRepository.findById(videoId);
        if (optionalVideo.isPresent()){
            Integer channelId = optionalVideo.get().getChannelId();
            Optional<ChannelEntity> optionalChannel = channelRepository.findById(channelId);
            if (optionalChannel.isPresent()){
                if (optionalChannel.get().getProfileId().equals(currentUser.getId())){
                    Optional<VideoPermissionEntity> optional = permissionRepository.existsByVideoIdAndProfileId(dto.getProfileId(), dto.getVideoId());
                    if (optional.isPresent()){
                        throw new AppBadException(resourceBundleService.getMessage("permission.has.already",language));
                    }
                    permissionRepository.save(getEntity(dto, language));
                    return true;
                }
                throw new AppBadException("sizga ruxsat mavjud emas");
            }
            throw new AppBadException("kanal mavjud emas");
        }
        throw new AppBadException("Bunday vidio mavjud emas");
    }

    private VideoPermissionEntity getEntity(VideoPermissionDTO dto, AppLanguage language) {
        if (!videoRepository.existsById(dto.getVideoId())) {
            throw new AppBadException(resourceBundleService.getMessage("video.not.found", language));
        }
        if (!profileRepository.existsById(dto.getProfileId())) {
            throw new AppBadException(resourceBundleService.getMessage("profile.not.fount", language));
        }
        VideoPermissionEntity permission = new VideoPermissionEntity();
        permission.setVideoId(dto.getVideoId());
        permission.setProfileId(dto.getProfileId());
        return permission;
    }

    public Boolean removePermission(Integer profileId, AppLanguage language) {
        Integer effectiveRow = permissionRepository.getByProfileId(profileId);
        return effectiveRow!=0;
    }
}
