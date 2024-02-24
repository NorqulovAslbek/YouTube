package com.example.repository;

import com.example.entity.ChannelEntity;
import org.springframework.data.jpa.repository.Query;
import com.example.enums.ChannelStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, Integer>, PagingAndSortingRepository<ChannelEntity, Integer> {

    @Query("FROM ChannelEntity WHERE profileId=?1")
    List<ChannelEntity> byUserIdGetChannelList(Integer id);
    Optional<ChannelEntity> findByIdAndProfileIdAndVisibleAndStatus(Integer id, Integer profileId, Boolean visible, ChannelStatus status);

}
