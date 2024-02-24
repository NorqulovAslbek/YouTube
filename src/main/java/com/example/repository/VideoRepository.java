package com.example.repository;

import com.example.entity.VideoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<VideoEntity,String> {

}
