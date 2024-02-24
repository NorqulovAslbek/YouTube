package com.example.repository;

import com.example.entity.PlaylistEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {
}
