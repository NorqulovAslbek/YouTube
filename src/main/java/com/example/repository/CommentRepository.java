package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

}
