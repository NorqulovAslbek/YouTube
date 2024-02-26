package com.example.repository;

import com.example.entity.PlayListVideoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListVideoRepository extends CrudRepository<PlayListVideoEntity, Integer> {

    @Transactional
    @Modifying
    @Query("delete from PlayListVideoEntity p where p.videoId=?1 and p.playListId=?2")
    int deleteByVideoIdAndPlayListId(String videoId, Integer playListId);
}

