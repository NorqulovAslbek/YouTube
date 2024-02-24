package com.example.repository;

import com.example.entity.ChannelEntity;
import com.example.enums.ChannelStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, Integer>, PagingAndSortingRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findByIdAndProfileIdAndVisibleAndStatus(Integer id, Integer profileId, Boolean visible, ChannelStatus status);
}
