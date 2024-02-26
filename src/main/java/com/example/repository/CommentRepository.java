package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer>, PagingAndSortingRepository<CommentEntity, Integer> {
    @Query("FROM CommentEntity WHERE profileId=?1")
    List<CommentEntity> getByProfileId(Integer id);
}
