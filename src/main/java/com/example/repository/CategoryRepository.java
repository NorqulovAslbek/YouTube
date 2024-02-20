package com.example.repository;

import com.example.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByNameUzAndNameRUAndNameEn(String nameUz, String nameRu, String nameEn);

    @Transactional
    @Modifying
    @Query("update CategoryEntity set nameUz = :nameUz, nameRU = :nameRu, nameEn = :nameEn, updatedDate = :updateDate where id = :id")
    int update(@Param("nameUz") String nameUz,
               @Param("nameRu") String nameRu,
               @Param("nameEn") String nameEn,
               @Param("updateDate") LocalDateTime updateDate,
               @Param("id") Integer id);

    @Query("FROM CategoryEntity WHERE  id=?1 AND visible=true ")
    Optional<CategoryEntity> getById(Integer id);
}
