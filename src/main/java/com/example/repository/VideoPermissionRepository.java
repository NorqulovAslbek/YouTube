package com.example.repository;

import com.example.entity.VideoPermissionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface VideoPermissionRepository extends CrudRepository<VideoPermissionEntity,Integer> {
    @Query("FROM VideoPermissionEntity WHERE profileId=?1 AND visible=true")
    Page<VideoPermissionEntity> getPermission(Integer id, Pageable pageable);

    @Query("FROM VideoPermissionEntity WHERE profileId=?1 AND videoId=?2")
    Optional<VideoPermissionEntity> existsByVideoIdAndProfileId(Integer profileId, String videoId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoPermissionEntity WHERE profileId=?1")
    Integer getByProfileId(Integer profileId);

}
