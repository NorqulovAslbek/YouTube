package com.example.service;

import com.example.dto.EmailSendHistoryDTO;
import com.example.entity.EmailSendHistoryEntity;
import com.example.repository.EmailSendHistoryRepository;
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
        Pageable pageable = PageRequest.of(page - 1, size,sort);
        Page<EmailSendHistoryEntity> all = emailSendHistoryRepository.findAll(pageable);
        long totalElements = all.getTotalElements();
        List<EmailSendHistoryEntity> content = all.getContent();
        List<EmailSendHistoryDTO> dtoList = new LinkedList<>();
        for (EmailSendHistoryEntity entity : content) {
            dtoList.add(getDTO(entity));
        }
        return new PageImpl<>(dtoList, pageable, totalElements);
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
}
