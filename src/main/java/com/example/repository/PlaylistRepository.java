package com.example.repository;

import com.example.entity.PlaylistEntity;
import com.example.entity.VideoEntity;
import com.example.enums.PlaylistStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer>, PagingAndSortingRepository<PlaylistEntity, Integer> {
    Optional<PlaylistEntity> findByIdAndVisible(Integer id, Boolean visible);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity SET status=?2 WHERE id=?1")
    void changeStatus(Integer id, PlaylistStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE PlaylistEntity SET visible=false WHERE id=?1")
    void delete(Integer id);

    @Query("from PlaylistEntity where channelId=?1")
    List<PlaylistEntity> getByChannelId(Integer id);

    @Query(value = "SELECT p.id, p.name FROM Playlist p WHERE p.id IN (SELECT plv.playlist_id FROM Video v INNER JOIN play_list_video plv ON v.id = plv.video_id)", nativeQuery = true)
    List<PlaylistEntity> getPlayList(String id);

}
