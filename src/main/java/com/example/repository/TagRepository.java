package com.example.repository;

import com.example.entity.TagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update TagEntity  set name=:name,updatedDate=:updatedDate where id=:id")
    int update(@Param("name") String name,
               @Param("updatedDate") LocalDateTime updatedDate,
               @Param("id") Integer id);
}
