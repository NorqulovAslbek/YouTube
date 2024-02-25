package com.example.repository;

import com.example.entity.PlaylistEntity;
import com.example.enums.PlaylistStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {
    Optional<PlaylistEntity> findByIdAndVisible(Integer id, Boolean visible);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity SET status=?2 WHERE id=?1")
    void changeStatus(Integer id, PlaylistStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity SET visible=false WHERE id=?1")
    void delete(Integer id);
}
