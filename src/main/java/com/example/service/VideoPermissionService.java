package com.example.service;

import com.example.dto.VideoPermissionDTO;
import com.example.entity.VideoPermissionEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.repository.VideoPermissionRepository;
import com.example.repository.VideoRepository;
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

    public Boolean permission(VideoPermissionDTO dto, AppLanguage language) {
        Optional<VideoPermissionEntity> optional = permissionRepository.existsByVideoIdAndProfileId(dto.getProfileId(), dto.getVideoId());
        if (optional.isPresent()){
            throw new AppBadException("permission has already been granted!");
        }
        permissionRepository.save(getEntity(dto, language));
        return true;
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
}
