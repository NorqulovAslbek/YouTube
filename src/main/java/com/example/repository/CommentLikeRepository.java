package com.example.repository;

import com.example.controller.CommentLikeController;
import com.example.entity.CommentLikeEntity;
import com.example.entity.VideoLikeEntity;
import com.example.enums.CommentLikeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {

    @Query("FROM CommentLikeEntity WHERE commentId=?1 AND profileId=?2")
    Optional<CommentLikeEntity> checkLike(Integer commentId, Integer profileId);

    @Transactional
    @Modifying
    @Query("update CommentLikeEntity set type =?1 where commentId =?2 and profileId=?3")
    void updateType(CommentLikeType type, Integer commentId, Integer profileId);


}
