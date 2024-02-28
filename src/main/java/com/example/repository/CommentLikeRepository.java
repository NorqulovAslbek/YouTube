package com.example.repository;

import com.example.controller.CommentLikeController;
import com.example.entity.CommentLikeEntity;
import com.example.entity.VideoLikeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommentLikeRepository extends CrudRepository<Integer, CommentLikeEntity> {

    @Query("FROM CommentLikeEntity WHERE commentId=?1 AND profileId=?2")
    Optional<CommentLikeEntity> checkLike(Integer commentId, Integer profileId);
}
