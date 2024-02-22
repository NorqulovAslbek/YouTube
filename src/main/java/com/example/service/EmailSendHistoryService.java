package com.example.service;

import com.example.dto.EmailSendHistoryDTO;
import com.example.dto.UpdateEmailDTO;
import com.example.entity.EmailSendHistoryEntity;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import com.example.util.SpringSecurityUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;

@Service
public class EmailSendHistoryService {
    @Autowired
    private EmailSendHistoryRepository emailSendHistoryRepository;

    public PageImpl<EmailSendHistoryDTO> getEmailPagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdData");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<EmailSendHistoryEntity> all = emailSendHistoryRepository.findAll(pageable);
        return getEmailSendHistoryDTOS(pageable, all);
    }

    public PageImpl<EmailSendHistoryDTO> getByEmailPagination(Integer page, Integer size, UpdateEmailDTO dto) {
        String email = SpringSecurityUtil.getCurrentUser().getEmail();
        if (!email.equals(dto.getEmail())) {
            throw new AppBadException("you are not allowed");
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createdData");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<EmailSendHistoryEntity> all = emailSendHistoryRepository.findByEmail(email, pageable);
        return getEmailSendHistoryDTOS(pageable, all);
    }


    public EmailSendHistoryDTO getDTO(EmailSendHistoryEntity entity) {
        EmailSendHistoryDTO dto = new EmailSendHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setStatus(entity.getStatus());
        dto.setCreatedData(entity.getCreatedData());
        return dto;
    }


    private PageImpl<EmailSendHistoryDTO> getEmailSendHistoryDTOS(Pageable pageable, Page<EmailSendHistoryEntity> all) {
        long totalElements = all.getTotalElements();
        List<EmailSendHistoryEntity> content = all.getContent();
        List<EmailSendHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSendHistoryEntity entity : content) {
            dtoList.add(getDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
    }
}
