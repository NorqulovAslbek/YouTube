package com.example.repository;

import com.example.entity.VideoTagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity, String> {

    @Query("from VideoTagEntity where videoId=?1")
    Optional<VideoTagEntity> getVideoById(String id);

    @Query("from VideoTagEntity where videoId=?1")
    List<VideoTagEntity> findVideoById(String id);

}
