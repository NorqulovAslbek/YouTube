package com.example.repository;

import com.example.entity.VideoPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoPermissionRepository extends CrudRepository<VideoPermissionEntity,Integer> {
    @Query("FROM VideoPermissionEntity WHERE profileId=?1 AND visible=true")
    Page<VideoPermissionEntity> getPermission(Integer id, Pageable pageable);

}
