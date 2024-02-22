package com.example.repository;

import com.example.entity.ChannelEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChannelRepository extends CrudRepository<ChannelEntity, Integer> , PagingAndSortingRepository<ChannelEntity, Integer> {


}
