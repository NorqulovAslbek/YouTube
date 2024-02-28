package com.example.service;

import com.example.dto.CreateCommentLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.entity.VideoLikeEntity;
import com.example.enums.AppLanguage;
import com.example.repository.CommentLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    public Object create(CreateCommentLikeDTO dto, AppLanguage language) {
//        Integer profileId = SpringSecurityUtil.getCurrentUser().getId();
//        Optional<CommentLikeEntity> optional = commentLikeRepository.checkLike(dto.getCommentId(), profileId);
//
//        if (optional.isPresent()) {
//            CommentLikeEntity commentLike = optional.get();
//            commentLike.setType(dto.getType());
//            commentLike.setUpdatedDate(LocalDateTime.now());
//            videoLikeRepository.save(videoLikeEntity);
//            return getVideoLikeInfoDTO(videoLikeEntity, language);
//        }
//        getVideoById(dto.getVideoId(), language); //check qilish uchun vidio id bormi yoqmi
//        VideoLikeEntity videoLikeEntity = videoLikeRepository.save(toEntity(dto, profileId));
//        return getVideoLikeInfoDTO(videoLikeEntity, language);
        return null;


    }
}
