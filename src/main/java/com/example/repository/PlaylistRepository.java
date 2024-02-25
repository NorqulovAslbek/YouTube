package com.example.repository;

import com.example.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {

    @Query("from PlaylistEntity where channelId=?1")
    Optional<PlaylistEntity> getByChannelId(Integer id);
}
