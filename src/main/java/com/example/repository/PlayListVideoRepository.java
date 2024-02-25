package com.example.repository;

import com.example.entity.PlayListVideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListVideoRepository extends CrudRepository<PlayListVideoEntity,Integer> {


}

