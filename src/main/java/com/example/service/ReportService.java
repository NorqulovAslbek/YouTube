package com.example.service;

import com.example.dto.CreateReportDTO;
import com.example.dto.ReportDTO;
import com.example.entity.ReportEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.ChannelRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        reportRepository.save(entity);

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(entity.getId());
        reportDTO.setContent(entity.getContent());
        reportDTO.setType(entity.getType());

        return reportDTO;
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
