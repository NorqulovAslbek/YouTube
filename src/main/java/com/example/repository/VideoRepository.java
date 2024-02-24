package com.example.repository;

import com.example.entity.VideoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends CrudRepository<VideoEntity, String>, PagingAndSortingRepository<VideoEntity, String> {
    @Query("FROM VideoEntity WHERE id=?1 AND categoryId=?2")
    Optional<VideoEntity> getVideoByVideoIdAndCategoryId(String videoId, Integer categoryId);

    @Query(value = " SELECT * FROM video WHERE category_id=?1 AND status= 'PUBLIC' ", nativeQuery = true)
    List<VideoEntity> getByCategoryId(Integer id);
}
