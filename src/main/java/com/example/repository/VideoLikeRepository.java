package com.example.repository;

import com.example.entity.VideoLikeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, Integer> {
    @Query("FROM VideoLikeEntity WHERE videoId=?1 AND profileId=?2")
    Optional<VideoLikeEntity> checkLike(String videoId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLikeEntity WHERE videoId=?1 AND profileId=?2")
    Integer deleteByAttachIdAndProfileId(String videoId, Integer id);
}
