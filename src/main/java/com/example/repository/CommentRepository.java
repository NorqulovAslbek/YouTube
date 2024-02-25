package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> , PagingAndSortingRepository<CommentEntity, Integer> {

}
