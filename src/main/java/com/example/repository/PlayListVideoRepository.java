package com.example.repository;

import com.example.entity.PlayListVideoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayListVideoRepository extends CrudRepository<PlayListVideoEntity, Integer> {

    @Query("from PlayListVideoEntity where playListId=?1 order by orderNum asc ")
//and video.status='PUBLIC'
    List<PlayListVideoEntity> getAllByPlayListId(Integer id);
}

