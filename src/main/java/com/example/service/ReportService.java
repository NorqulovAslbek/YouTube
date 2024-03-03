package com.example.service;

import com.example.dto.*;
import com.example.entity.ReportEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.mapper.ReportInfoMapper;
import com.example.repository.ChannelRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ResourceBundleService bundleService;
    @Autowired
    private AttachService attachService;

    public ReportDTO create(CreateReportDTO dto, AppLanguage language) {
        if (!profileRepository.existsById(dto.getProfileId())) {
            throw new AppBadException(bundleService.getMessage("profile.not.found", language));
        }
        if (!channelRepository.existsById(dto.getChannelId())) {
            throw new AppBadException(bundleService.getMessage("channel.not.found", language));
        }
        ReportEntity entity = new ReportEntity();
        entity.setContent(dto.getContent());
        entity.setProfileId(dto.getProfileId());
        entity.setChannelId(dto.getChannelId());
        entity.setType(dto.getType());
        entity.setCreatedDate(LocalDateTime.now());
        reportRepository.save(entity);
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(entity.getId());
        reportDTO.setContent(entity.getContent());
        reportDTO.setType(entity.getType());
        reportDTO.setCreatedDate(entity.getCreatedDate());
        return reportDTO;
    }

    public PageImpl<ReportInfoDTO> reportList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ReportInfoMapper> reportPage = reportRepository.getReportInfo(pageable);
        List<ReportInfoMapper> entityList = reportPage.getContent();
        long totalElements = reportPage.getTotalElements();
        List<ReportInfoDTO> dtoList = new LinkedList<>();
        for (ReportInfoMapper mapper : entityList) {
            dtoList.add(getReportInfoDTO(mapper));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }

    private ReportInfoDTO getReportInfoDTO(ReportInfoMapper mapper) {
        ReportInfoDTO dto = new ReportInfoDTO();
        dto.setId(mapper.getReportId());
        ProfileDTO profile = new ProfileDTO();
        profile.setId(mapper.getProfileId());
        profile.setName(mapper.getName());
        profile.setSurname(mapper.getSurname());
        PreviewAttachDTO previewAttachDTO = new PreviewAttachDTO();
        if (mapper.getPhotoId() != null) {
            previewAttachDTO.setId(mapper.getPhotoId());
            previewAttachDTO.setUrl(attachService.getURL(mapper.getPhotoId()).getUrl());
        }
        dto.setPreviewAttach(previewAttachDTO);
        dto.setProfile(profile);
        dto.setContent(mapper.getContent());
        dto.setChannelId(mapper.getChannelId());
        dto.setType(mapper.getType());
        dto.setCreatedDate(mapper.getCreatedDate());
        return dto;
    }

    public List<ReportInfoDTO> reportListByUserId(Integer profileId, AppLanguage language) {
        if (!profileRepository.existsById(profileId)) {
            throw new AppBadException(bundleService.getMessage("profile.not.found", language));
        }
        List<ReportInfoMapper> mapperList = reportRepository.getReportListByUserId(profileId);
        if (mapperList.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("report.not.found", language));
        }
        List<ReportInfoDTO> reportList = new LinkedList<>();
        for (ReportInfoMapper mapper : mapperList) {
            reportList.add(getReportInfoDTO(mapper));
        }
        return reportList;
    }

    public Boolean delete(Integer id, AppLanguage language) {
        ReportEntity entity = get(id, language);
        reportRepository.delete(entity);
        return true;
    }

    private ReportEntity get(Integer id, AppLanguage language) {
        Optional<ReportEntity> optional = reportRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("report.not.found", language));
        }
        return optional.get();
    }
}
