package com.example.repository;

import com.example.entity.ChannelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ChannelRepository extends CrudRepository<ChannelEntity, Integer>, PagingAndSortingRepository<ChannelEntity, Integer> {

    @Query("FROM ChannelEntity WHERE profileId=?1")
    List<ChannelEntity> byUserIdGetChannelList(Integer id);

}
